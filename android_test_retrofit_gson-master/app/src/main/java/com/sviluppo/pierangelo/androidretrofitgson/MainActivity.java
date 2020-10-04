package com.sviluppo.pierangelo.androidretrofitgson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    TextView txtLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtLabel = (TextView) findViewById(R.id.labelTxt);

        Gson gson = new GsonBuilder().registerTypeAdapter(Cluniacensi.class, new CluniacensiDeserializer())
                .create();

        final Retrofit retrofit = new Retrofit.Builder().baseUrl("http://sanpietroinlamosa.it")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CluniacensiApi service = retrofit.create(CluniacensiApi.class);
        Call<List<Cluniacensi>> call = service.getCluniacensiList();
        call.enqueue(new Callback<List<Cluniacensi>>() {
            @Override
            public void onResponse(Call<List<Cluniacensi>> call, Response<List<Cluniacensi>> response) {
                if (response.isSuccessful()) {
                    List<Cluniacensi> clun = response.body();

                    for (int i = 0; i < clun.size(); i++) {
                        //Cluniacensi cluniacensi = clun.get(i);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Cluniacensi>> call, Throwable t) {
                txtLabel.setText(t.toString());
            }
        });
    }
}
