package com.sviluppo.pierangelo.workreport.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.sviluppo.pierangelo.workreport.Model.Customer;
import com.sviluppo.pierangelo.workreport.Model.Timesheet;
import com.sviluppo.pierangelo.workreport.R;
import com.sviluppo.pierangelo.workreport.Service.BaseService;
import com.sviluppo.pierangelo.workreport.Service.CustomerService;
import com.sviluppo.pierangelo.workreport.Service.TimesheetService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class InizioFragment extends Fragment {

    public static final String MY_USER_PREFERENCE = "MyLogFile";
    SharedPreferences prefs;
    int m_userId;


    Button btnStart;
    Spinner customerSpinner;
    ArrayList<String> countryList;

    int customer_id;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_inizio, container, false);

        // richiamo Id user da passare poi in salva
        SharedPreferences prefs = getActivity().getSharedPreferences(MY_USER_PREFERENCE, MODE_PRIVATE);
        final int m_userId = Integer.parseInt(prefs.getString("mId", null));

        TimesheetService.TimesheetApi checkservice = new BaseService().getRetrofit().create(TimesheetService.TimesheetApi.class);
        Call<List<Timesheet>> call = checkservice.getTimesheetList(m_userId);
        call.enqueue(new Callback<List<Timesheet>>() {
            @Override
            public void onResponse(Call<List<Timesheet>> call, Response<List<Timesheet>> response) {
                if (response.isSuccessful()) {
                    List<Timesheet> timesheets = response.body();
                    int i = timesheets.size() - 1;
                    Log.d("listato:", String.valueOf(timesheets.get(i).getWorkerId()));
                    if (timesheets.get(i).getFine() == null) {
                        Fragment fragment = new FineFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("tmp_inizio", timesheets.get(i).getInizio());
                        bundle.putInt("timesheetID", timesheets.get(i).getId());
                        fragment.setArguments(bundle); // invio Id del record da aggiornare.
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.fragment_contenitore_start_activity, fragment);
                        transaction.commit();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Timesheet>> call, Throwable t) {

            }
        });

        customerSpinner = (Spinner) view.findViewById(R.id.spinner_start_cliente);

        final CustomerService.CustomerAPI customerAPI = new BaseService().getRetrofit().create(CustomerService.CustomerAPI.class);
        final Call<List<Customer>> callCustomer = customerAPI.getCustomerList();
        callCustomer.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if (response.isSuccessful()) {
                    countryList = new ArrayList<>();
                    final List<Customer> customers = response.body();
                    for (int i = 0; i < customers.size(); i++)
                    {
                        countryList.add(customers.get(i).getDenominazione());
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, countryList);
                    arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    customerSpinner.setAdapter(arrayAdapter);
                    customerSpinner.setContentDescription(countryList.toString());

                    customerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            customer_id = customers.get(position).getId();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {

            }
        });

        btnStart = (Button) view.findViewById(R.id.btnStartTask);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                TimesheetService.TimesheetApi serviceTimesheet = new BaseService().getRetrofit().create(TimesheetService.TimesheetApi.class);

                Timesheet timesheet = new Timesheet();
                timesheet.setWorkerId(m_userId);
                timesheet.setCustumerId(customer_id);
                timesheet.setData(dateFormat.format(calendar.getTime()));
                timesheet.setInizio(DateFormat.getDateTimeInstance().format(new Date()));
                Log.d("identifica:", String.valueOf(m_userId));

                Call<Timesheet> callTimesheet = serviceTimesheet.addTimesheet(timesheet);
                callTimesheet.enqueue(new Callback<Timesheet>() {
                    @Override
                    public void onResponse(Call<Timesheet> call, Response<Timesheet> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Task Iniziata", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Timesheet> call, Throwable t) {

                    }
                });
            }


        });


        return view;
    }

}
