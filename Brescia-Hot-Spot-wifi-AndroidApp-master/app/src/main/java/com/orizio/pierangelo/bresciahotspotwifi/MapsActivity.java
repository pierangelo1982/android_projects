package com.orizio.pierangelo.bresciahotspotwifi;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.orizio.pierangelo.bresciahotspotwifi.Service.WifiService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();
    Location location;
    double mylatitudine;
    double mylongitudine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Assume thisActivity is the current activity
        final int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

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

                    double lati = 45.5411672;
                    double longi = 10.2193481;
                    LatLng myplace = new LatLng(lati, longi);

                    Log.i("test", response.body().get(0).getUbicazione());
                    List<HotSpot> items = response.body();
                    for (int i = 0; i < items.size(); i++) {
                        HotSpot item = items.get(i);
                        String id = item.getId();
                        String ubicazione = item.getUbicazione();
                        String tipologia = item.getTipologia();
                        String latitudine = item.getLatitudine();
                        String longitudine = item.getLongitudine();

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

                        } else {
                            distanza = "(gp disattivato)";
                        }

                        Log.i("distanza", distanza);

                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("id", id);
                        map.put("ubicazione", ubicazione);
                        map.put("tipologia", tipologia);
                        map.put("coordinate", "lat: " + latitudine + " - long: " + longitudine);
                        map.put("distanza", distanza);
                        jsonlist.add(map);

                        ////CREO Coordinate per loop marker
                        double eLat = Double.parseDouble(latitudine);
                        double eLong = Double.parseDouble(longitudine);
                        mMap.addMarker(new MarkerOptions().position(new LatLng(eLat, eLong)).title(distanza)
                                .snippet(ubicazione));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myplace, 14));
                    }

                }
            }


            @Override
            public void onFailure(Call<List<HotSpot>> call, Throwable t) {

            }
        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }
}
