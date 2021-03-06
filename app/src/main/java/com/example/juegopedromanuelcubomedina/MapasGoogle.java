package com.example.juegopedromanuelcubomedina;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
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

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;

import com.example.juegopedromanuelcubomedina.R;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

public class MapasGoogle extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    LocationManager locationManager;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    Marker marker;
    LocationListener locationListener;
    Polyline line;

    String score;
    int flag=0;
    LatLng anterior = new LatLng(0, 0);
    ArrayList<ElPunto> lospuntos=new ArrayList<>();
    String identificacion="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapas_google);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent recibido=getIntent();
        score=recibido.getStringExtra("score");


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        final boolean gpsenable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsenable) {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }





        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
        else{

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LatLng actual = new LatLng(latitude, longitude);

                    if ((score instanceof String) && identificacion.isEmpty()) {
                        insertar(score, latitude,longitude);

                    }

                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    mMap.setMaxZoomPreference(20);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(actual, 12.0f));
                    rellenarTodo();

                    lospuntos.forEach(i->{
                        LatLng latLng2 = new LatLng(i.getLatitud(), i.getLongitud());

                       identificacion=mMap.addMarker(new MarkerOptions().position(latLng2).title(i.getScore())).getId();

                    });




                    /*try {
                        List<Address> addresses =
                                geocoder.getFromLocation(latitude, longitude, 1);
                        String result = addresses.get(0).getLocality()+":";
                        result += addresses.get(0).getCountryName();
                        LatLng latLng = new LatLng(latitude, longitude);
                        if (marker != null){
                            marker.remove();
                            marker = mMap.addMarker(new MarkerOptions().position(latLng).title("Score "+score));

                            mMap.setMaxZoomPreference(20);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));


                        }
                        else{
                            marker = mMap.addMarker(new MarkerOptions().position(latLng).title("Score "+score));
                            mMap.setMaxZoomPreference(20);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 21.0f));
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){

            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    mMap.setMaxZoomPreference(20);


              //      insertar(score, latitude,longitude);



                    //get the location name from latitude and longitude
                    Geocoder geocoder = new Geocoder(getApplicationContext());



                //    rellenarTodo();
                //    mMap.setMaxZoomPreference(20);

                    //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));

                  /*  lospuntos.forEach(i->{
                        LatLng latLng = new LatLng(i.getLatitud(), i.getLongitud());
                        line = mMap.addPolyline(new PolylineOptions()
                                .add(latLng)
                                .width(5)
                                .color(Color.RED));


                    });*/




                    /* try {
                        List<Address> addresses =
                                geocoder.getFromLocation(latitude, longitude, 1);
                        String result = addresses.get(0).getLocality()+":";
                        result += addresses.get(0).getCountryName();
                        LatLng latLng = new LatLng(latitude, longitude);
                        if (marker != null){
                            marker.remove();
                            marker = mMap.addMarker(new MarkerOptions().position(latLng).title("Score "+score));

                           line = mMap.addPolyline(new PolylineOptions()
                                    .add(latLng)
                                    .width(5)
                                    .color(Color.RED));

                            mMap.setMaxZoomPreference(20);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
                        }
                        else{
                            marker = mMap.addMarker(new MarkerOptions().position(latLng).title("Score "+score));
                            mMap.setMaxZoomPreference(20);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 21.0f));
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }


    public void insertar(String punt, double lttud, double longtud) {

        MisLocalizaciones rel=new MisLocalizaciones(this);
        SQLiteDatabase midatabase=rel.getWritableDatabase();

        ContentValues contentvalues2=new ContentValues();

        contentvalues2.put("puntuacion", "Score: "+punt);
        contentvalues2.put("latitud", String.valueOf(lttud));
        contentvalues2.put("longitud", String.valueOf(longtud));


        long row= midatabase.insertWithOnConflict("lospuntos",null, contentvalues2, SQLiteDatabase.CONFLICT_REPLACE);


        midatabase.close();


        contentvalues2.clear();

    }


    public void rellenarTodo() {

        lospuntos.clear();
        MisLocalizaciones re=new MisLocalizaciones(this);

        SQLiteDatabase databaselectura=re.getReadableDatabase();
        String[] columnas={"puntuacion", "latitud", "longitud"};
        Cursor cursor= databaselectura.query("lospuntos",columnas,null,null,null,null,null);


        if (cursor.moveToFirst()) {

            do {


                String scores = cursor.getString(0);
                String lat = cursor.getString(1);
                String longitud = cursor.getString(2);

                lospuntos.add(new ElPunto(Double.parseDouble(lat), Double.parseDouble(longitud), scores));


            } while (cursor.moveToNext());
        }
        re.close();

    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


    }

    @Override
    protected void onStop() {

        super.onStop();
        finish();
        locationManager.removeUpdates(locationListener);
    }
}