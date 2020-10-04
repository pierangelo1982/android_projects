package com.example.pierangelo.youthhostelslombardy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener ascoltatore = new MyLocationListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ascoltatore);


        Location test = new Location("test");
        double prova = test.getLatitude();
        Log.d("coordonate", String.valueOf(prova));


        /// dichiaro il pulsante e genero l'azione
        Button enterbtn = (Button) findViewById(R.id.buttonentra);
        enterbtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intento = new Intent(v.getContext(), IndiceActivity.class);
                startActivityForResult(intento, 0);
            }
        });


        /// pulsante mappa Lista
        Button gomappa = (Button) findViewById(R.id.buttonmappa);
        gomappa.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento2 = new Intent(v.getContext(), MapsActivity.class);
                startActivityForResult(intento2, 0);
            }
        });

        /// link sito regione Lombardia open data
        Button linkLombardia = (Button) findViewById(R.id.buttonLombardia);
        linkLombardia.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent linkLo = new Intent(Intent.ACTION_VIEW);
                linkLo.setData(Uri.parse("http://dati.lombardia.it"));
                startActivity(linkLo);
            }
        });

        /// link openWeather
        Button linkMeteo = (Button) findViewById(R.id.buttonMeteo);
        linkMeteo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent meteoLink = new Intent(Intent.ACTION_VIEW);
                meteoLink.setData(Uri.parse("http://openweathermap.org"));
                startActivity(meteoLink);
            }
        });

    }

}
