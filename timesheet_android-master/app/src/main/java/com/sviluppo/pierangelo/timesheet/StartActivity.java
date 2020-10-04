package com.sviluppo.pierangelo.timesheet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class StartActivity extends AppCompatActivity implements View.OnClickListener
{
    public static final String MY_USER_PREFERENCE = "MyPrefsFile";

    private SimpleDateFormat sdf = new SimpleDateFormat("DD-MM-yyyy'T'HH:mm:ss");

    // ottengo data e ora attuali
    final String current_time = DateFormat.getDateTimeInstance().format(new Date());



    public static final String url_filtro = "http://timesheet.web-dev.info/timesheets.json?ricerca=";
    public String url_dettaglio = null;
    public String start_time = null;

    private LocationManager locationManager;
    private Location loc;

    public double slat = 0;
    public double slon = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // richiamo SharedPreferences
        SharedPreferences prefs = getSharedPreferences(MY_USER_PREFERENCE, MODE_PRIVATE);
        String restoredNome = prefs.getString("mNome", null);
        String restoredID = prefs.getString("mId", null);

        TextView txtBenvenuto = (TextView) findViewById(R.id.labelBenvenuto);
        txtBenvenuto.setText("Benvenuto " + restoredNome);


        TextView txtDate = (TextView) findViewById(R.id.labelDate);
        txtDate.setText(current_time);

        // GEOLOCAZIONE
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        loc = Utils.getLastBestLocation(locationManager);
        slat = loc.getLatitude();
        slon = loc.getLongitude();
        Log.d("posizione", String.valueOf(slat + slon));

        final RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_filtro + restoredID,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.d("test", response);
                        Button btnInizio = (Button) findViewById(R.id.btnInizio);
                        Button btnFine = (Button) findViewById(R.id.btnFine);
                        TextView labelInzioLavoro = (TextView) findViewById(R.id.labelInizioLavoro);

                        try
                        {
                            JSONArray jarray = new JSONArray(response);
                            int lunghezza_array = jarray.length() - 1; //ottengo la conta dell'array, per dire JSONObject di prendere l'ultima
                            JSONObject job = jarray.getJSONObject(lunghezza_array);//dico al jsonObject di controllare l'ultimo items dell'array

                            start_time = job.getString("inizio");
                            String fine = job.getString("fine");
                            // adesso gli dico di controllare se il campo  fine è vuoto
                            if (fine.length() < 5)// conto lunghezza campo fine xchè check null non funzionava, se inferiore a 5 vuol dire che è vuoto
                            {
                                btnInizio.setVisibility(View.GONE);
                                btnFine.setVisibility(View.VISIBLE);

                                // ottego solo time da datetime
                                Utils ut = new Utils();
                                String datainizio = null;
                                try {
                                    Date dInizio = sdf.parse(job.getString("inizio"));
                                    datainizio = ut.getDateTime(dInizio);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                labelInzioLavoro.setText("Sessione Iniziata alle:\n" + datainizio);
                                url_dettaglio = job.getString("url");
                            } else {
                                btnInizio.setVisibility(View.VISIBLE);
                                btnFine.setVisibility(View.GONE);
                                labelInzioLavoro.setText("Buon Lavoro");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("test", "non va");
            }
        });
        queue.add(stringRequest);

        // vai a storico
        Button btnStorico = (Button) findViewById(R.id.btnStorico);
        btnStorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goStorico = new Intent(v.getContext(), StoricoActivity.class);
                startActivity(goStorico);
            }
        });

        Button btnInizio = (Button) findViewById(R.id.btnInizio);
        Button btnFine = (Button) findViewById(R.id.btnFine);
        btnFine.setVisibility(View.GONE);
        btnInizio.setOnClickListener(this);
        btnFine.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        // uso switch x separare funzionalità dei due pulsanti inizio-fine in quanto entrambi sono (this).
        // Sarebbe stato piu opportuno fare i clicklistener del pulsante ma era tanto per provare.
        switch (v.getId()) {
            case R.id.btnInizio:

                String url = "http://timesheet.web-dev.info/timesheets.json";

                // richiamo SharedPreferences
                SharedPreferences prefs = getSharedPreferences(MY_USER_PREFERENCE, MODE_PRIVATE);
                String restoredNome = prefs.getString("mNome", null);
                String restoredID = prefs.getString("mId", null);

                // ottengo data
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String oggi = dateFormat.format(calendar.getTime());

                Log.d("ora esatta", oggi);
                try
                {
                    RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());
                    // Prepares POST data...
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("worker_id", restoredID);
                    jsonBody.put("data", oggi);
                    jsonBody.put("inizio", current_time);
                    jsonBody.put("latitudine", slat);
                    jsonBody.put("longitudine", slon);
                    final String mRequestBody = jsonBody.toString();
                    // Volley request
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            Log.i("VOLLEY", response);
                        }
                    }, new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Log.e("VOLLEY", error.toString());
                        }
                    }) {
                        @Override
                        public String getBodyContentType()
                        {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError
                        {
                            try
                            {
                                return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee)
                            {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                                        mRequestBody, "utf-8");
                                return null;
                            }
                        }
                    };
                    requestQueue.add(request);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                // refresh activity x passare a fine
                Intent refresh = new Intent(v.getContext(), StartActivity.class);
                startActivity(refresh);

                break;

            case R.id.btnFine:
                // ottengo data e ora attuali
                try
                {
                    RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());
                    // Prepares POST data...
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("fine", current_time);
                    jsonBody.put("latitudine_fine", slat);
                    jsonBody.put("longitudine_fine", slon);
                    final String mRequestBody = jsonBody.toString();
                    // Volley request
                    StringRequest request = new StringRequest(Request.Method.PUT, url_dettaglio, new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            Log.i("VOLLEY", response);
                        }
                    }, new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Log.e("VOLLEY", error.toString());
                        }
                    }) {
                        @Override
                        public String getBodyContentType()
                        {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError
                        {
                            try
                            {
                                return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                                        mRequestBody, "utf-8");
                                return null;
                            }
                        }
                    };
                    requestQueue.add(request);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //pagina chiusura
                Intent goFinish = new Intent(v.getContext(), CloseActivity.class);
                goFinish.putExtra("url_dettaglio", url_dettaglio); // passo l'url dettaglio alla prossima activity x resoconto
                startActivity(goFinish);

                break;
            default:
                break;
        }

    }
}