package com.bridgelabz.fundoo.common.share_preference;

import android.content.Context;
import android.content.SharedPreferences;


public class SharePreferencesManager
{
    public static final String PREFERENCE_FILE_NAME = "SharePreferencesManager";
    private SharedPreferences sharedPreferences;

    public SharePreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences
                (PREFERENCE_FILE_NAME,context.MODE_PRIVATE);
    }


    public void setAccessToken(String token) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        sharedPreferences.getString("Status",null);
        editor.putString("key",token);
        editor.clear();
        editor.commit();
    }

    public String getAccessToken()
    {
        return sharedPreferences.getString("key","NULL");
    }

    public boolean isLoggedOut() {
        return sharedPreferences.getAll().isEmpty();
    }
}
