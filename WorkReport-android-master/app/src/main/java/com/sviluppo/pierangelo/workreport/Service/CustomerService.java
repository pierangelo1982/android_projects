package com.sviluppo.pierangelo.workreport.Service;

import com.sviluppo.pierangelo.workreport.Model.Customer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by pierangelo on 22/10/16.
 */

public class CustomerService extends BaseService {

    public interface CustomerAPI
    {
        @GET("/custumers.json")
        Call<List<Customer>> getCustomerList();

        @GET("/custumers/{detail_id}.json")
        Call<Customer> getCustomer(@Path("detail_id") int id);

        @POST("/custumers.json")
        Call<Customer> addCustomer(@Body Customer customer);

        @PUT("custumers/{detail_id}.json")
        Call<Customer> editCustomer(@Path("detail_id") int id, @Body Customer customer);

    }
}
