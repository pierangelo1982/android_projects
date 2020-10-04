package com.sviluppo.pierangelo.workreport.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sviluppo.pierangelo.workreport.Helper.DateTimeHelper;
import com.sviluppo.pierangelo.workreport.Model.Customer;
import com.sviluppo.pierangelo.workreport.Model.Timesheet;
import com.sviluppo.pierangelo.workreport.R;
import com.sviluppo.pierangelo.workreport.Service.BaseService;
import com.sviluppo.pierangelo.workreport.Service.TimesheetService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FineFragment extends Fragment {

    TextView txt_data_inizio, txt_inizio_ora;
    EditText edit_note;
    Button btnFine;

    int timesheetID;
    String data_inizio;
    String note;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fine, container, false);

        Bundle b = getArguments();
        timesheetID = b.getInt("timesheetID");
        data_inizio = b.getString("tmp_inizio");

        txt_data_inizio = (TextView) view.findViewById(R.id.fragment_fine_data_inizio);
        txt_inizio_ora = (TextView) view.findViewById(R.id.ora_fine_fragment);

        String tmp_data_inizio = new DateTimeHelper().myFormatDate(data_inizio);
        String tmp_ora_inizio = new DateTimeHelper().myFormatTime(data_inizio);

        txt_data_inizio.setText(tmp_data_inizio);
        txt_inizio_ora.setText(tmp_ora_inizio);

        edit_note = (EditText) view.findViewById(R.id.fragment_fine_note);

        btnFine = (Button) view.findViewById(R.id.btnFineTask);
        btnFine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Timesheet timesheet = new Timesheet();
                timesheet.setFine(DateFormat.getDateTimeInstance().format(new Date()));
                timesheet.setNote(edit_note.getText().toString());

                TimesheetService.TimesheetApi service = new BaseService().getRetrofit().create(TimesheetService.TimesheetApi.class);
                Call<Timesheet> call = service.closeOpenTimesheet(timesheetID, timesheet);
                call.enqueue(new Callback<Timesheet>() {
                    @Override
                    public void onResponse(Call<Timesheet> call, Response<Timesheet> response) {
                        if (response.isSuccessful()) {
                            Fragment fragment = new TimesheetHistory();
                            FragmentManager manager = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.replace(R.id.fragment_contenitore_start_activity, fragment);
                            transaction.commit();
                            Toast.makeText(getContext(), "Task Ultimata", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Timesheet> call, Throwable t) {
                        Toast.makeText(getContext(), "Task non chiusa", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }

}
