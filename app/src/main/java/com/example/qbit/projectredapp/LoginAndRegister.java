package com.example.qbit.projectredapp;



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

import org.json.JSONException;
import org.json.JSONObject;
public class LoginAndRegister extends Activity {
    private EditText user, passwd;
    private static final String REGISTER_URL="http://192.168.1.21/QueryFiles/register.php";
    private static final String LOGIN_URL="http://192.168.1.21/QueryFiles/login.php";
    private int success;
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
                Intent intent = new Intent(LoginAndRegister.this, Frontpage.class);
                try {
                    if(loginAction(LOGIN_URL, username, password)==true){
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Button createAccount = (Button) findViewById(R.id.create_account);
        createAccount.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String username=user.getText().toString();
                String password=passwd.getText().toString();
                try {
                    createAccountAction(REGISTER_URL, username, password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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


private boolean loginAction(String url, final String user, final String passwd) throws JSONException {
        RequestQueue requestQueue =VolleySingleton.getInstance().getRequestQueue();
        JSONObject obj = new JSONObject();
        obj.put("username", user);
        obj.put("password", passwd);

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST,url,obj,new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response){
                try{
                    if (response.getInt("success")==1){
                        Toast.makeText(LoginAndRegister.this, response.getString("message"), Toast.LENGTH_LONG).show();
                        success = response.getInt("success");
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("Print response", response.toString());
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginAndRegister.this, "RESPONSE" + error, Toast.LENGTH_LONG).show();
                Log.d("Error Volley", error.toString());
            }
        }){

        };
        requestQueue.add(request);
        if (success==1){
            return true;
        }else{
            return false;
        }



    }
private void createAccountAction(String url, final String user, final String passwd) throws JSONException {
        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
        JSONObject obj = new JSONObject();
        obj.put("username", user);
        obj.put("password", passwd);
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, url,obj, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response){
                try {
                    Toast.makeText(LoginAndRegister.this, response.getString("message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginAndRegister.this, "RESPONSE"+error, Toast.LENGTH_LONG).show();
                Log.d("Error Volley", error.toString());
            }
        }){
    };
        requestQueue.add(request);

    }
}
