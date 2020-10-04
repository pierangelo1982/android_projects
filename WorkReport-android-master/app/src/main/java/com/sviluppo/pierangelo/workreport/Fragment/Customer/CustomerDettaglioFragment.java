package com.sviluppo.pierangelo.workreport.Fragment.Customer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sviluppo.pierangelo.workreport.Model.Customer;
import com.sviluppo.pierangelo.workreport.R;
import com.sviluppo.pierangelo.workreport.Service.BaseService;
import com.sviluppo.pierangelo.workreport.Service.CustomerService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CustomerDettaglioFragment extends Fragment {

    public int detail_id;

    TextView txtDenominazione, txtIndirizzo, txtCitta;
    Button btnEditCustomer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_dettaglio_customer, container, false);

        Bundle b = getArguments();
        detail_id = b.getInt("id");

        txtDenominazione = (TextView) view.findViewById(R.id.txt_customer_dettaglio_denominazione);
        txtIndirizzo = (TextView) view.findViewById(R.id.txt_customer_dettaglio_indirizzo_civico);
        txtCitta = (TextView) view.findViewById(R.id.txt_customer_dettaglio_cap_citta);

        CustomerService.CustomerAPI service = new BaseService().getRetrofit().create(CustomerService.CustomerAPI.class);
        Call<Customer> call = service.getCustomer(detail_id);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (response.isSuccessful()) {
                    Customer c = response.body();
                    txtDenominazione.setText(c.getDenominazione());
                    txtIndirizzo.setText(c.getIndirizzo() + "," + c.getCivico());
                    txtCitta.setText(c.getCap() + " - " + c.getCitta());

                    // invio id x edit, ricordati Bundle
                    btnEditCustomer = (Button) view.findViewById(R.id.btnEditCustomer);
                    btnEditCustomer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Fragment fragmentEditCustomer = new CustomerEditFragment();
                            Bundle bundle = new Bundle();
                            bundle.putInt("tmp_id", detail_id);
                            fragmentEditCustomer.setArguments(bundle);
                            FragmentManager manager = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.replace(R.id.fragment_contenitore_start_activity, fragmentEditCustomer);
                            transaction.commit();
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {

            }
        });

        return view;
    }

}
