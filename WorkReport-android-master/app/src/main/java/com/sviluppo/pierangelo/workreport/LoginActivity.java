package com.sviluppo.pierangelo.workreport;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sviluppo.pierangelo.workreport.Model.Worker;
import com.sviluppo.pierangelo.workreport.Service.WorkerService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    public static final String MY_USER_PREFERENCE = "MyLogFile";
    private EditText email;
    private EditText password;
    private String mEmail;
    private String mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.txtEmail);
        password = (EditText) findViewById(R.id.txtPassword);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {

        mEmail = email.getText().toString();
        mPassword = password.getText().toString();

        // chiamo Retrofit
        Call<List<Worker>> call = WorkerService.retrofit.create(WorkerService.WorkerAPI.class).getWorkerList();
        call.enqueue(new Callback<List<Worker>>() {
            @Override
            public void onResponse(Call<List<Worker>> call, Response<List<Worker>> response)
            {
                if (response.isSuccessful()) {

                    List<Worker> wk = response.body();
                    for (int i = 0; i < wk.size(); i++)
                    {
                        Worker c = wk.get(i);
                        Log.d("vedi", c.getEmail());
                        // se email e password uguali a quelle inserite salvo in memoria
                        if (c.getEmail().equals(mEmail) && c.getPassword().equals(mPassword))
                        {
                            SharedPreferences.Editor editor = getSharedPreferences(MY_USER_PREFERENCE, MODE_PRIVATE).edit();
                            editor.putString("mId", c.getId().toString());
                            editor.putString("mNome", c.getNome());
                            editor.putString("mUsername", c.getEmail());
                            editor.putString("mPassword", c.getPassword());
                            editor.commit();
                        }
                    }

                    //richiamo dati in memoria per passare username e password al controllo login
                    SharedPreferences prefs = getSharedPreferences(MY_USER_PREFERENCE, MODE_PRIVATE);
                    String storedUsernae = prefs.getString("mUsername", null);
                    String stroredPassword = prefs.getString("mPassword", null);
                    //controllo parametri login
                    if (mEmail.equals(storedUsernae) && mPassword.equals(stroredPassword)) {
                        Toast.makeText(getApplicationContext(), "Credenziali OK", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(), StartActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Credenziali errate", Toast.LENGTH_SHORT).show();
                    }
                }
            }

                @Override
                public void onFailure(Call<List<Worker>> call, Throwable t) {

                }
        });

    }
}