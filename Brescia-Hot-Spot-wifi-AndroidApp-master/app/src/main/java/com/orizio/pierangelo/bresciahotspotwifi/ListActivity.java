package com.orizio.pierangelo.bresciahotspotwifi;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.orizio.pierangelo.bresciahotspotwifi.Service.WifiService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {

    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();
    Location location;
    double mylatitudine;
    double mylongitudine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Assume thisActivity is the current activity
        final int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        Log.i("permesso:", String.valueOf(permissionCheck));

        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this, new String[] {
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION },
                        1);
            }
        }

        if (permissionCheck == 0) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            mylatitudine = location.getLatitude();
            mylongitudine = location.getLongitude();
        }

        final WifiService.HotSpotApi hotSpotApi = new WifiService().getRetrofit().create(WifiService.HotSpotApi.class);
        Call<List<HotSpot>> call = hotSpotApi.getTyreList();
        call.enqueue(new Callback<List<HotSpot>>() {
            @Override
            public void onResponse(Call<List<HotSpot>> call, Response<List<HotSpot>> response) {
                if (response.isSuccessful()) {
                    Log.i("test", response.body().get(0).getUbicazione());
                    List<HotSpot> items = response.body();
                    for (int i = 0; i < items.size(); i++) {
                        HotSpot item = items.get(i);
                        String id = item.getId();
                        String ubicazione = item.getUbicazione();
                        String tipologia = item.getTipologia();
                        String latitudine = item.getLatitudine();
                        String longitudine = item.getLongitudine();

                        double ordine;
                        String distanza = "";
                        if (permissionCheck == 0) {
                            //ottengo mia posizione gps e generò un punto di partenza
                            Location start = new Location("start");
                            start.setLatitude(mylatitudine);
                            start.setLongitude(mylongitudine);
                            ////CREO PUNTO DI ARRIVO
                            double eLat = Double.parseDouble(latitudine);
                            double eLong = Double.parseDouble(longitudine);
                            Location end = new Location("end");
                            end.setLatitude(eLat);
                            end.setLongitude(eLong);

                            /// CALCOLO DISTANZA DA PUNTO START A PUNTO END
                            double distance = start.distanceTo(end) / 1000;
                            distanza = String.format("%.2f", distance) + " km.";  ///limito a due i decimali dopo la virgola;
                            ordine = distance;

                        } else {
                            distanza = "(gps disabled, impossible calculate distance)";
                            ordine = 0;
                        }

                        Log.i("distanza", distanza);

                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("id", id);
                        map.put("ubicazione", ubicazione);
                        map.put("tipologia", tipologia);
                        map.put("latitudine", latitudine);
                        map.put("longitudine", longitudine);
                        map.put("coordinate", "lat: " + latitudine + " - long: " + longitudine);
                        map.put("distanza", distanza);
                        map.put("ordine", String.valueOf(ordine));
                        jsonlist.add(map);

                        if (permissionCheck==0) {
                            ///ordine jsolist
                            Collections.sort(jsonlist, new Comparator<HashMap<String, String>>() {
                                @Override
                                public int compare(HashMap<String, String> lhs, HashMap<String, String> rhs) {
                                    return Integer.parseInt(String.valueOf(lhs.get("distanza").compareTo(rhs.get("distanza"))));
                                }
                            }); /// fine collections
                        }

                    }

                }

                ListView listView = (ListView) findViewById(R.id.listViewHotSpot);

                ListAdapter adapter = new SimpleAdapter(getBaseContext(), jsonlist, R.layout.listview_content,
                        new String[]{"id",
                                "ubicazione",
                                "tipologia",
                                "coordinate",
                                "distanza"},
                        new int[]{R.id.textId,
                                R.id.textUbicazione,
                                R.id.textTipologia,
                                R.id.textCoordinate,
                                R.id.textDistanza});

                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                        intent.putExtra("id", jsonlist.get(position).get("id"));
                        intent.putExtra("ubicazione", jsonlist.get(position).get("ubicazione"));
                        intent.putExtra("tipologia", jsonlist.get(position).get("tipologia"));
                        intent.putExtra("latitudine", jsonlist.get(position).get("latitudine"));
                        intent.putExtra("longitudine", jsonlist.get(position).get("longitudine"));
                        intent.putExtra("distanza", jsonlist.get(position).get("distanza"));
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<HotSpot>> call, Throwable t) {

            }
        });
    }

    public void goDistanza(View view) {
        Intent goDistanza = new Intent(this, ListActivity.class);
        startActivity(goDistanza);
    }

    public void goMap(View view) {
        Intent goMap = new Intent(ListActivity.this, MapsActivity.class);
        startActivity(goMap);
    }
}
