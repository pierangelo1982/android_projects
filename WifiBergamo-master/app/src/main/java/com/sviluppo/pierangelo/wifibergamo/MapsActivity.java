package com.sviluppo.pierangelo.wifibergamo;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity {

    String myurl = "http://www.dati.lombardia.it/resource/w5ni-c86b.json";

    private Context context;

    private static final String TAG_ID = "id";
    private static final String TAG_TIPOLOGIA = "tipologia";
    private static final String TAG_LOCATION = "location";
    private static final String TAG_REGISTRAZIONE = "needs_recording";
    private static final String TAG_LATITUDE = "latitude";
    private static final String TAG_LONGITUDINE = "longitude";
    private static final String TAG_RETE = "rete";
    private static final String TAG_ENTE = "ente";
    private static final String TAG_CONNETTIVITA = "connettivita";
    private static final String TAG_DISTANZA = "distanza";
    private static final String TAG_LATLONG = null;

    private Location loc;
    private Location start;



    //// fine jsonlista


    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();



    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        /// json lista
        new CreaLista(MapsActivity.this).execute();

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener ascoltatore = new MyLocationListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ascoltatore);

    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            /// mostro la mia posizione sulla mappa
            mMap.setMyLocationEnabled(true);
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }


    private void setUpMap() {
        /// mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }


    private class CreaLista extends AsyncTask<String, Void, Void> {

        private ProgressDialog dialog;

        public CreaLista(MapsActivity mapsActivity) {

            context = mapsActivity;

            dialog = new ProgressDialog(context);
        }

        private Context context;

        protected void onPreExecute() {
            this.dialog.setMessage("Dati in caricamento");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(final Void success) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }


            Double lati = 45.7044258;
            Double longi = 9.6602655;
            LatLng myplace = new LatLng(lati, longi);

            final String js = String.valueOf(jsonlist);

            try {
                Log.d("europa", js);
                JSONArray json = new JSONArray(js);
                for (int i = 0; i < json.length(); i++) {
                    JSONObject c = json.getJSONObject(i);
                    final String distanza = c.getString(TAG_DISTANZA);
                    final String posizione = c.getString(TAG_TIPOLOGIA);
                    final String latitude = c.getString(TAG_LATITUDE);
                    final String longitude = c.getString(TAG_LONGITUDINE);

                    ////CREO Coordinate per loop marker
                    double eLat = Double.parseDouble(latitude);
                    double eLong = Double.parseDouble(longitude);
                    mMap.addMarker(new MarkerOptions().position(new LatLng(eLat, eLong)).title(distanza)
                            .snippet(posizione));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myplace, 15));

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("megaerrore", String.valueOf(e));
            }
            Log.d("aiuto", String.valueOf(jsonlist));

        }

        @Override
        protected Void doInBackground(String... params)
        {
            //ottengo mia posizione gps e generò un punto di partenza
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            loc = Utils.getLastBestLocation(locationManager);
            double slat = loc.getLatitude();
            double slong = loc.getLongitude();
            start = new Location("start");
            start.setLatitude(slat);
            start.setLongitude(slong);

            //Parsing JSON
            JSONParser jParser = new JSONParser();
            String par = jParser.makeServiceCall(myurl, JSONParser.GET);

            try
            {
                JSONArray json = new JSONArray(par);

                for (int i = 0; i < json.length(); i++)
                {
                    JSONObject c = json.getJSONObject(i);
                    String tipologia = c.getString(TAG_TIPOLOGIA);
                    String ente = c.getString(TAG_ENTE);
                    // SubArray location:
                    JSONObject l = c.getJSONObject(TAG_LOCATION);
                    String latitude = l.getString(TAG_LATITUDE);
                    String longitude = l.getString(TAG_LONGITUDINE);

                    ////CREO PUNTO DI ARRIVO
                    double eLat = Double.parseDouble(latitude);
                    double eLong = Double.parseDouble(longitude);
                    Location end = new Location("end");
                    end.setLatitude(eLat);
                    end.setLongitude(eLong);

                    /// CALCOLO DISTANZA DA PUNTO START A PUNTO END
                    double distanza = start.distanceTo(end) / 1000;
                    final String distanz = String.format("%.2f", distanza);  ///limito a due i decimali dopo la virgola;


                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(TAG_TIPOLOGIA, "\" Posizione: " + tipologia + "\"");
                    map.put(TAG_ENTE, "\" Ente: " + ente + "\"");
                    map.put(TAG_LATITUDE, latitude);
                    map.put(TAG_LONGITUDINE, longitude);
                    map.put(TAG_DISTANZA, "\" DISTANZA: " + distanz + " Km. \"");
                    jsonlist.add(map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

}