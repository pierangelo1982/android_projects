package com.example.pierangelo.youthhostelslombardy;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by pierangelo on 09/07/15.
 */
public final class MyLocationListener implements LocationListener {

    @Override
    public void onLocationChanged(Location locFromGps) {
        Log.d("e dai che va", "e dai che va");
    }

    @Override
    public void onProviderDisabled(String provider) {
        // called when the GPS provider is turned off (user turning off the GPS on the phone)
        Log.d("spento", "spento");

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("acceso", "acceso");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // called when the status of the GPS provider changes
        Log.d("cambia", "tuttocambia");

    }
}