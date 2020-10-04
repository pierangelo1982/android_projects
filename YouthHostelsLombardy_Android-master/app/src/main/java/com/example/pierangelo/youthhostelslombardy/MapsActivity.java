package com.example.pierangelo.youthhostelslombardy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends FragmentActivity {

    // add for json
    String urlok = "http://www.dati.lombardia.it/resource/r9fb-4fm4.json";

    private Context context;


    private static final String TAG_DENOMINAZIO = "denominazione";
    private static final String TAG_COMUNE = "comune_provincia";
    private static final String TAG_PROVINCIA = "prov";
    private static final String TAG_INDIRIZZO = "indirizzo";
    private static final String TAG_POSTI = "numero_posti_letto";
    private static final String TAG_APERTURA = "periodo_apertura";
    private static final String TAG_TELEFONO = "telefono";
    private static final String TAG_SITO = "sito_internet"; ///array
    private static final String TAG_URL = "url";
    private static final String TAG_LOCATION = "location"; /// array
    private static final String TAG_LAT = "latitude";
    private static final String TAG_LONG = "longitude";

    private static final String TAG_ID = "id";



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
                    String denominazione = c.getString(TAG_DENOMINAZIO);
                    String indirizzo = c.getString(TAG_INDIRIZZO);
                    final String latitude = c.getString(TAG_LAT);
                    final String longitude = c.getString(TAG_LONG);
                    ////CREO Coordinate per loop marker
                    double eLat = Double.parseDouble(latitude);
                    double eLong = Double.parseDouble(longitude);
                    mMap.addMarker(new MarkerOptions().position(new LatLng(eLat, eLong)).title(denominazione)
                            .snippet(indirizzo));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myplace, 8));

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("megaerrore", String.valueOf(e));
            }
            Log.d("aiuto", String.valueOf(jsonlist));

        }

        @Override
        protected Void doInBackground(String... params) {

            JSONParser jParser = new JSONParser(); // get JSON data from URL

            String par = jParser.makeServiceCall(urlok, JSONParser.GET);

            try {
                JSONArray json = new JSONArray(par);

                for (int i = 0; i < json.length(); i++) {

                    JSONObject c = json.getJSONObject(i);
                    String denominazione = c.getString(TAG_DENOMINAZIO);
                    String denominazio = String.format("\" %s \"", denominazione);// messo tra parentesi per ovviare all'errore dato dall'accento presente nel valore
                    String comune = c.getString(TAG_COMUNE);
                    String indirizzo = c.getString(TAG_INDIRIZZO);
                    String provincia = c.getString(TAG_PROVINCIA);
                    String luogo = String.format("\" %s , \n %s %s \"", indirizzo, comune, provincia);// messo tra parentesi per ovviare all'errore dato dall'accento presente nel valore


                    JSONObject coord = c.getJSONObject(TAG_LOCATION);
                    String latitude = coord.getString(TAG_LAT);
                    String longitude = coord.getString(TAG_LONG);


                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(TAG_DENOMINAZIO, denominazio);
                    map.put(TAG_INDIRIZZO, luogo);
                    map.put(TAG_LAT, latitude);
                    map.put(TAG_LONG, longitude);

                    jsonlist.add(map);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}