package com.sviluppo.pierangelo.siticluniacensilombardi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class IndiceActivity extends AppCompatActivity
{

    public static final String url = "http://sanpietroinlamosa.it/api/cluniacensi/";

    public static final String TAG_FIELDS = "fields";
    public static final String TAG_TITOLO = "titolo";
    public static final String TAG_LATITUDINE = "latitudine";
    public static final String TAG_LONGITUDINE = "longitudine";
    public static final String TAG_CITTA = "citta";
    public static final String TAG_WEB = "web";
    public static final String TAG_INTRODUZIONE = "introduzione";
    public static final String TAG_IMAGE = "image";

    private Context context;

    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();

    public IndiceActivity() {
    }



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indice);

        new CreaLista(IndiceActivity.this).execute();

    }


    class CreaLista extends AsyncTask<String, Void, String>
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
            this.dialog.setMessage("Dati in caricamento");
            this.dialog.show();
        }


        @Override
        protected void onPostExecute(final String success)
        {

            if (dialog.isShowing())
            {
                dialog.dismiss();
            }

            /*ListAdapter listAdapter = new SimpleAdapter(context, jsonlist, R.layout.contenuto_lista,
                    new String[]{TAG_TITOLO, TAG_CITTA},
                    new int[]{R.id.labelTitolo, R.id.labelLocation});

            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(listAdapter); */

            ///ArrayAdapter -> presi da TagAdapter
            ArrayList myjson = new ArrayList(jsonlist);
            TagAdapter adapter = new TagAdapter(IndiceActivity.this, myjson);
            ListView list = (ListView) findViewById(R.id.listView);
            adapter.notifyDataSetChanged();
            list.setAdapter(adapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(view.getContext(), DetailActivity.class);
                    intent.putExtra("image", jsonlist.get(i).get(TAG_IMAGE));
                    intent.putExtra("titolo", jsonlist.get(i).get(TAG_TITOLO));
                    intent.putExtra("citta", jsonlist.get(i).get(TAG_CITTA));
                    intent.putExtra("latitudine", jsonlist.get(i).get(TAG_LATITUDINE));
                    intent.putExtra("longitudine", jsonlist.get(i).get(TAG_LONGITUDINE));
                    intent.putExtra("web", jsonlist.get(i).get(TAG_WEB));
                    intent.putExtra("introduzione", jsonlist.get(i).get(TAG_INTRODUZIONE));
                    startActivity(intent);
                }
            });

        }


        @Override
        protected String doInBackground(String... strings)
        {
            JSONParser jpar = new JSONParser();
            String myj = jpar.getParser(url);

            try {
                JSONArray jarray = new JSONArray(myj);

                for (int i = 0; i < jarray.length(); i++)
                {
                    JSONObject c = jarray.getJSONObject(i);
                    JSONObject b = c.getJSONObject(TAG_FIELDS); //campo fields sun array
                    String titolo = b.getString(TAG_TITOLO);
                    String latitudine = b.getString(TAG_LATITUDINE);
                    String longitudine = b.getString(TAG_LONGITUDINE);
                    String citta = b.getString(TAG_CITTA);
                    String web = b.getString(TAG_WEB);
                    String introduzione = b.getString(TAG_INTRODUZIONE);
                    String image = b.getString(TAG_IMAGE);


                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(TAG_TITOLO, titolo);
                    map.put(TAG_CITTA, citta);
                    map.put(TAG_WEB, "web");
                    map.put(TAG_LATITUDINE, latitudine);
                    map.put(TAG_LONGITUDINE, longitudine);
                    map.put(TAG_INTRODUZIONE, introduzione);
                    map.put(TAG_IMAGE, "http://sanpietroinlamosa.it/media/" + image);

                    jsonlist.add(map);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


    }
}
