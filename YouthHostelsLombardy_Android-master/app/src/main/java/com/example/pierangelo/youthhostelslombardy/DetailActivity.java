package com.example.pierangelo.youthhostelslombardy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class DetailActivity extends Activity implements OnMapReadyCallback  {

    private String titlemap;
    private double latitudine;
    private double longitudine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        /// map fragment
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        //// Passo i dati dettaglio
        Bundle extra = getIntent().getExtras();

        String data = extra.getString("denominazione");
        TextView denominazio = (TextView) findViewById(R.id.labelDenominazione);
        denominazio.setText(data);

        String comune = extra.getString("comune");
        String indirizzo = extra.getString("indirizzo");
        TextView comu = (TextView) findViewById(R.id.labelComune);
        comu.setText(indirizzo + " - " + comune);

        TextView tel = (TextView) findViewById(R.id.labelTelefono);
        tel.setText("Tel." + extra.getString("telefono"));

        TextView web = (TextView) findViewById(R.id.labelWeb);
        web.setText(extra.getString("url"));

        TextView letti = (TextView) findViewById(R.id.labelLetti);
        letti.setText("Bed available: " + extra.getString("letti"));

        TextView apertura = (TextView) findViewById(R.id.labelApertura);
        apertura.setText("Open: " + extra.getString("periodo"));
        /// fine dati dettaglio


        /// definisco titolo che esce quando clicchi sul marker della mappa
        titlemap = extra.getString("denominazione");

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
        map.addMarker(new MarkerOptions().position(myplace).title(titlemap));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myplace, 10));
    }
}
