package com.example.pierangelo.youthhostelslombardy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;


public class IndiceActivity extends Activity {

    String urlok = "http://www.dati.lombardia.it/resource/r9fb-4fm4.json";

    private Context context;

    public static final String TAG_DENOMINAZIO = "denominazione";
    public static final String TAG_COMUNE = "comune_provincia";
    public static final String TAG_PROVINCIA = "prov";
    public static final String TAG_INDIRIZZO = "indirizzo";
    public static final String TAG_POSTI = "numero_posti_letto";
    public static final String TAG_APERTURA = "periodo_apertura";
    public static final String TAG_TELEFONO = "telefono";
    public static final String TAG_SITO = "sito_internet"; ///array
    public static final String TAG_URL = "url";
    public static final String TAG_LOCATION = "location"; /// array
    public static final String TAG_LAT = "latitude";
    public static final String TAG_LONG = "longitude";

    public static final String TAG_DISTANZA = "";

    public static final String TAG_SORT = "";

    // tag meteo
    public static final String TAG_MAIN = "main";
    public static final String TAG_TEMP = "temp";
    public static final String TAG_WEATHER = "weather"; // array
    public static final String TAG_ICON = "icon";
    public String temperatura;
    public String icon;

    private LocationManager locationManager;
    private Location start;
    private Location loc;



    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();

    public IndiceActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indice);

        new CreaLista(IndiceActivity.this).execute();

    }


    class CreaLista extends AsyncTask<String, Void, Void> {

        private ProgressDialog dialog;
        private IndiceActivity indiceActivity;

        public CreaLista(IndiceActivity indiceActivity) {
            this.indiceActivity = indiceActivity;

            context = indiceActivity;

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

            ///ArrayAdapter -> presi da TagAdapter
            ArrayList myjson = new ArrayList(jsonlist);
            TagAdapter adapter = new TagAdapter(IndiceActivity.this, myjson);
            ListView list = (ListView) findViewById(R.id.listHostel);
            adapter.notifyDataSetChanged();
            list.setAdapter(adapter);
            //// DetailView
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(IndiceActivity.this, DetailActivity.class);
                    intent.putExtra("denominazione", jsonlist.get(position).get(TAG_DENOMINAZIO));
                    intent.putExtra("comune", jsonlist.get(position).get("comune_provincia"));
                    intent.putExtra("indirizzo", jsonlist.get(position).get(TAG_INDIRIZZO));
                    intent.putExtra("provincia", jsonlist.get(position).get(TAG_PROVINCIA));
                    intent.putExtra("letti", jsonlist.get(position).get(TAG_POSTI));
                    intent.putExtra("periodo", jsonlist.get(position).get(TAG_APERTURA));
                    intent.putExtra("telefono", jsonlist.get(position).get(TAG_TELEFONO));
                    intent.putExtra("url", jsonlist.get(position).get(TAG_URL));
                    intent.putExtra("latitude", jsonlist.get(position).get(TAG_LAT));
                    intent.putExtra("longitude", jsonlist.get(position).get(TAG_LONG));
                    startActivity(intent);
                }
            });  ///Fine Detailink
        }



        @Override
        protected Void doInBackground(String... params) {

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            loc = Utils.getLastBestLocation(locationManager);
            double slat = loc.getLatitude();
            double slon = loc.getLongitude();

            start = new Location("start");
            start.setLatitude(slat);
            start.setLongitude(slon);


            JSONParser jParser = new JSONParser(); // get JSON data from URL

            String par = jParser.makeServiceCall(urlok, JSONParser.GET);



            try {
                JSONArray json = new JSONArray(par);
                Log.d(String.valueOf(json.length()), "misuratore");
                for (int i = 0; i < json.length(); i++) {

                    JSONObject c = json.getJSONObject(i);
                    final String denominazione = c.getString(TAG_DENOMINAZIO);
                    String comune = c.getString(TAG_COMUNE);
                    String indirizzo = c.getString(TAG_INDIRIZZO);
                    String provincia = c.getString(TAG_PROVINCIA);
                    String letti = c.getString(TAG_POSTI);
                    String periodo = c.getString(TAG_APERTURA);
                    String telefono = c.getString(TAG_TELEFONO);

                    JSONObject web = c.getJSONObject(TAG_SITO);
                    String url = web.getString(TAG_URL);

                    JSONObject coord = c.getJSONObject(TAG_LOCATION);
                    String latitude = coord.getString(TAG_LAT);
                    String longitude = coord.getString(TAG_LONG);

                    // parse json meteo
                    String urlmeteo = "http://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude +"&units=metric&appid=f0f46a3f5c0247de8bb71d3e7cb29560";
                    String mPar = jParser.makeServiceCall(urlmeteo, JSONParser.GET);
                    JSONObject mJson = new JSONObject(mPar);
                    try {
                        JSONObject mn = mJson.getJSONObject(TAG_MAIN);
                        temperatura = mn.getString(TAG_TEMP);
                        JSONArray wea = mJson.getJSONArray(TAG_WEATHER);
                        JSONObject w = wea.getJSONObject(0);
                        icon = w.getString(TAG_ICON);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } // fine parse


                    ////CREO PUNTO DI ARRIVO
                    double eLat = Double.parseDouble(latitude);
                    double eLong = Double.parseDouble(longitude);

                    Location end = new Location("end");
                    end.setLatitude(eLat);
                    end.setLongitude(eLong);

                    /// CALCOLO DISTANZA DA PUNTO START A PUNTO END
                    double distanza = start.distanceTo(end) / 1000;
                    final String distanz = String.format("%.2f", distanza);  ///limito a due i decimali dopo la virgola;
                    /// fine Calcolo distanza

                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(TAG_DENOMINAZIO, denominazione);
                    map.put(TAG_COMUNE, comune + ", (" + provincia + ")");
                    map.put(TAG_INDIRIZZO, indirizzo);
                    map.put(TAG_POSTI, letti);
                    map.put(TAG_APERTURA, periodo);
                    map.put(TAG_TELEFONO, telefono);
                    map.put(TAG_URL, url);
                    map.put(TAG_LAT, latitude);
                    map.put(TAG_LONG, longitude);
                    map.put(TAG_DISTANZA, distanz + " Km.");
                    map.put(TAG_SORT, distanz);

                    // meteo
                    map.put(TAG_TEMP, temperatura);
                    map.put(TAG_ICON, "http://openweathermap.org/img/w/" + icon + ".png");

                    jsonlist.add(map);

                    ///ordine jsolist
                    Collections.sort(jsonlist, new Comparator<HashMap<String, String>>() {
                        @Override
                        public int compare(HashMap<String, String> lhs, HashMap<String, String> rhs) {
                            return lhs.get(TAG_SORT).compareTo(rhs.get(TAG_SORT));
                        }
                    }); /// fine collections

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

}


