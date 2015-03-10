package com.example.qbit.projectredapp;

/**
 * Created by qbit on 09/03/15.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class LoginAndRegister extends Activity {
    parserJSON jParser = new parserJSON();
    private EditText user, passwd;
    private static final String REGISTER_URL="http://192.168.1.23/QueryFiles/register.php";
    private static final String LOGIN_URL="http://192.168.1.23/QueryFiles/login.php";
    private ProgressDialog pDialog;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        //On click listener for Login
        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                new Login().execute();
            }
        });
        //On Click listener for Create account
        user = (EditText)findViewById(R.id.username);
        passwd = (EditText)findViewById(R.id.password);
        Button createAccount = (Button) findViewById(R.id.create_account);
        createAccount.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                    new CreateAccount().execute();
            }
        });
        // Linking page to Frontpage with the no details as user won't have to login
               Button goAhead = (Button) findViewById(R.id.defaultCategory);
                goAhead.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                Intent intent =new Intent(v.getContext(), Frontpage.class);
                startActivityForResult(intent, 0);
            }
        });
    }
    class Login extends AsyncTask<String, String, String>{
        protected void onPreExecute(){
            super.onPreExecute();
             Toast.makeText(LoginAndRegister.this, "Logging in...", Toast.LENGTH_LONG).show();
        }
        protected String doInBackground(String...args){
            String username = user.getText().toString();
            String password = passwd.getText().toString();
            List<NameValuePair> userDetails = new ArrayList<NameValuePair>();
            userDetails.add(new BasicNameValuePair("username", username));
            userDetails.add(new BasicNameValuePair("password", password));
            Log.d("request", "Trying to parse data");
            JSONObject jObject = jParser.makeRequest(LOGIN_URL, "POST", userDetails);
            try{
                int success= jObject.getInt(TAG_SUCCESS);
                if (success==1){
                    Log.d("Logged On", "User has been logged in");
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(LoginAndRegister.this);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("username", username);
                    edit.commit();
                    Intent i = new Intent(LoginAndRegister.this, Frontpage.class);
                    finish();
                    startActivity(i);
                }else {
                    Log.d("Login failure", jObject.getString(TAG_MESSAGE));
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
            return null;
        }
    }
    class CreateAccount extends AsyncTask<String, String, String> {
        protected void onPreExecute(){
            super.onPreExecute();
            Toast.makeText(LoginAndRegister.this, "Creating User...", Toast.LENGTH_LONG).show();
        }
        protected String doInBackground(String... args) {
            String username = user.getText().toString();
            String password = passwd.getText().toString();
            List<NameValuePair> userDetails = new ArrayList<NameValuePair>();
            userDetails.add(new BasicNameValuePair("username", username));
            userDetails.add(new BasicNameValuePair("password", password));
            Log.d("request!", "going to try and parse the data");
            JSONObject json = jParser.makeRequest(REGISTER_URL, "POST", userDetails);
            try{
                int success  = json.getInt(TAG_SUCCESS);
                String message = json.getString(TAG_MESSAGE);
                if( success==1){
                    Log.d("User Created", json.toString());
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(LoginAndRegister.this);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("username", username);
                    edit.commit();
                    Intent i = new Intent(LoginAndRegister.this, Frontpage.class);
                    finish();
                    startActivity(i);
                }else{
                    Log.d("Register Failure", json.getString(TAG_MESSAGE));
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }


    }
 }