package com.example.rentconnect.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static final String PREF_NAME = "RentConnectPrefs";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_USER_EMAIL = "userEmail";
    private static final String KEY_USER_ROLE = "userRole"; // Tenant or Landlord

    private final SharedPreferences pref;
    private final SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void saveUserSession(int id, String name, String email, String role) {
        editor.putInt(KEY_USER_ID, id);
        editor.putString(KEY_USER_NAME, name);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putString(KEY_USER_ROLE, role);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return pref.getInt(KEY_USER_ID, -1) != -1;
    }

    public int getUserId() {
        return pref.getInt(KEY_USER_ID, 1);
    }

    public String getUserName() {
        return pref.getString(KEY_USER_NAME, "Khách");
    }

    public String getUserEmail() {
        return pref.getString(KEY_USER_EMAIL, "user@rentconnect.vn");
    }

    public String getUserRole() {
        return pref.getString(KEY_USER_ROLE, "Tenant");
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}
