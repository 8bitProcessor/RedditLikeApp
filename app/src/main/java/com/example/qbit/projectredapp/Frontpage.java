package com.example.qbit.projectredapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Frontpage extends ActionBarActivity {

        private static String FRONTPAGE_URL="http://ec2-52-16-75-101.eu-west-1.compute.amazonaws.com/QueryFiles/frontpage.php";

        private RecyclerView mRecyclerView;
        private FrontpageAdaptor mAdaptor;
        private RecyclerView.LayoutManager mLayoutManager;
        private ArrayList<ThreadClass> threads = new ArrayList<ThreadClass>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontpage);
        getFrontPage();
        mRecyclerView = (RecyclerView) findViewById(R.id.frontpage_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdaptor = new FrontpageAdaptor(this);
        mRecyclerView.setAdapter(mAdaptor);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_frontpage, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void getFrontPage(){
        RequestQueue requestQueue =VolleySingleton.getInstance().getRequestQueue();
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET,FRONTPAGE_URL,new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
              threads=  parseData(response);
              mAdaptor.setData(threads);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Frontpage.this, "RESPONSE" + error, Toast.LENGTH_LONG).show();
                Log.d("Error Volley", error.toString());
            }
        });
        requestQueue.add(request);
    }
    public ArrayList<ThreadClass> parseData(JSONObject response){
        ArrayList<ThreadClass> parseThreads = new ArrayList<ThreadClass>();
        if(response==null || response.length()==0){
            return null;
        }else{
            try {
                JSONArray arrayThreads =response.getJSONArray("frontpage");
                for (int i= 0; i<arrayThreads.length(); i++){
                    JSONObject currentThread=arrayThreads.getJSONObject(i);
                    ThreadClass threadClass = new ThreadClass();
                    String username=currentThread.getString("username");
                    String title =currentThread.getString("title");
                    threadClass.setTitle(title);
                    threadClass.setUsername(username);
                    parseThreads.add(threadClass);
                }
            }
            catch(JSONException e){
                e.printStackTrace();
            }
            return parseThreads;
        }
    }
}


