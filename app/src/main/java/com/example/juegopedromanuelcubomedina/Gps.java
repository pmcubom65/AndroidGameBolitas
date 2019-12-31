package com.example.juegopedromanuelcubomedina;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Gps implements LocationListener {

   MainActivity mainactivity;
   TextView tvmensaje;
   double latitud;
   double longitud;
    Marker mCurrLocationMarker=null;

    GoogleMap mGoogleMap;
    GoogleApiClient mGoogleApiClient;

   public MainActivity getMainactivity() {
       return mainactivity;
   }


    public void setMainactivity(MainActivity mainactivity, TextView mensaje) {
        this.mainactivity = mainactivity;
        this.tvmensaje=mensaje;
    }

    @Override
    public void onLocationChanged(Location location) {

        latitud=location.getLatitude();
        longitud=location.getLongitude();

        System.out.println("latitud "+latitud+" longitud "+longitud);




    }

    public GoogleMap getmGoogleMap() {
        return mGoogleMap;
    }

    public void setmGoogleMap(GoogleMap mGoogleMap) {
        this.mGoogleMap = mGoogleMap;
    }

    public Marker getmCurrLocationMarker() {
        return mCurrLocationMarker;
    }

    public double getLatitud() {
        return latitud;
    }



    public double getLongitud() {
        return longitud;
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
