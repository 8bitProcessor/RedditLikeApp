package com.example.qbit.projectredapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;

public class SubmitThread extends Fragment {
    SaveSharedPreference pm =new SaveSharedPreference();
    private EditText title,topic_link;
    private Spinner category;
    private Spinner link_option;
    private Button submit,back;
    private static final String post_url= "http://ec2-52-16-75-101.eu-west-1.compute.amazonaws.com/QueryFiles/createThread.php";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View submit_thread =  inflater.inflate(R.layout.submit_thread, container,false);
        title = (EditText) submit_thread.findViewById(R.id.title);
        category = (Spinner) submit_thread.findViewById(R.id.category);
        link_option = (Spinner) submit_thread.findViewById(R.id.link_options);
        topic_link = (EditText) submit_thread.findViewById(R.id.topic_link);
        submit = (Button) submit_thread.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String post_title=title.getText().toString();
                String post_category=category.getSelectedItem().toString();
                String post_option=link_option.getSelectedItem().toString();
                String post_info =topic_link.getText().toString();
                try{
                    postThread(post_url, post_title,post_category,post_option,post_info);
                }catch (JSONException e){
                    e.printStackTrace();
                }
           }
        });

        back = (Button) submit_thread.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(getActivity(), Frontpage.class);
                startActivity(i);
            }
        });

        return submit_thread;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
    public void postThread(String url, String post_title, String post_category, String post_option,String post_info) throws JSONException {
        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
        final JSONObject postDetails = new JSONObject();
        postDetails.put("username", pm.getUsername(getActivity()));
        postDetails.put("password", pm.getPassword(getActivity()));
        postDetails.put("title", post_title);
        postDetails.put("category", post_category);
        postDetails.put("type", post_option);
        postDetails.put("info", post_info);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url,postDetails, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast.makeText(getActivity(), response.getString("message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "RESPONSE" + error, Toast.LENGTH_LONG).show();
                Log.d("Error Volley", error.toString());
            }
        }){
        };
        requestQueue.add(postRequest);
    }
}
