package com.sviluppo.pierangelo.siticluniacensilombardi;


import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    public static final String url = "http://sanpietroinlamosa.it/api/cluniacensi/";

    public static final String TAG_FIELDS = "fields";
    public static final String TAG_TITOLO = "titolo";
    public static final String TAG_LATITUDINE = "latitudine";
    public static final String TAG_LONGITUDINE = "longitudine";
    public static final String TAG_CITTA = "citta";
    public static final String TAG_WEB = "web";
    public static final String TAG_INTRODUZIONE = "introduzione";
    public static final String TAG_IMAGE = "image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public void onStart()
    {
        super.onStart();

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jarray = new JSONArray(response);
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

                                LatLng myplace = new LatLng(Float.parseFloat(latitudine), Float.parseFloat(longitudine));
                                mMap.addMarker(new MarkerOptions().position(myplace).title(titolo));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("vedi", String.valueOf(error));
            }
        });

        queue.add(stringRequest);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng point = new LatLng(45.6152989,9.468099);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 8));
    }
}