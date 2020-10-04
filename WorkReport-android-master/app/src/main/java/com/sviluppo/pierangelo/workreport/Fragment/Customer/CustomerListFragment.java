package com.sviluppo.pierangelo.workreport.Fragment.Customer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.sviluppo.pierangelo.workreport.Model.Customer;
import com.sviluppo.pierangelo.workreport.R;
import com.sviluppo.pierangelo.workreport.Service.BaseService;
import com.sviluppo.pierangelo.workreport.Service.CustomerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CustomerListFragment extends Fragment {


    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_list, container, false);

        CustomerService.CustomerAPI service = new BaseService().getRetrofit().create(CustomerService.CustomerAPI.class);
        Call<List<Customer>> call = service.getCustomerList();
        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, final Response<List<Customer>> response) {
                if (response.isSuccessful()) {
                    List<Customer> customers = response.body();
                    for (int i = 0; i < customers.size(); i++) {
                        Customer c = customers.get(i);
                        Log.d("denominazione", String.valueOf(c.getId()));
                        String id = String.valueOf(c.getId());
                        String denominazione = c.getDenominazione();
                        String indirizzo = c.getIndirizzo();
                        String civico = c.getCivico();
                        String cap = c.getCap();
                        String citta = c.getCitta();

                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("id", id);
                        map.put("denominazione", String.valueOf(denominazione));
                        map.put("indirizzo", String.valueOf(indirizzo) + ',' + String.valueOf(civico));
                        map.put("citta", String.valueOf(cap) + '-' + String.valueOf(citta));

                        jsonlist.add(map);
                    }

                    ListView listView = (ListView) getView().findViewById(R.id.listClienti);
                    ListAdapter adapter = new SimpleAdapter(getContext(), jsonlist, R.layout.customer_contenuto_listview,
                            new String[]{"denominazione", "indirizzo", "citta"},
                            new int[]{R.id.textListaDenominazione, R.id.textListIndirizzo, R.id.textListaCitta});
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String tmp_denominazione = jsonlist.get(position).get("denominazione");

                            Fragment fragment = new CustomerDettaglioFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("denominazione", tmp_denominazione);
                            bundle.putInt("id", Integer.parseInt(jsonlist.get(position).get("id")));
                            fragment.setArguments(bundle);

                            FragmentManager manager = getActivity().getSupportFragmentManager();
                            FragmentTransaction transaction = manager.beginTransaction();
                            transaction.replace(R.id.fragment_contenitore_start_activity, fragment);
                            transaction.commit();


                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {

            }
        });

        return view;
    }

}
