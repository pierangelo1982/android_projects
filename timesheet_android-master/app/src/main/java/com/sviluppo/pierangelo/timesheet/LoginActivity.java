package com.sviluppo.pierangelo.timesheet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{

    //private static String url = "http://timesheet.web-dev.info/workers.json";
    public static final String MY_USER_PREFERENCE = "MyPrefsFile";
    private EditText inputUsername;
    private EditText inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputUsername = (EditText) findViewById(R.id.inputEmail);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        Button btnEntra = (Button) findViewById(R.id.buttonEntra);

        btnEntra.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        //new LoginTask().execute("http://timesheet.web-dev.info/workers.json");
        new LoginTask().execute();
    }

    public class LoginTask extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... params)
        {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try
            {
                URL url = new URL("http://timesheet.web-dev.info/workers.json");
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null)
                {
                    buffer.append(line);
                }
                return buffer.toString();


            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);

            String email = inputUsername.getText().toString();
            String psw = inputPassword.getText().toString();

            String myj = s.toString();
            try
            {

                String okid = null;
                String oknome = null;
                String okusername = null;
                String okpassword = null;
                JSONArray json = new JSONArray(myj);

                //filtro i dati dall'array se combaciano con quelli inseriti nel form login, per passarli al check login
                for (int i=0; i < json.length(); i++)
                {
                    JSONObject c = json.getJSONObject(i);
                    if (c.getString("email").equals(email) && c.getString("password").equals(psw))
                    {
                        okid = c.getString("id");
                        oknome = c.getString("nome") + " " + c.getString("cognome");
                        okusername = c.getString("email");
                        okpassword = c.getString("password");
                    }
                }

                Log.d("dettaglio", okusername + okpassword);


                // check login
                if (email.equals(okusername) && psw.equals(okpassword)) {
                    // memorizzo dati utente loggato compreso id per salvaro in db nelle futire azioni
                    SharedPreferences.Editor editor = getSharedPreferences(MY_USER_PREFERENCE, MODE_PRIVATE).edit();
                    editor.putString("mId", okid);
                    editor.putString("mNome", oknome);
                    editor.putString("mUsername", okusername);
                    editor.putString("mPassword", okpassword);
                    editor.commit();

                    Intent goStart = new Intent(getApplicationContext(), StartActivity.class);
                    startActivity(goStart);
                    //Toast.makeText(getApplicationContext(), "Funziona", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Credenziali errate", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
