package com.sviluppo.pierangelo.workreport.Service;

import com.sviluppo.pierangelo.workreport.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pierangelo on 29/06/16.
 */
public class BaseService {

    public Retrofit getRetrofit()
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://timesheet.web-dev.info")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

}
