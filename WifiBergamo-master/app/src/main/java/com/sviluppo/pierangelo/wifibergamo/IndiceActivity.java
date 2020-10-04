package com.sviluppo.pierangelo.wifibergamo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class IndiceActivity extends Activity {

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
    private static final String TAG_DISTANZA = null;
    private static final String TAG_LATLONG = null;

    private Location loc;
    private Location start;

    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();

    public IndiceActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indice);

        new CreaLista(IndiceActivity.this).execute();

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener ascoltatore = new MyLocationListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ascoltatore);
    }


    private class CreaLista extends AsyncTask<String, Void, Void>
    {
        private ProgressDialog dialog;
        private IndiceActivity indiceActivity;

        public CreaLista(IndiceActivity indiceActivity)
        {
            this.indiceActivity = indiceActivity;

            context = indiceActivity;

            dialog = new ProgressDialog(context);
        }

        private Context context;

        protected void onPreExecute()
        {
            this.dialog.setMessage("Dati in Caricamento");
            this.dialog.show();

        }

        @Override
        protected void onPostExecute(final Void success)
        {
            if (dialog.isShowing())
            {
                dialog.dismiss();
            }

            ListAdapter adapter = new SimpleAdapter(context, jsonlist, R.layout.contenuto_lista,
                    new String[]{TAG_TIPOLOGIA, TAG_ENTE, TAG_DISTANZA},
                    new int[]{R.id.labelTipologia, R.id.labelEnte, R.id.labelLocation});

            ListView list = (ListView) findViewById(R.id.listView);
            list.setAdapter(adapter);

            //link dettaglio (DetailActivity
            list.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    Intent intent = new Intent(IndiceActivity.this, DetailActivity.class);
                    intent.putExtra("latitude", jsonlist.get(position).get(TAG_LATITUDE));
                    intent.putExtra("longitude", jsonlist.get(position).get(TAG_LONGITUDINE));
                    startActivity(intent);
                }
            });

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

                    map.put(TAG_TIPOLOGIA, "Posizione: " + tipologia);
                    map.put(TAG_ENTE, "Ente: " + ente);
                    map.put(TAG_LATITUDE, latitude);
                    map.put(TAG_LONGITUDINE, longitude);
                    map.put(TAG_DISTANZA, "DISTANZA: " + distanz + " Km.");
                    jsonlist.add(map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

    }
}
