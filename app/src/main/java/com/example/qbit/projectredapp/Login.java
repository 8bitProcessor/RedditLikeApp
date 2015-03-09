package com.example.qbit.projectredapp;

/**
 * Created by qbit on 09/03/15.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Login extends Activity {
    parserJSON jParser = new parserJSON();
    private EditText user, passwd;
    private static final String REGISTER_URL="http://192.168.1.22/QueryFiles/register.php";
    private ProgressDialog pDialog;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
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
    class CreateAccount extends AsyncTask<String, String, String> {


        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Creating User...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        protected String doInBackground(String... args) {
            String username = user.getText().toString();
            String password = user.getText().toString();


            List<NameValuePair> UNandPW = new ArrayList<NameValuePair>();
            Log.d("request!", "going to try and parse the data");
            JSONObject json = jParser.createAccount(REGISTER_URL, "POST", UNandPW);
            try{
                int success  = json.getInt(TAG_SUCCESS);
                if( success==1){
                    Log.d("Usercreated", json.toString());
                    finish();
                }else{
                    Log.d("Register Failure", json.getString(TAG_MESSAGE));
                    finish();
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url){
            pDialog.dismiss();
            if(file_url!=null){
                Toast.makeText(Login.this, file_url, Toast.LENGTH_LONG).show();
            }
       }

    }




 }