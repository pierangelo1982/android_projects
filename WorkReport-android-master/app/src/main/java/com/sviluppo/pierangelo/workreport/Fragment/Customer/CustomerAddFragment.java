package com.sviluppo.pierangelo.workreport.Fragment.Customer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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


public class CustomerAddFragment extends Fragment {

    EditText editTextDenominazione, editPiva, editCodFisc, editIndirizzo, editCivico, editCap,
            editCitta, editTelefono, editFax, editEmail, editWeb;
    Button btnAggiungi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_customer_add, container, false);

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
        btnAggiungi = (Button) view.findViewById(R.id.btnAddCustomer);

        btnAggiungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String denominazione = editTextDenominazione.getText().toString();
                String piva = editPiva.getText().toString();
                String codfisc = editCodFisc.getText().toString();
                String indirizzo = editIndirizzo.getText().toString();
                String civico = editCivico.getText().toString();
                String cap = editCap.getText().toString();
                String citta = editCitta.getText().toString();
                String telefono = editTelefono.getText().toString();
                String fax = editFax.getText().toString();
                String email = editEmail.getText().toString();
                String web = editWeb.getText().toString();

                CustomerService.CustomerAPI service = new BaseService().getRetrofit().create(CustomerService.CustomerAPI.class);

                Customer customer = new Customer();
                customer.setDenominazione(denominazione);
                customer.setPiva(piva);
                customer.setCodfisc(codfisc);
                customer.setIndirizzo(indirizzo);
                customer.setCivico(civico);
                customer.setCap(cap);
                customer.setCitta(citta);
                customer.setTelefono(telefono);
                customer.setFax(fax);
                customer.setEmail(email);
                customer.setWeb(web);


                Call<Customer> call = service.addCustomer(customer);
                call.enqueue(new Callback<Customer>() {
                    @Override
                    public void onResponse(Call<Customer> call, Response<Customer> response) {
                        if (response.isSuccessful()) {
                            Log.d("vediamo:", response.message());
                            Toast.makeText(getContext(), "Cliente Aggiunto", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {
                        Toast.makeText(getContext(), "errore", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        return view;
    }

}
