package com.sviluppo.pierangelo.wifibergamo;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailActivity extends Activity implements OnMapReadyCallback
{

    private Double latitudine;
    private Double longitudine;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle extra = getIntent().getExtras();

        /// ottengo le coordinate dal json e le converto in double per passarle alla mappa
        String mLat = extra.getString("latitude");
        String mLon = extra.getString("longitude");
        latitudine = Double.parseDouble(mLat);
        longitudine = Double.parseDouble(mLon);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        // Add a marker from coordinate get json.
        LatLng myplace = new LatLng(latitudine, longitudine);
        map.addMarker(new MarkerOptions().position(myplace));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myplace, 10));
    }

}
