package com.sviluppo.pierangelo.workreport.Fragment.Customer;

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
import android.widget.Toast;

import com.sviluppo.pierangelo.workreport.Model.Customer;
import com.sviluppo.pierangelo.workreport.R;
import com.sviluppo.pierangelo.workreport.Service.BaseService;
import com.sviluppo.pierangelo.workreport.Service.CustomerService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CustomerEditFragment extends Fragment {

    public int detail_id;

    EditText editTextDenominazione, editPiva, editCodFisc, editIndirizzo, editCivico, editCap,
            editCitta, editTelefono, editFax, editEmail, editWeb;

    Button btnEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_edit, container, false);

        Bundle b = getArguments();
        detail_id = b.getInt("tmp_id");
        Log.d("vediamo:", String.valueOf(detail_id));

        editTextDenominazione = (EditText) view.findViewById(R.id.edit_add_customer_denominazione);
        editPiva = (EditText) view.findViewById(R.id.edit_add_customer_piva);
        editCodFisc = (EditText) view.findViewById(R.id.edit_add_customer_codice_fiscale);
        editIndirizzo = (EditText) view.findViewById(R.id.edit_add_customer_indirizzo);
        editCivico = (EditText) view.findViewById(R.id.edit_add_customer_civico);
        editCap = (EditText) view.findViewById(R.id.edit_add_customer_cap);
        editCitta = (EditText) view.findViewById(R.id.edit_add_customer_citta);
        editTelefono = (EditText) view.findViewById(R.id.edit_add_customer_telefono);
        editFax = (EditText) view.findViewById(R.id.edit_add_customer_fax);
        editEmail = (EditText) view.findViewById(R.id.edit_add_customer_email);
        editWeb = (EditText) view.findViewById(R.id.edit_add_customer_web);

        btnEdit = (Button) view.findViewById(R.id.btnEditCustomer);

        // ottengo dati per riempire i campi form con i dati da modificare
        CustomerService.CustomerAPI service = new BaseService().getRetrofit().create(CustomerService.CustomerAPI.class);
        Call<Customer> call = service.getCustomer(detail_id);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                if (response.isSuccessful()) {
                    Customer c = response.body();
                    editTextDenominazione.setText(c.getDenominazione());
                    editPiva.setText(c.getPiva());
                    editCodFisc.setText(c.getCodfisc());
                    editIndirizzo.setText(c.getIndirizzo());
                    editCivico.setText(c.getCivico());
                    editCap.setText(c.getCap());
                    editCitta.setText(c.getCitta());
                    editTelefono.setText(c.getTelefono());
                    editFax.setText(c.getFax());
                    editEmail.setText(c.getEmail());
                    editWeb.setText(c.getWeb());
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {

            }
        });

        // invio dati aggiornati
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Customer c = new Customer();
                c.setDenominazione(editTextDenominazione.getText().toString());
                c.setPiva(editPiva.getText().toString());
                c.setCodfisc(editCodFisc.getText().toString());
                c.setIndirizzo(editIndirizzo.getText().toString());
                c.setCivico(editCivico.getText().toString());
                c.setCap(editCap.getText().toString());
                c.setCitta(editCitta.getText().toString());
                c.setTelefono(editTelefono.getText().toString());
                c.setFax(editFax.getText().toString());
                c.setEmail(editEmail.getText().toString());
                c.setWeb(editWeb.getText().toString());

                CustomerService.CustomerAPI updateService = new BaseService().getRetrofit().create(CustomerService.CustomerAPI.class);
                Call<Customer> updateCall = updateService.editCustomer(detail_id, c);
                updateCall.enqueue(new Callback<Customer>() {
                    @Override
                    public void onResponse(Call<Customer> call, Response<Customer> response) {
                        if (response.isSuccessful()) {
                            Log.d("vediamo:", response.message());

                            Fragment fragment = new CustomerDettaglioFragment();
                            Bundle bundle = new Bundle();
                            bundle.putInt("id", detail_id);
                            fragment.setArguments(bundle);

                            FragmentManager manager = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.replace(R.id.fragment_contenitore_start_activity, fragment);
                            transaction.commit();

                            Toast.makeText(getContext(), "Cliente Aggiunto", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {
                        Toast.makeText(getContext(), "C'è stato un problema", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });



        return view;
    }

}
