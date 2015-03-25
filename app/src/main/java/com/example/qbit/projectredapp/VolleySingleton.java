package com.example.qbit.projectredapp;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by qbit on 24/03/15.
 */
public class VolleySingleton {
    private static VolleySingleton sInstance=null;
    private RequestQueue mRequestQueue;

    private VolleySingleton(){
        mRequestQueue=Volley.newRequestQueue(MyApplication.getAppContext());
    }

    public static VolleySingleton getInstance(){
        if(sInstance==null){
            sInstance=new VolleySingleton();
        }
        return sInstance;
    }
    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }





}
