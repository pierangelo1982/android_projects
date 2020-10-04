package com.sviluppo.pierangelo.timesheet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DettaglioActivity extends AppCompatActivity {
    private String data;
    private String inizio;
    private String fine;
    float ore;

    float latitudine;
    float longitudine;
    float latitudine_fine;
    float longitudine_fine;


    SimpleDateFormat sdf = new SimpleDateFormat("DD-MM-yyyy'T'HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio);

        Bundle extra = getIntent().getExtras();
        String id = extra.getString("id");

        String url = "http://timesheet.web-dev.info/timesheets/" + id + ".json";

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
                            data = job.getString("data");
                            ore = Float.parseFloat(job.getString("ore"));
                            latitudine = Float.parseFloat(job.getString("latitudine"));
                            longitudine = Float.parseFloat(job.getString("longitudine"));
                            latitudine_fine = Float.parseFloat(job.getString("latitudine_fine"));
                            longitudine_fine = Float.parseFloat(job.getString("longitudine_fine"));

                            // ottengo solo le ore da datetime
                            Utils ut = new Utils();
                            inizio = null;
                            fine = null;
                            try {
                                Date dInizio = sdf.parse(job.getString("inizio"));
                                Date dFine = sdf.parse(job.getString("fine"));
                                inizio = ut.getDateTime(dInizio);
                                fine = ut.getDateTime(dFine);

                                TextView txtData = (TextView) findViewById(R.id.txtData);
                                txtData.setText(data);

                                TextView txtInizio = (TextView) findViewById(R.id.txtInizio);
                                txtInizio.setText(inizio);

                                TextView txtFine = (TextView) findViewById(R.id.txtFine);
                                txtFine.setText(fine);

                                TextView txtOre = (TextView) findViewById(R.id.txtOre);
                                txtOre.setText(String.format("%.2f", ore));

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            Log.d("posizione finale", String.valueOf(latitudine_fine + longitudine_fine));

                            MapFragment mapFragment = (MapFragment) getFragmentManager()
                                    .findFragmentById(R.id.map);
                            mapFragment.getMapAsync(new OnMapReadyCallback() {
                                @Override
                                public void onMapReady(GoogleMap map) {
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
