package com.example.qbit.projectredapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by qbit on 08/04/15.
 */
public class SaveSharedPreference {
    static final String username = "username";
    static final String password = "password";
    static SharedPreferences getSharedPreferences(Context ctx){
        return PreferenceManager .getDefaultSharedPreferences(ctx);
    }
    public static void setDetails(Context ctx, String userName, String passwd){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(username, userName);
        editor.putString(password, passwd);
        editor.commit();
    }
    public static String getUsername(Context ctx){
        return getSharedPreferences(ctx).getString(username, "");
    }
    public static String getPassword(Context ctx){
        return getSharedPreferences(ctx).getString(password, "");
    }

    public static void logout(Context ctx){
        SharedPreferences.Editor editor= getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();

    }
}
