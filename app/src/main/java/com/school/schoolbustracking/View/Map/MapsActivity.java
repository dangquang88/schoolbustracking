package com.school.schoolbustracking.View.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.school.schoolbustracking.Config.ShareF;
import com.school.schoolbustracking.Models.MapModel;
import com.school.schoolbustracking.Presenter.MapPresenter;
import com.school.schoolbustracking.Presenter.MapView;
import com.school.schoolbustracking.Presenter.UserPreSenter;
import com.school.schoolbustracking.R;

import java.util.ArrayList;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, MapView {

    private GoogleMap mMap;
    private MapPresenter mapPresenter;
    private  MarkerOptions markerOptions;
    private ArrayList<MapModel> arrayList;
    private  int check = 0;
    private ShareF shareF;
    private  SupportMapFragment mapFragment;
    private  LatLng latLng_user;
    private  Location lUser;
    private  int checkmap = 0;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mapPresenter = new MapPresenter(this);
        arrayList = new ArrayList<>();
        mapPresenter.HandleReadData();
        shareF = new ShareF(this);
         UpdateLocation();



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
      mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {



        mMap =googleMap;
        markerOptions = new MarkerOptions();
        int check = 0;
        if(arrayList.size()>0){
            for(MapModel m : arrayList){
                double distance = 0;
              LatLng latLng = new LatLng(m.getLat(),m.getLo());
               Location lB = new Location("LB");
               lB.setLongitude(latLng.longitude);
               lB.setLatitude(latLng.latitude);
                 distance = lUser.distanceTo(lB)/1000;


                if(distance<=10){
                    markerOptions.position(latLng);
                    markerOptions.title(m.getAddress());
                    markerOptions.snippet("About : "+String.format("%.1f",distance)+" km");
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_gps));
                    googleMap.addMarker(markerOptions);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));
                    check = 1;
                }


            }
            if(check ==0){
                Toast.makeText(MapsActivity.this, "No buses nearby u !", Toast.LENGTH_SHORT).show();
            }
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {

                    for(MapModel m : arrayList){
                        if(m.getLo()==marker.getPosition().longitude && m.getLat()==marker.getPosition().latitude){
                            Intent intent = new Intent(MapsActivity.this,SearchMapActivity.class);
                            intent.putExtra("MAP",m);
                            startActivity(intent);
                        }
                    }
                    return true;
                }
            });
        }


    }


    @Override
    public void getDataMap(double lat, double lo, String address) {
        arrayList.add(new MapModel(lat,lo,address));
        if(check == 0){

            mapFragment.getMapAsync(this);
            check = 1;
        }

    }
    private void UpdateLocation() {
        SharedPreferences sharedPreferences= getSharedPreferences("MENU", Context.MODE_PRIVATE);
        String tasks = sharedPreferences.getString("TASK","");




        ShareF shareF = new ShareF(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION},123);
            }else{
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {


                        shareF.putLa(location.getLatitude());
                        shareF.putLo(location.getLongitude());
                        if(checkmap == 0){
                            mapFragment.getMapAsync(MapsActivity.this);
                            if(tasks.equalsIgnoreCase("Student")){
                                mapPresenter.HandleUpdateLocation(location.getLatitude(),location.getLongitude(),1,shareF.getUID());
                            }else{
                                mapPresenter.HandleUpdateLocation(location.getLatitude(),location.getLongitude(),2,shareF.getUID());
                            }

                            checkmap = 1;
                        }
                        lUser = new Location("User");
                        lUser.setLongitude(location.getLongitude());
                        lUser.setLatitude(location.getLatitude());

                    }
                });


            }
        }

    }
}