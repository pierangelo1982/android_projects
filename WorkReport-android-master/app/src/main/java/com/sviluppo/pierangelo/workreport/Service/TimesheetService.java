package com.sviluppo.pierangelo.workreport.Service;

import com.sviluppo.pierangelo.workreport.Model.Timesheet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by pierangelo on 05/11/16.
 */

public class TimesheetService extends BaseService {

    public interface TimesheetApi {

        @GET("/timesheets.json?ricerca=")
        Call<List<Timesheet>> getTimesheetList(@Query("ricerca") int workerId);

        @POST("/timesheets.json")
        Call<Timesheet> addTimesheet(@Body Timesheet timesheet);

        @PUT("timesheets/{detail_id}.json")
        Call<Timesheet> closeOpenTimesheet(@Path("detail_id") int id, @Body Timesheet timesheet);
    }
}
