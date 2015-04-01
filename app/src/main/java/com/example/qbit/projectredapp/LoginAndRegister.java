package com.example.qbit.projectredapp;

/**
 * Created by qbit on 09/03/15.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
public class LoginAndRegister extends Activity {
    private EditText user, passwd;
    private static final String REGISTER_URL="http://ec2-52-16-75-101.eu-west-1.compute.amazonaws.com/QueryFiles/register.php";
    private static final String LOGIN_URL="http://ec2-52-16-75-101.eu-west-1.compute.amazonaws.com/QueryFiles/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        //On click listener for Login
        Button login = (Button) findViewById(R.id.login);
        //On Click listener for Create account
        user = (EditText)findViewById(R.id.username);
        passwd = (EditText)findViewById(R.id.password);
        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String username=user.getText().toString();
                String password=passwd.getText().toString();
                loginAction(LOGIN_URL, username, password);
            }
        });
        Button createAccount = (Button) findViewById(R.id.create_account);
        createAccount.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String username=user.getText().toString();
                String password=passwd.getText().toString();
                    createAccountAction(REGISTER_URL, username, password);
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
    private void loginAction(String url, final String user, final String passwd){
        RequestQueue requestQueue =VolleySingleton.getInstance().getRequestQueue();
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST,url,new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response){

                    Log.d("Print response", response.toString());

            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginAndRegister.this, "RESPONSE" + error, Toast.LENGTH_LONG).show();
                Log.d("Error Volley", error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", user);
                params.put("password", passwd);
                return params;
            }
        };
        requestQueue.add(request);
    }
    private void createAccountAction(String url, final String user, final String passwd){
        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, url, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response){
                Log.d("Print response", response.toString());
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginAndRegister.this, "RESPONSE"+error, Toast.LENGTH_LONG).show();
                Log.d("Error Volley", error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", user);
                params.put("password", passwd);
                return params;
            }
        };
        requestQueue.add(request);
    }
}
