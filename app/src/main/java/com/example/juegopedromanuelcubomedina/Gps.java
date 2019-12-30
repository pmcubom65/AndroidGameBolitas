package com.example.juegopedromanuelcubomedina;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.TextView;

public class Gps implements LocationListener {

   MainActivity mainactivity;
   TextView tvmensaje;
   double latitud;
   double longitud;

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
        System.out.println("GPS desactivado");
    }

    @Override
    public void onProviderDisabled(String provider) {
        System.out.println("GPS desactivado");
    }
}
