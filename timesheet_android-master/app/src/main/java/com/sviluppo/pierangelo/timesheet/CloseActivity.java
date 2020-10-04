package com.sviluppo.pierangelo.timesheet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CloseActivity extends AppCompatActivity
{

    private float tot_ore;

    float latitudine;
    float longitudine;
    float latitudine_fine;
    float longitudine_fine;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close);

        // prendo url_dettaglio inviato da Activity precedente
        Bundle extra = getIntent().getExtras();
        String url = extra.getString("url_dettaglio");
        Log.d("indirizzo", url);

        // parse json con Volley
        final RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.d("test", response);
                        try
                        {
                            JSONObject job = new JSONObject(response);
                            tot_ore = Float.parseFloat(job.getString("ore"));
                            TextView totOre = (TextView) findViewById(R.id.txtOre);
                            totOre.setText(String.format("%.2f", tot_ore));

                            final String inizio = job.getString("inizio");
                            final String fine = job.getString("fine");

                            latitudine = Float.parseFloat(job.getString("latitudine"));
                            longitudine = Float.parseFloat(job.getString("longitudine"));
                            latitudine_fine = Float.parseFloat(job.getString("latitudine_fine"));
                            longitudine_fine = Float.parseFloat(job.getString("longitudine_fine"));
                            Log.d("Posizione Inizio:", String.valueOf(latitudine + " " + longitudine));
                            Log.d("Posizione Fine:", String.valueOf(latitudine_fine + " " + longitudine_fine));

                            // mappa
                            MapFragment mapFragment = (MapFragment) getFragmentManager()
                                    .findFragmentById(R.id.map);
                            mapFragment.getMapAsync(new OnMapReadyCallback()
                            {
                                @Override
                                public void onMapReady(GoogleMap map)
                                {
                                    // Add a marker from coordinate get json.
                                    LatLng myplace = new LatLng(latitudine, longitudine);
                                    LatLng myplace_fine = new LatLng(latitudine_fine, longitudine_fine);
                                    map.addMarker(new MarkerOptions().position(myplace).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).title("INIZIO")
                                            .snippet(inizio));
                                    map.addMarker(new MarkerOptions().position(myplace_fine).title("FINE")
                                            .snippet(fine));
                                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(myplace, 7));
                                }
                            });

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


    }
}

    /*
    // mappa
    MapFragment mapFragment = (MapFragment) getFragmentManager()
            .findFragmentById(R.id.map);
mapFragment.getMapAsync(new OnMapReadyCallback()
        {
@Override
public void onMapReady(GoogleMap map) {
        // Add a marker from coordinate get json.
        LatLng myplace = new LatLng(latitudine, longitudine);
        LatLng myplace_fine = new LatLng(latitudine_fine, longitudine_fine);
        map.addMarker(new MarkerOptions().position(myplace));
        map.addMarker(new MarkerOptions().position(myplace_fine));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myplace, 7));
        }

                */