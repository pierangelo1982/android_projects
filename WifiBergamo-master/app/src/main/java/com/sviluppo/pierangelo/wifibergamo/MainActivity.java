package com.sviluppo.pierangelo.wifibergamo;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /// external link to dati.regionelombardia.it
        TextView linkRegione = (TextView) findViewById(R.id.regioneLombardia);
        linkRegione.setText(Html.fromHtml("<a href=http://dati.regionelombardia.it> Dati forniti da Regione lombardia "));
        linkRegione.setMovementMethod(LinkMovementMethod.getInstance());

        // button link lista
        Button linkLista = (Button) findViewById(R.id.btnLista);
        linkLista.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Toast.makeText(v.getContext(), "il GPS è attivo sul tuo device", Toast.LENGTH_SHORT).show();
                    Intent indice = new Intent(v.getContext(), IndiceActivity.class);
                    startActivity(indice);
                }
                else
                {
                    showGPSDisabledAlertToUser();
                }
            }
        });

        ///button link map
        Button linkMappa = (Button) findViewById(R.id.btnMappa);
        linkMappa.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Toast.makeText(v.getContext(), "il GPS è attivo sul tuo device", Toast.LENGTH_SHORT).show();
                    Intent mappa = new Intent(v.getContext(), MapsActivity.class);
                    startActivity(mappa);
                }
                else
                {
                    showGPSDisabledAlertToUser();
                }
            }
        });

    }

    private void showGPSDisabledAlertToUser()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Questa APP richiede l'uso del GPS. Vuoi Attivarlo?")
                .setCancelable(false)
                .setPositiveButton("Attiva GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id)
                            {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


}


