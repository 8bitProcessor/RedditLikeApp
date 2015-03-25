package com.example.qbit.projectredapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by qbit on 24/03/15.
 */
public class MyApplication extends Application {
    private static MyApplication sInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
    }

    public static MyApplication getInstance(){
        return sInstance;
    }
    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }

}
