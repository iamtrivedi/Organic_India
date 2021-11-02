package com.organic.india.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Calendar;
import java.util.Set;

public class SharedPreferenceUtils {

    private static SharedPreferences prefs;
    private static SharedPreferences.Editor editor;

    private SharedPreferenceUtils() {

    }
    public static void init(Context context) {
        if (prefs == null) {
            prefs = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
            editor = prefs.edit();
        }
    }

    public static void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();

    }



    public static String getString(String key, String defValue) {
        return prefs.getString(key, defValue);
    }

    public static void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getInt(String key, int defValue) {
        return prefs.getInt(key, defValue);
    }

    public static void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(String key, boolean defValue){
        return prefs.getBoolean(key, defValue);
    }


    public static void putArray(String key, Set<String> arrayList){
        editor.putStringSet(key, arrayList);
        editor.commit();
    }

    public static Set getArray(String key, Set<String> defvalue){
        return prefs.getStringSet(key,defvalue);
    }


    public static void putLong(String key, long value){
        editor.putLong(key,value);
        editor.commit();
    }

    public static Long getLong(String key, long defvalue){
        return prefs.getLong(key,defvalue);
    }


    public static void removeSavedPref(String prefkey){
        Log.e("clear_pref_data","key "+prefkey);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(prefkey);
        editor.apply();
        editor.commit();
    }

    public static boolean prefExists(String prefkey){
        return  prefs.contains(prefkey);
    }

    public static boolean ifexiststhenclear(String prefkey){
        if (prefExists(prefkey)){
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove(prefkey);
            editor.commit();
        }
        else{
            return false;
        }
        return true;
    }

    public static void clear()
    {
        editor.clear();
        editor.commit();
    }

    public static void clear(String keyBlockedUser)
    {
        editor.remove(keyBlockedUser);

        editor.commit();
    }

    public static class User_Creds{

        public static final String account_state = "Your onboarding request is under review, please try again later.";
        public static String user_creds="user_creds";
        public static String attendance_metadata=Constant.yyyy_mm_dd(Calendar.getInstance());

    }

}

