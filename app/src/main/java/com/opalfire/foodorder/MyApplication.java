package com.opalfire.foodorder;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.opalfire.foodorder.utils.LocaleUtils;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig.Builder;

public class MyApplication
        extends Application {
    @Override
    protected void attachBaseContext(Context paramContext) {
        super.attachBaseContext(LocaleUtils.onAttach(paramContext, "en"));
        MultiDex.install(this);
    }

    @Override
    public void onConfigurationChanged(Configuration paramConfiguration) {
        super.onConfigurationChanged(paramConfiguration);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        CalligraphyConfig.initDefault(new Builder().setDefaultFontPath("fonts/Nunito-Regular.ttf").setFontAttrId(2130968887).build());
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}

