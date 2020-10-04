package com.sviluppo.pierangelo.timesheet;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnEntra = (Button) findViewById(R.id.buttonEntra);
        btnEntra.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                // controllo se GPS è attivo, se si prosegui, altrimenti vai ad attivarlo
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                {
                    Toast.makeText(v.getContext(), "il GPS è attivo sul tuo device", Toast.LENGTH_SHORT).show();
                    Intent goLogin = new Intent(v.getContext(), LoginActivity.class);
                    startActivity(goLogin);
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
