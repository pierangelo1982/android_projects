package com.sviluppo.pierangelo.workreport.Service;

import android.content.Context;
import android.util.Log;

import com.sviluppo.pierangelo.workreport.Model.Worker;
import com.sviluppo.pierangelo.workreport.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by pierangelo on 29/06/16.
 */
public class WorkerService extends BaseService {

    public interface WorkerAPI {
        @GET("/workers.json")
        Call<List<Worker>> getWorkerList();
    }

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://timesheet.web-dev.info")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
