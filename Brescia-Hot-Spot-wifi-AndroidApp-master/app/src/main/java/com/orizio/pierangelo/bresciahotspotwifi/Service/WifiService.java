package com.orizio.pierangelo.bresciahotspotwifi.Service;

import com.orizio.pierangelo.bresciahotspotwifi.HotSpot;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by pierangelo on 24/12/17.
 */

public class WifiService {

    public Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.dati.lombardia.it")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    public interface HotSpotApi {
        @GET("/resource/gh57-eadh.json")
        Call<List<HotSpot>> getTyreList();
    }
}
