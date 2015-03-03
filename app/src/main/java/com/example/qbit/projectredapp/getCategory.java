package com.example.qbit.projectredapp;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

/**
 * Created by qbit on 03/03/15.
 */
public class getCategory {
    public String response="";
       public getCategory(){
       }
    public JSONArray getCats(){

        try{
            HttpClient catClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://192.168.1.91/QueryFiles/frontpage.php");
            HttpResponse response = catClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
        }catch(Exception e){
            Log.e("log_tage", "Couldn't get httpstuff");
        }




    }





}
