package com.sviluppo.pierangelo.androidretrofitgson;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by pierangelo on 18/06/16.
 */
public interface CluniacensiApi {

    @GET("/api/cluniacensi")
    Call<List<Cluniacensi>> getCluniacensiList();
}
