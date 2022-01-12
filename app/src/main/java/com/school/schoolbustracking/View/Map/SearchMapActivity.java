package com.school.schoolbustracking.View.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.L;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.school.schoolbustracking.Config.ShareF;
import com.school.schoolbustracking.Models.MapModel;
import com.school.schoolbustracking.Presenter.MapPresenter;
import com.school.schoolbustracking.Presenter.MapView;
import com.school.schoolbustracking.R;

import java.util.ArrayList;


public class SearchMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private  MarkerOptions markerOptions;
    private ShareF shareF;
    private  SupportMapFragment mapFragment;

    private  MapModel mapModel;
private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        shareF = new ShareF(this);
        Intent intent = getIntent();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Back");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mapModel = (MapModel) intent.getSerializableExtra("MAP");



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
      mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        Polyline polyline = googleMap.addPolyline(new PolylineOptions()
                .clickable(true).add(
                        new LatLng(mapModel.getLat(),mapModel.getLo()),
             new LatLng(shareF.getLat(),shareF.getLo())));

        polyline.setColor(ContextCompat.getColor(SearchMapActivity.this, R.color.main));
        polyline.setWidth(12);
        polyline.setJointType(JointType.DEFAULT);
        polyline.setEndCap(new RoundCap());









       LatLng latLng = new LatLng(shareF.getLat(),shareF.getLo());


        markerOptions.position(latLng);
        markerOptions.title("Your Here !");
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,14));

        /// bus




        Location lB = new Location("LB");
        lB.setLongitude(mapModel.getLo());
        lB.setLatitude(mapModel.getLat());
        // lUser

        Location lU = new Location("LU");
        lU.setLongitude(shareF.getLo());
        lU.setLatitude(shareF.getLat());
        double distance = lU.distanceTo(lB)/1000;

        /// custom bus
        LatLng latLng_bus = new LatLng(mapModel.getLat(),mapModel.getLo());

        markerOptions.position(latLng_bus);
        markerOptions.title(mapModel.getAddress());
        markerOptions.snippet("About : "+String.format("%.1f",distance)+" km");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.bus_gps));
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng_bus));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,14));


      //  mMap.addMarker(markerOptions);


    }



}