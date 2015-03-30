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

import java.util.List;


public class Frontpage extends ActionBarActivity {
        private static String FRONTPAGE_URL="http://192.168.1.10/QueryFiles/frontpage.php";
        private RecyclerView recyclerView;
        private FrontpageAdaptor adaptor;
        private List<ThreadClass> threads;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontpage);
        getFrontPage();
        recyclerView = (RecyclerView) findViewById(R.id.frontpage_view);
        adaptor = new FrontpageAdaptor(Frontpage.this,threads);
        recyclerView.setAdapter(adaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(Frontpage.this));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_frontpage, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void getFrontPage(){
        RequestQueue requestQueue =VolleySingleton.getInstance().getRequestQueue();

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET,FRONTPAGE_URL,new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response){
                try {
                    parseJsonResponse(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
    private void parseJsonResponse(JSONObject response) throws JSONException {

        if(response==null || response.length()==0){
            return;
        }
        try{
        JSONArray jsonArray = response.getJSONArray("frontpage");
        for (int i = 0; i<jsonArray.length(); i++){
            ThreadClass threadClass = new ThreadClass();
            JSONObject currentObject =jsonArray.getJSONObject(i);
            threadClass.title=currentObject.getString("title");
            threadClass.username=currentObject.getString("username");
            threads.add(threadClass);
        }
        }catch (JSONException e){

        }
    }
}


