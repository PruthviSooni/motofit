package com.test.motofit_temp.test_1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapFragment extends AppCompatActivity implements OnMapReadyCallback{
    private Boolean LocationPermissionGranted = false;
    private GoogleMap mMap;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this,"Map is Ready",Toast.LENGTH_LONG).show();
        mMap = googleMap;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getLocationPermission();
    }


    private void initmap () {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapFragment.this);
    }




    private void getLocationPermission(){
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION,};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    LocationPermissionGranted = true;
            }
            else {
                ActivityCompat.requestPermissions(this,permission,1234);
            }
        }else {
            ActivityCompat.requestPermissions(this,permission,1234);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        LocationPermissionGranted = false;
        switch (requestCode){
            case 1234:{
                if (grantResults.length > 0){
                    for(int i = 0;i<grantResults.length;i++){
                        if(grantResults [i] != PackageManager.PERMISSION_GRANTED){
                            LocationPermissionGranted = false;
                            return;
                        }
                    }
                }
                LocationPermissionGranted = true;
                initmap();
                    //init Map

            }
        }
    }
}
