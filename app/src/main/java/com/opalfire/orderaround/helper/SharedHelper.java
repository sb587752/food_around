package com.opalfire.orderaround.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedHelper {
    public static Editor editor;
    public static SharedPreferences sharedPreferences;

    public static void putKey(Context context, String str, String str2) {
        sharedPreferences = context.getSharedPreferences("Cache", 0);
        editor = sharedPreferences.edit();
        editor.putString(str, str2);
        editor.apply();
    }

    public static String getKey(Context context, String str) {
        sharedPreferences = context.getSharedPreferences("Cache", 0);
        return sharedPreferences.getString(str, "");
    }

    public static void clearSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences("Cache", 0);
        sharedPreferences.edit().clear().apply();
    }
}
