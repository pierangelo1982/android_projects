package com.orizio.pierangelo.bresciahotspotwifi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goList(View view) {
        Intent goList = new Intent(getBaseContext(), ListActivity.class);
        startActivity(goList);
    }

    public void goMap(View view) {
        Intent goMaps = new Intent(getBaseContext(), MapsActivity.class);
        startActivity(goMaps);
    }
}
