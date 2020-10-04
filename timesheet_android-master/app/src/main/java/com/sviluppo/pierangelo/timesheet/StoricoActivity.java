package com.sviluppo.pierangelo.timesheet;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

public class StoricoActivity extends AppCompatActivity
{

    String url = "http://timesheet.web-dev.info/timesheets.json";

    SimpleDateFormat sdf = new SimpleDateFormat("DD-MM-yyyy'T'HH:mm:ss");

    private Context context;


    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storico);

        new StoricoTask(StoricoActivity.this).execute();


    }

   public class StoricoTask extends AsyncTask<String, String, String>
   {
       private StoricoActivity storicoActivity;

       public StoricoTask(StoricoActivity storicoActivity)
       {
           this.storicoActivity = storicoActivity;
           context = storicoActivity;
       }

       @Override
       protected String doInBackground(String... params)
       {
            /*
           HttpURLConnection connection = null;
           BufferedReader reader = null; */

           try {

               /*
               URL url = new URL("http://timesheet.web-dev.info/timesheets.json");
               connection = (HttpURLConnection) url.openConnection();
               connection.connect();

               InputStream stream = connection.getInputStream();
               reader = new BufferedReader(new InputStreamReader(stream));
               StringBuffer buffer = new StringBuffer();
               String line = "";
               while ((line = reader.readLine()) != null)
               {
                   buffer.append(line);
               }  */

               String url = "http://timesheet.web-dev.info/timesheets.json";
               String cstring = Utils.myArray(url); // funzione sopra identata ricostruita in Utils x poterla riutilizzare
               JSONArray json = new JSONArray(cstring);

               for (int i=0; i < json.length(); i++)
               {
                   JSONObject job = json.getJSONObject(i);
                   String data = job.getString("data");
                   String inizio = job.getString("inizio");
                   String fine = job.getString("fine");
                   int iD = job.getInt("id");
                   float tot = Float.parseFloat(job.getString("ore"));

                   // ottieni solo time da datetime
                   Utils uti = new Utils(); // richiamo funzione getDateTime da Utils (riduce datetime a solo time
                   String sFine = null;
                   String sInizio = null;
                   try
                   {
                       Date dInizio = sdf.parse(inizio); // converto Stringa data in formato data con SimpleDateFormat (vedi header)
                       Date dFine = sdf.parse(fine);
                       sInizio = uti.getDateTime(dInizio); // chiamo funzione da Utils
                       sFine = uti.getDateTime(dFine);
                   } catch (ParseException e)
                   {
                       e.printStackTrace();
                   }

                   HashMap<String, String> map = new HashMap<String, String>();
                   map.put("id", String.valueOf(iD));
                   map.put("data", data);
                   map.put("inizio", "Dalle:" + sInizio + " - Alle:" + sFine);
                   map.put("fine", fine);
                   map.put("ore", "Totale Ore:" + " " + String.format("%.2f", tot));

                   jsonlist.add(map);
               }

           } catch (MalformedURLException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           } catch (JSONException e) {
               e.printStackTrace();
           }

           return null;

       }

       @Override
       protected void onPostExecute(final String success)
       {
           ListAdapter adapter = new SimpleAdapter(context, jsonlist, R.layout.contenuto_lista,
                   new String[]{"data", "inizio", "ore"},
                   new int[]{R.id.labelData, R.id.labelOre, R.id.labelTot});

           ListView list = (ListView) findViewById(R.id.listView);
           list.setAdapter(adapter);

           list.setOnItemClickListener(new AdapterView.OnItemClickListener()
           {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id)
               {
                   Intent goDettaglio = new Intent(view.getContext(), DettaglioActivity.class);
                   goDettaglio.putExtra("id", jsonlist.get(position).get("id").toString());
                   startActivity(goDettaglio);
               }
           });
       }
   }
}
