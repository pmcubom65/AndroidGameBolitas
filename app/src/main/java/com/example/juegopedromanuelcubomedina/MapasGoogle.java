package com.example.juegopedromanuelcubomedina;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapasGoogle extends FragmentActivity implements OnMapReadyCallback , LocationListener {

    private GoogleMap mMap;
    double latitud;
    double longitud;
    private static final long MIN_TIME = 10000;
    Marker mCurrLocationMarker=null;
    LocationListener locationListener;
    LatLng latLng;
    LocationManager  locationManager;


    GoogleMap mGoogleMap;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapas_google);

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);

        } else {
            localizar();
        }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    public void localizar() {

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Gps migps = new Gps();

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        this.latitud=migps.getLatitud();
        this.longitud=migps.getLongitud();

        final boolean gpsenable=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!gpsenable) {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_DENIED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                !=PackageManager.PERMISSION_GRANTED) {

         return;

        }

       locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME, 0,migps);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME, 0,migps);


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==1000) {
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                localizar();
                return;
            }
        }
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
     //   Gps gps=new Gps();
          mMap = googleMap;


   /*     mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                Gps gps2=new Gps();

                LatLng milugar2 = new LatLng(gps2.getLatitud(), gps2.getLongitud());
                mMap.moveCamera(CameraUpdateFactory.newLatLng(milugar2));
               mMap.getMaxZoomLevel();


            }
        });*/






  /*          Marker mimarker=gps.getmCurrLocationMarker();


        mMap.getMaxZoomLevel();

        mMap.addMarker(new MarkerOptions()..position(milugar).title("Estoy aqui"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(milugar));*/



    }


    @Override
    public void onLocationChanged(Location location) {
        mMap.clear();
        latitud=location.getLatitude();
        longitud=location.getLongitude();

        System.out.println("latitud "+latitud+" longitud "+longitud);


        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng).zoom(14).build();



        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .zIndex(20).title("Score"));
    }



    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        System.out.println("Errores en el gps");
    }

    @Override
    public void onProviderEnabled(String provider) {
        System.out.println("GPS activado");
    }

    @Override
    public void onProviderDisabled(String provider) {
        System.out.println("GPS desactivado");
    }
}
