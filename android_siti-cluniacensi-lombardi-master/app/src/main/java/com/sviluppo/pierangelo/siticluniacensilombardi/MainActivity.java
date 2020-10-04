package com.sviluppo.pierangelo.siticluniacensilombardi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLista = (Button) findViewById(R.id.buttonLista);
        btnLista.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent goIndice = new Intent(view.getContext(), IndiceActivity.class);
                startActivity(goIndice);
            }
        });

        Button btnMappa = (Button) findViewById(R.id.btnMappa);
        btnMappa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goMappa = new Intent(view.getContext(), MapsActivity.class);
                startActivity(goMappa);
            }
        });

    }
}
