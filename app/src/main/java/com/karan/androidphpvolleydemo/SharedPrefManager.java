package com.karan.androidphpvolleydemo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Admin on 7/29/2017.
 */

public class SharedPrefManager
{
    private  static final String SHARED_PREF_NAME="mysharedpref12";
    private static final String KEY_USER_NAME="username";
    private static final String KEY_USER_EMAIl="email";
    private static final String KEY_USER_ID="userid";
    private static  SharedPrefManager mInstance;
    private static Context mcxt;
    private  SharedPrefManager(Context context)
    {
        mcxt=context;
    }
    public static synchronized  SharedPrefManager getmInstance(Context context)
    {
        if(mInstance==null)
        {
            mInstance=new SharedPrefManager(context);
        }
        return mInstance;
    }
    public boolean userLogin(int id,String email,String username)
    {
        SharedPreferences sharedPreferences=mcxt.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(KEY_USER_ID,id);
        editor.putString(KEY_USER_EMAIl,email);
        editor.putString(KEY_USER_NAME,username);
        editor.apply();

        return true;
    }
    public boolean isLoggedIn()
    {
        SharedPreferences sharedPreferences=mcxt.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USER_NAME,null)!=null)
        {
            return true;
        }
        return false;
    }
    public boolean LogOut()
    {
        SharedPreferences sharedPreferences=mcxt.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}
