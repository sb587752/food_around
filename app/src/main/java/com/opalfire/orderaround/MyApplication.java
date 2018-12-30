package com.opalfire.orderaround;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;
import com.crashlytics.android.Crashlytics;
import com.opalfire.orderaround.utils.LocaleUtils;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig.Builder;

public class MyApplication
  extends Application
{
  protected void attachBaseContext(Context paramContext)
  {
    super.attachBaseContext(LocaleUtils.onAttach(paramContext, "en"));
    MultiDex.install(this);
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
  }
  
  public void onCreate()
  {
    super.onCreate();
    Fabric.with(this, new Crashlytics());
    CalligraphyConfig.initDefault(new Builder().setDefaultFontPath("fonts/Nunito-Regular.ttf").setFontAttrId(2130968887).build());
  }
  
  public void onLowMemory()
  {
    super.onLowMemory();
  }
}

