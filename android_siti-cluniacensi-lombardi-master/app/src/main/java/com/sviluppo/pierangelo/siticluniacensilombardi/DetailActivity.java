package com.sviluppo.pierangelo.siticluniacensilombardi;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;


public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private String title = null;
    private Float latitudine;
    private Float longitudine;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //// Passo i dati dettaglio
        Bundle extra = getIntent().getExtras();

        String lat = extra.getString("latitudine");
        String lon = extra.getString("longitudine");
        latitudine = Float.parseFloat(lat);
        longitudine = Float.parseFloat(lon);

        title = extra.getString("titolo");

        TextView titolo = (TextView) findViewById(R.id.labelTitolo);
        titolo.setText(extra.getString("titolo"));

        TextView citta = (TextView) findViewById(R.id.labelLocation);
        citta.setText(extra.getString("citta"));

        TextView introduzione = (TextView) findViewById(R.id.lavelIntroduzione);
        introduzione.setText(extra.getString("introduzione"));

        ImageView image = (ImageView) findViewById(R.id.labelImage);
        Picasso.with(this).load(extra.getString("image")).into(image);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map)
    {
        LatLng myplace = new LatLng(latitudine, longitudine);
        map.addMarker(new MarkerOptions().position(myplace).title(title));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myplace, 10));
    }
}
