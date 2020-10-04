package com.sviluppo.pierangelo.timesheet;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by pierangelo on 07/05/16.
 */
public class Utils
{
    // ottieni ultima posizione ottenuta
    public static Location getLastBestLocation(LocationManager mLocationManager)
    {
        Location locationGPS = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location locationNet = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        long GPSLocationTime = 0;
        if (null != locationGPS) { GPSLocationTime = locationGPS.getTime(); }

        long NetLocationTime = 0;

        if (null != locationNet)
        {
            NetLocationTime = locationNet.getTime();
        }

        if ( 0 < GPSLocationTime - NetLocationTime )
        {
            return locationGPS;
        }
        else {
            return locationNet;
        }
    }


    /// connessione parser json
    public static String myArray(String myurl) throws MalformedURLException
    {
        URL url = new URL(myurl);
        HttpURLConnection connection;
        try
        {
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null)
            {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    //ottieni solo time da datetime
    public String getDateTime(Date dt)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "HH:mm:ss", Locale.getDefault());
        return dateFormat.format(dt);
    }

    //ottieni solo time da datetime
    /*public String getSoloData(Date dt)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-mm-yyyy'T'HH:mm:ss", Locale.getDefault());
        return dateFormat.format(dt);
    } */


}
