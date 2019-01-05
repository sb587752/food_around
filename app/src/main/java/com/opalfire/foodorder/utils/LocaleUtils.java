package com.opalfire.foodorder.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.preference.PreferenceManager;

import java.util.Locale;

public class LocaleUtils {
    private static final String SELECTED_LANGUAGE = "language";

    public static Context onAttach(Context context) {
        return setLocale(context, getPersistedData(context, Locale.getDefault().getLanguage()));
    }

    public static Context onAttach(Context context, String str) {
        return setLocale(context, getPersistedData(context, str));
    }

    public static String getLanguage(Context context) {
        return getPersistedData(context, Locale.getDefault().getLanguage());
    }

    public static Context setLocale(Context context, String str) {
        persist(context, str);
        if (VERSION.SDK_INT >= 24) {
            return updateResources(context, str);
        }
        return updateResourcesLegacy(context, str);
    }

    private static String getPersistedData(Context context, String str) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(SELECTED_LANGUAGE, str);
    }

    private static void persist(Context mContext, String str) {
        SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
        prefEditor.putString(SELECTED_LANGUAGE, str);
        prefEditor.apply();
    }

    @TargetApi(24)
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = context.getResources().getConfiguration();
        config.setLocale(locale);
        config.setLayoutDirection(locale);
        return context.createConfigurationContext(config);
    }

    private static Context updateResourcesLegacy(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration configuration = res.getConfiguration();
        configuration.locale = locale;
        if (VERSION.SDK_INT >= 17) {
            configuration.setLayoutDirection(locale);
        }
        res.updateConfiguration(configuration, res.getDisplayMetrics());
        return context;
    }
}
