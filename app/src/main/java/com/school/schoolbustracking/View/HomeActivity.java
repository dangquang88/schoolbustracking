package com.school.schoolbustracking.View;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.school.schoolbustracking.Config.ShareF;
import com.school.schoolbustracking.Presenter.MapPresenter;
import com.school.schoolbustracking.Presenter.MapView;
import com.school.schoolbustracking.R;
import com.school.schoolbustracking.View.Account.SignInActivity;
import com.school.schoolbustracking.View.FragMent.FragMent_Contact;
import com.school.schoolbustracking.View.FragMent.FragMent_Profile;
import com.school.schoolbustracking.View.Map.MapsActivity;

import java.util.Set;

public class HomeActivity  extends AppCompatActivity implements MapView, OnMapReadyCallback {
    private NavigationView navigationView;
    private Fragment fm;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    public  static  String uid_teacher_contact="";
    private MapPresenter mapPresenter;
    private SupportMapFragment mapFragment;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        frameLayout = findViewById(R.id.framelayout);

        navigationView = findViewById(R.id.navigationview);
        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        Init();
        fm = new FragMent_Profile();
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,fm).commit();
       
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void Init() {
        if(checkSelfPermission(Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},123);
        }

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open,R.string.Close);
        toggle.syncState();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.searchbus: startActivity(new Intent( HomeActivity.this, MapsActivity.class));break;
                    case R.id.profile:fm =new FragMent_Profile();break;
                    case R.id.contact:fm =new FragMent_Contact();break;
                    case R.id.signOut:startActivity(new Intent(HomeActivity.this, SignInActivity.class));finish();break;
                    case  R.id.Location: UpdateLocation();break;
                    case R.id.home: fm=null;break;
                    case R.id.settings: startActivity( new Intent(HomeActivity.this, SettingsActivity.class));


                }
                if(fm!=null){
                    frameLayout.setVisibility(View.VISIBLE);
                    getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,fm).commit();
                }else{
                    frameLayout.setVisibility(View.GONE);
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void UpdateLocation() {
        mapPresenter = new MapPresenter(this);
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
                        if(tasks.equalsIgnoreCase("Student")){
                            mapPresenter.HandleUpdateLocation(location.getLatitude(),location.getLongitude(),1,shareF.getUID());
                        }else{
                            mapPresenter.HandleUpdateLocation(location.getLatitude(),location.getLongitude(),2,shareF.getUID());
                        }


                    }
                });


            }
        }

    }

    @Override
    public void getDataMap(double lat, double lo, String address) {

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        ShareF shareF = new ShareF(this);

        MarkerOptions markerOptions = new MarkerOptions();
        LatLng latLng = new LatLng(shareF.getLat(),shareF.getLo());


        markerOptions.position(latLng);
        markerOptions.title("Your Here !");
        Bitmap icon = BitmapFactory.decodeResource(getResources(),R.drawable.avatar);
        icon = Bitmap.createScaledBitmap(icon,icon.getWidth()/4,icon.getHeight()/4,true);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,14));

    }
}
