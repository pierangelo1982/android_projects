package com.orizio.pierangelo.bresciahotspotwifi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.orizio.pierangelo.bresciahotspotwifi.R.id.map;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    TextView id;
    TextView ubicazione;
    TextView tipologia;
    //TextView coordinate;
    TextView distanza;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        id = (TextView)findViewById(R.id.textId);
        ubicazione = (TextView)findViewById(R.id.textUbicazione);
        tipologia = (TextView)findViewById(R.id.textTipologia);
        //coordinate = (TextView)findViewById(R.id.textCoordinate);
        distanza = (TextView)findViewById(R.id.textDistanza);

        Bundle extra = getIntent().getExtras();
        id.setText(extra.getString("id"));
        ubicazione.setText(extra.getString("ubicazione"));
        tipologia.setText(extra.getString("tipologia"));
        //coordinate.setText("Lat: " + extra.getString("latitudine") + " Long: " + extra.getString("longitudine"));
        distanza.setText("distance: " + extra.getString("distanza"));

    }

    @Override
    public void onMapReady(GoogleMap map) {
        Bundle extra = getIntent().getExtras();

        double tlat = Double.parseDouble(extra.getString("latitudine"));
        double tlon = Double.parseDouble(extra.getString("longitudine"));

        LatLng myplace = new LatLng(tlat, tlon);
        map.addMarker(new MarkerOptions().position(myplace));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myplace, 14));
    }
}
