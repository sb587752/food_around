package com.opalfire.foodorder.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.places.Places;
import com.opalfire.foodorder.HomeActivity;
import com.opalfire.foodorder.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class WelcomeScreenActivity extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener {
    private static final int ASK_MULTIPLE_PERMISSION_REQUEST_CODE = 0;
    private static final int PLAY_SERVICES_REQUEST = 1000;
    private static final int REQUEST_CHECK_SETTINGS = 2000;
    private static final String TAG = "HomeActivity";
    Button loginButton;
    Button signUpButton;
    TextView skipBtn;
    OnPageChangeListener viewPagerPageChangeListener = new C13785();
    private TextView[] dots;
    private LinearLayout dotsLayout;
    private int[] layouts;
    private MyViewPagerAdapter myViewPagerAdapter;
    private ViewPager viewPager;

    public void onConnected(@Nullable Bundle bundle) {
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    public void onConnectionSuspended(int i) {
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void onCreate(android.os.Bundle r6) {
        /*
        r5 = this;
        super.onCreate(r6);
        r6 = android.os.Build.VERSION.SDK_INT;
        r0 = 21;
        if (r6 < r0) goto L_0x0016;
    L_0x0009:
        r6 = r5.getWindow();
        r6 = r6.getDecorView();
        r0 = 1280; // 0x500 float:1.794E-42 double:6.324E-321;
        r6.setSystemUiVisibility(r0);
    L_0x0016:
        r6 = 2131492929; // 0x7f0c0041 float:1.8609324E38 double:1.0530974306E-314;
        r5.setContentView(r6);
        r6 = 2131296964; // 0x7f0902c4 float:1.821186E38 double:1.053000611E-314;
        r6 = r5.findViewById(r6);
        r6 = (android.support.v4.view.ViewPager) r6;
        r5.viewPager = r6;
        r6 = 2131296615; // 0x7f090167 float:1.8211152E38 double:1.0530004386E-314;
        r6 = r5.findViewById(r6);
        r6 = (android.widget.LinearLayout) r6;
        r5.dotsLayout = r6;
        r6 = 2131296846; // 0x7f09024e float:1.821162E38 double:1.0530005527E-314;
        r6 = r5.findViewById(r6);
        r6 = (android.widget.Button) r6;
        r5.loginButton = r6;
        r6 = 2131296850; // 0x7f090252 float:1.8211628E38 double:1.0530005547E-314;
        r6 = r5.findViewById(r6);
        r6 = (android.widget.TextView) r6;
        r5.skipBtn = r6;
        r6 = 2131296849; // 0x7f090251 float:1.8211626E38 double:1.053000554E-314;
        r6 = r5.findViewById(r6);
        r6 = (android.widget.Button) r6;
        r5.signUpButton = r6;
        r6 = 3;
        r6 = new int[r6];
        r6 = {2131493083, 2131493084, 2131493085};
        r5.layouts = r6;
        r6 = r5.checkPlayServices();
        if (r6 == 0) goto L_0x0064;
    L_0x0061:
        r5.buildGoogleApiClient();
    L_0x0064:
        r6 = android.os.Build.VERSION.SDK_INT;
        r0 = 23;
        r1 = 0;
        if (r6 < r0) goto L_0x008d;
    L_0x006b:
        r6 = "android.permission.ACCESS_FINE_LOCATION";
        r6 = android.support.v4.content.ContextCompat.checkSelfPermission(r5, r6);
        if (r6 != 0) goto L_0x007c;
    L_0x0073:
        r6 = "android.permission.ACCESS_COARSE_LOCATION";
        r6 = android.support.v4.content.ContextCompat.checkSelfPermission(r5, r6);
        if (r6 != 0) goto L_0x007c;
    L_0x007b:
        goto L_0x008d;
    L_0x007c:
        r6 = "android.permission.ACCESS_FINE_LOCATION";
        r0 = "android.permission.ACCESS_COARSE_LOCATION";
        r2 = "android.permission.READ_EXTERNAL_STORAGE";
        r3 = "android.permission.WRITE_EXTERNAL_STORAGE";
        r4 = "android.permission.CAMERA";
        r6 = new java.lang.String[]{r6, r0, r2, r3, r4};
        android.support.v4.app.ActivityCompat.requestPermissions(r5, r6, r1);
    L_0x008d:
        r6 = r5.loginButton;
        r0 = new com.entriver.orderaround.activities.WelcomeScreenActivity$1;
        r0.<init>();
        r6.setOnClickListener(r0);
        r6 = r5.signUpButton;
        r0 = new com.entriver.orderaround.activities.WelcomeScreenActivity$2;
        r0.<init>();
        r6.setOnClickListener(r0);
        r6 = 2130771995; // 0x7f01001b float:1.7147096E38 double:1.052741242E-314;
        r0 = 2130771980; // 0x7f01000c float:1.7147065E38 double:1.0527412344E-314;
        r5.overridePendingTransition(r6, r0);
        r6 = r5.skipBtn;
        r0 = new com.entriver.orderaround.activities.WelcomeScreenActivity$3;
        r0.<init>();
        r6.setOnClickListener(r0);
        r5.addBottomDots(r1);
        r5.changeStatusBarColor();
        r6 = new com.entriver.orderaround.activities.WelcomeScreenActivity$MyViewPagerAdapter;
        r6.<init>();
        r5.myViewPagerAdapter = r6;
        r6 = r5.viewPager;
        r0 = r5.myViewPagerAdapter;
        r6.setAdapter(r0);
        r6 = r5.viewPager;
        r0 = r5.viewPagerPageChangeListener;
        r6.addOnPageChangeListener(r0);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.entriver.orderaround.activities.WelcomeScreenActivity.onCreate(android.os.Bundle):void");
    }

    protected synchronized void buildGoogleApiClient() {
        GoogleApiClient build = new Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Places.GEO_DATA_API).addApi(LocationServices.API).build();
        build.connect();
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(100);
        LocationServices.SettingsApi.checkLocationSettings(build, new LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build()).setResultCallback(new C13774());
    }

    private void addBottomDots(int i) {
        this.dots = new TextView[this.layouts.length];
        int[] intArray = getResources().getIntArray(R.array.array_dot_active);
        int[] intArray2 = getResources().getIntArray(R.array.array_dot_inactive);
        this.dotsLayout.removeAllViews();
        for (int i2 = 0; i2 < this.dots.length; i2++) {
            this.dots[i2] = new TextView(this);
            this.dots[i2].setText(Html.fromHtml("&#8226;"));
            this.dots[i2].setTextSize(35.0f);
            this.dots[i2].setTextColor(intArray2[i]);
            this.dotsLayout.addView(this.dots[i2]);
        }
        if (this.dots.length > 0) {
            Animation loadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
            this.dots[i].setTextColor(intArray[i]);
            this.dots[i].startAnimation(loadAnimation);
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
        int isGooglePlayServicesAvailable = instance.isGooglePlayServicesAvailable(this);
        if (isGooglePlayServicesAvailable == 0) {
            return true;
        }
        if (instance.isUserResolvableError(isGooglePlayServicesAvailable)) {
            instance.getErrorDialog(this, isGooglePlayServicesAvailable, 1000).show();
        } else {
            Toast.makeText(getApplicationContext(), "This device is not supported.", 1).show();
            finish();
        }
        return false;
    }

    private int getItem(int i) {
        return this.viewPager.getCurrentItem() + i;
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    private void changeStatusBarColor() {
        if (VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(View.VISIBLE);
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        if (i == 0) {
            if (iArr.length > 0) {
                i = 1;
                strArr = iArr[1] == null ? 1 : null;
                if (iArr[0] != null) {
                    i = 0;
                }
                if (strArr == null || r2 == 0) {
                    Snackbar.make(findViewById(16908290), (CharSequence) "Please Grant Permissions to start service", (int) -2).setAction((CharSequence) "ENABLE", new C07696()).show();
                }
            }
        }
    }

    /* renamed from: com.entriver.orderaround.activities.WelcomeScreenActivity$1 */
    class C07661 implements OnClickListener {
        C07661() {
        }

        public void onClick(View view) {
            WelcomeScreenActivity.this.startActivity(new Intent(WelcomeScreenActivity.this, LoginActivity.class).addFlags(67108864));
            WelcomeScreenActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
            WelcomeScreenActivity.this.finish();
        }
    }

    /* renamed from: com.entriver.orderaround.activities.WelcomeScreenActivity$2 */
    class C07672 implements OnClickListener {
        C07672() {
        }

        public void onClick(View view) {
            WelcomeScreenActivity.this.startActivity(new Intent(WelcomeScreenActivity.this, MobileNumberActivity.class).putExtra("signup", true).addFlags(67108864));
            WelcomeScreenActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
            WelcomeScreenActivity.this.finish();
        }
    }

    /* renamed from: com.entriver.orderaround.activities.WelcomeScreenActivity$3 */
    class C07683 implements OnClickListener {
        C07683() {
        }

        public void onClick(View view) {
            WelcomeScreenActivity.this.startActivity(new Intent(WelcomeScreenActivity.this, HomeActivity.class).addFlags(67108864));
            WelcomeScreenActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
            WelcomeScreenActivity.this.finish();
        }
    }

    /* renamed from: com.entriver.orderaround.activities.WelcomeScreenActivity$6 */
    class C07696 implements OnClickListener {
        C07696() {
        }

        public void onClick(View view) {
            ActivityCompat.requestPermissions(WelcomeScreenActivity.this, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 0);
        }
    }

    /* renamed from: com.entriver.orderaround.activities.WelcomeScreenActivity$4 */
    class C13774 implements ResultCallback<LocationSettingsResult> {
        C13774() {
        }

        public void onResult(com.google.android.gms.location.LocationSettingsResult r3) {
            /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/193388045.run(Unknown Source)
*/
            /*
            r2 = this;
            r3 = r3.getStatus();
            r0 = r3.getStatusCode();
            if (r0 == 0) goto L_0x0015;
        L_0x000a:
            r1 = 6;
            if (r0 == r1) goto L_0x000e;
        L_0x000d:
            goto L_0x0015;
        L_0x000e:
            r0 = com.entriver.orderaround.activities.WelcomeScreenActivity.this;	 Catch:{ SendIntentException -> 0x0015 }
            r1 = 2000; // 0x7d0 float:2.803E-42 double:9.88E-321;	 Catch:{ SendIntentException -> 0x0015 }
            r3.startResolutionForResult(r0, r1);	 Catch:{ SendIntentException -> 0x0015 }
        L_0x0015:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.entriver.orderaround.activities.WelcomeScreenActivity.4.onResult(com.google.android.gms.location.LocationSettingsResult):void");
        }
    }

    /* renamed from: com.entriver.orderaround.activities.WelcomeScreenActivity$5 */
    class C13785 implements OnPageChangeListener {
        C13785() {
        }

        public void onPageScrollStateChanged(int i) {
        }

        public void onPageScrolled(int i, float f, int i2) {
        }

        public void onPageSelected(int i) {
            WelcomeScreenActivity.this.addBottomDots(i);
        }
    }

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        public Object instantiateItem(ViewGroup viewGroup, int i) {
            this.layoutInflater = (LayoutInflater) WelcomeScreenActivity.this.getSystemService("layout_inflater");
            i = this.layoutInflater.inflate(WelcomeScreenActivity.this.layouts[i], viewGroup, false);
            viewGroup.addView(i);
            return i;
        }

        public int getCount() {
            return WelcomeScreenActivity.this.layouts.length;
        }

        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }
    }
}
