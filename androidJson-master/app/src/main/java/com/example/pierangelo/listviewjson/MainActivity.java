package com.example.pierangelo.listviewjson;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends Activity {

    String urlok = "https://www.dati.lombardia.it/resource/r9fb-4fm4.json";

    private Context context;

    private static final String TAG_DENOMINAZIO = "denominazione";
    private static final String TAG_COMUNE = "comune_provincia";
    private static final String TAG_INDIRIZZO = "indirizzo";

    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new ProgressTask(MainActivity.this).execute();

    }


    private class ProgressTask extends AsyncTask<String, Void, Void> {

        private ProgressDialog dialog;

        private MainActivity activity;

        public ProgressTask(MainActivity activity) {

            this.activity = activity;

            context = activity;

            dialog = new ProgressDialog(context);

        }

        private Context context;

        protected void onPreExecute() {
            this.dialog.setMessage("Gira la Ruota gira la ruota");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(final Void success) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            ListAdapter adapter = new SimpleAdapter(context, jsonlist, R.layout.contenuto_lista,
                    new String[]{TAG_DENOMINAZIO, TAG_COMUNE, TAG_INDIRIZZO},
                    new int[]{R.id.labelDenominazione, R.id.labelComune, R.id.labelIndirizzo});

           /* setListAdapter(adapter);
            lv = getListView(); */
            //ListView list = (ListView) findViewById(R.id.listViewok);
            //list.setAdapter(adapter);


            ListView list = (ListView) findViewById(R.id.listViewok);
            list.setAdapter(adapter);
            ///lv = getListView();
            /*setListAdapter(adapter);
            lv = getListView(); */

        }

        
        protected Void doInBackground(String... params) {

            ///String urlok = "http://falegnameriapea.it/json/index/";

            JSONParser jParser = new JSONParser(); // get JSON data from URL

            JSONArray json = jParser.getData(urlok);

            for (int i = 0; i < json.length(); i++) {
                try {
                    JSONObject c = json.getJSONObject(i);
                    String denominazione = c.getString(TAG_DENOMINAZIO);
                    String comune = c.getString(TAG_COMUNE);
                    String indirizzo = c.getString(TAG_INDIRIZZO);

                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put(TAG_DENOMINAZIO, denominazione);
                    map.put(TAG_COMUNE, comune);
                    map.put(TAG_INDIRIZZO, indirizzo);

                    jsonlist.add(map);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }



    }


}

