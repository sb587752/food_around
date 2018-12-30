package com.opalfire.orderaround;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation.OnTabSelectedListener;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation.TitleState;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.tasks.OnSuccessListener;
import com.orderaround.user.build.api.ApiClient;
import com.orderaround.user.build.api.ApiInterface;
import com.orderaround.user.fragments.CartFragment;
import com.orderaround.user.fragments.HomeFragment;
import com.orderaround.user.fragments.ProfileFragment;
import com.orderaround.user.fragments.SearchFragment;
import com.orderaround.user.helper.ConnectionHelper;
import com.orderaround.user.helper.GlobalData;
import com.orderaround.user.helper.SharedHelper;
import com.orderaround.user.models.Cart;
import com.orderaround.user.models.DisputeMessage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import okhttp3.ResponseBody;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HomeActivity extends AppCompatActivity implements LocationListener, ConnectionCallbacks, OnConnectionFailedListener, OnRequestPermissionsResultCallback {
    private static final int ASK_MULTIPLE_PERMISSION_REQUEST_CODE = 0;
    private static final int PLAY_SERVICES_REQUEST = 1000;
    private static final int REQUEST_CHECK_SETTINGS = 2000;
    private static final String TAG = "HomeActivity";
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    public static AHBottomNavigation bottomNavigation;
    public static double latitude;
    public static double longitude;
    public static AHNotification notification;
    ApiInterface apiInterface = ((ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class));
    private ConnectionHelper connectionHelper;
    Context context = this;
    private Fragment fragment;
    private FragmentManager fragmentManager;
    boolean isChangePassword = false;
    int itemCount = 0;
    FusedLocationProviderClient mFusedLocationClient;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    LocationRequest mLocationRequest;
    Retrofit retrofit;
    FragmentTransaction transaction;

    /* renamed from: com.orderaround.user.HomeActivity$7 */
    class C07057 implements OnClickListener {
        C07057() {
        }

        public void onClick(View view) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 0);
        }
    }

    /* renamed from: com.orderaround.user.HomeActivity$1 */
    class C12621 implements OnSuccessListener<Location> {
        C12621() {
        }

        public void onSuccess(Location location) {
            if (location != null) {
                HomeActivity.latitude = location.getLatitude();
                HomeActivity.longitude = location.getLongitude();
                GlobalData.latitude = location.getLatitude();
                GlobalData.longitude = location.getLongitude();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(location.getLatitude());
                Log.e("latitude3", stringBuilder.toString());
                stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(location.getLongitude());
                Log.e("longitude3", stringBuilder.toString());
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("");
                stringBuilder2.append(GlobalData.latitude);
                Log.e("GlobalData.latitude3", stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("");
                stringBuilder2.append(GlobalData.longitude);
                Log.e("GlobalData.longitude3 ", stringBuilder2.toString());
                HomeActivity.this.getAddress();
            }
        }
    }

    /* renamed from: com.orderaround.user.HomeActivity$2 */
    class C12632 implements OnSuccessListener<Location> {
        C12632() {
        }

        public void onSuccess(Location location) {
            if (location != null) {
                HomeActivity.latitude = location.getLatitude();
                HomeActivity.longitude = location.getLongitude();
                GlobalData.latitude = location.getLatitude();
                GlobalData.longitude = location.getLongitude();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(location.getLatitude());
                Log.e("latitude3", stringBuilder.toString());
                stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(location.getLongitude());
                Log.e("longitude3", stringBuilder.toString());
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("");
                stringBuilder2.append(GlobalData.latitude);
                Log.e("GlobalData.latitude3", stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("");
                stringBuilder2.append(GlobalData.longitude);
                Log.e("GlobalData.longitude3 ", stringBuilder2.toString());
                HomeActivity.this.getAddress();
            }
        }
    }

    /* renamed from: com.orderaround.user.HomeActivity$3 */
    class C12643 implements OnTabSelectedListener {
        C12643() {
        }

        public boolean onTabSelected(int i, boolean z) {
            switch (i) {
                case 0:
                    HomeActivity.this.fragment = new HomeFragment();
                    break;
                case 1:
                    HomeActivity.this.fragment = new SearchFragment();
                    break;
                case 2:
                    HomeActivity.this.fragment = new CartFragment();
                    break;
                case 3:
                    HomeActivity.this.fragment = new ProfileFragment();
                    break;
                default:
                    break;
            }
            HomeActivity.this.transaction = HomeActivity.this.fragmentManager.beginTransaction();
            HomeActivity.this.transaction.replace(true, HomeActivity.this.fragment).commit();
            return true;
        }
    }

    /* renamed from: com.orderaround.user.HomeActivity$4 */
    class C12654 implements Callback<List<DisputeMessage>> {
        C12654() {
        }

        public void onResponse(Call<List<DisputeMessage>> call, Response<List<DisputeMessage>> response) {
            if (response.isSuccessful() != null) {
                Log.e("Dispute List : ", response.toString());
                GlobalData.disputeMessageList = new ArrayList();
                GlobalData.disputeMessageList.addAll((Collection) response.body());
                return;
            }
            try {
                Toast.makeText(HomeActivity.this.context, new JSONObject(response.errorBody().toString()).optString("message"), 1).show();
            } catch (Response<List<DisputeMessage>> response2) {
                Toast.makeText(HomeActivity.this.context, response2.getMessage(), 1).show();
            }
        }

        public void onFailure(Call<List<DisputeMessage>> call, Throwable th) {
            Toast.makeText(HomeActivity.this.context, "Something went wrong", 0).show();
        }
    }

    /* renamed from: com.orderaround.user.HomeActivity$5 */
    class C12665 implements Callback<ResponseBody> {
        C12665() {
        }

        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SUCESS");
            stringBuilder.append(response.body());
            Log.e("sUCESS", stringBuilder.toString());
            if (response.body() != null) {
                call = new Intent("location");
                call.putExtra("message", "This is my message!");
                LocalBroadcastManager.getInstance(HomeActivity.this.context).sendBroadcast(call);
                try {
                    call = new String(((ResponseBody) response.body()).bytes());
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("bodyString");
                    stringBuilder.append(call);
                    Log.e("sUCESS", stringBuilder.toString());
                    response = null;
                    try {
                        response = new JSONObject(call);
                    } catch (Call<ResponseBody> call2) {
                        call2.printStackTrace();
                    }
                    call2 = response.optJSONArray("results");
                    if (call2.length() > null) {
                        if (GlobalData.addressHeader.equalsIgnoreCase("") != null) {
                            GlobalData.addressHeader = call2.optJSONObject(0).optString("formatted_address");
                            GlobalData.address = call2.optJSONObject(0).optString("formatted_address");
                            response = new StringBuilder();
                            response.append("");
                            response.append(GlobalData.addressHeader);
                            Log.v("Formatted Address", response.toString());
                            return;
                        }
                        return;
                    } else if (GlobalData.addressHeader.equalsIgnoreCase("") != null) {
                        call2 = new StringBuilder();
                        call2.append("");
                        call2.append(HomeActivity.latitude);
                        call2.append("");
                        call2.append(HomeActivity.longitude);
                        GlobalData.addressHeader = call2.toString();
                        call2 = new StringBuilder();
                        call2.append("");
                        call2.append(HomeActivity.latitude);
                        call2.append("");
                        call2.append(HomeActivity.longitude);
                        GlobalData.address = call2.toString();
                        return;
                    } else {
                        return;
                    }
                } catch (Call<ResponseBody> call22) {
                    call22.printStackTrace();
                    return;
                }
            }
            call22 = new StringBuilder();
            call22.append("");
            call22.append(HomeActivity.latitude);
            call22.append("");
            call22.append(HomeActivity.longitude);
            GlobalData.addressHeader = call22.toString();
            call22 = new StringBuilder();
            call22.append("");
            call22.append(HomeActivity.latitude);
            call22.append("");
            call22.append(HomeActivity.longitude);
            GlobalData.address = call22.toString();
        }

        public void onFailure(Call<ResponseBody> call, Throwable th) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onFailure");
            stringBuilder.append(call.request().url());
            Log.e("onFailure", stringBuilder.toString());
        }
    }

    /* renamed from: com.orderaround.user.HomeActivity$6 */
    class C12676 implements ResultCallback<LocationSettingsResult> {
        C12676() {
        }

        public void onResult(LocationSettingsResult r3) {
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
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:56)
	at jadx.core.ProcessClass.process(ProcessClass.java:39)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/193388045.run(Unknown Source)
*/
            /*
            r2 = this;
            r3 = r3.getStatus();
            r0 = r3.getStatusCode();
            if (r0 == 0) goto L_0x0016;
        L_0x000a:
            r1 = 6;
            if (r0 == r1) goto L_0x000e;
        L_0x000d:
            goto L_0x001b;
        L_0x000e:
            r0 = com.orderaround.user.HomeActivity.this;	 Catch:{ SendIntentException -> 0x001b }
            r1 = 2000; // 0x7d0 float:2.803E-42 double:9.88E-321;	 Catch:{ SendIntentException -> 0x001b }
            r3.startResolutionForResult(r0, r1);	 Catch:{ SendIntentException -> 0x001b }
            goto L_0x001b;
        L_0x0016:
            r3 = com.orderaround.user.HomeActivity.this;
            r3.getLocation();
        L_0x001b:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.orderaround.user.HomeActivity.6.onResult(com.google.android.gms.location.LocationSettingsResult):void");
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (SharedHelper.getKey(this.context, "login_by").equals("facebook") != null) {
            FacebookSdk.sdkInitialize(getApplicationContext());
        }
        setContentView((int) C0709R.layout.activity_home);
        this.connectionHelper = new ConnectionHelper(this);
        this.isChangePassword = getIntent().getBooleanExtra("change_language", false);
        this.mFusedLocationClient = LocationServices.getFusedLocationProviderClient((Activity) this);
        if (VERSION.SDK_INT < 23) {
            this.mFusedLocationClient.getLastLocation().addOnSuccessListener((Activity) this, new C12632());
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == null && ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == null) {
            this.mFusedLocationClient.getLastLocation().addOnSuccessListener((Activity) this, new C12621());
        } else {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"}, 0);
        }
        if (checkPlayServices() != null) {
            buildGoogleApiClient();
        }
        if (GlobalData.profileModel != null) {
            getNotificationItemCount();
        }
        this.fragmentManager = getSupportFragmentManager();
        this.transaction = this.fragmentManager.beginTransaction();
        bottomNavigation = (AHBottomNavigation) findViewById(C0709R.id.bottom_navigation);
        bundle = new AHBottomNavigationItem((int) C0709R.string.home, (int) C0709R.drawable.ic_home, (int) C0709R.color.grey);
        AHBottomNavigationItem aHBottomNavigationItem = new AHBottomNavigationItem((int) C0709R.string.search, (int) C0709R.drawable.ic_search, (int) C0709R.color.grey);
        AHBottomNavigationItem aHBottomNavigationItem2 = new AHBottomNavigationItem("Cart", (int) C0709R.drawable.ic_cart, (int) C0709R.color.grey);
        AHBottomNavigationItem aHBottomNavigationItem3 = new AHBottomNavigationItem("Profile", (int) C0709R.drawable.ic_user, (int) C0709R.color.grey);
        bottomNavigation.addItem(bundle);
        bottomNavigation.addItem(aHBottomNavigationItem);
        bottomNavigation.addItem(aHBottomNavigationItem2);
        bottomNavigation.addItem(aHBottomNavigationItem3);
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));
        bottomNavigation.setBehaviorTranslationEnabled(true);
        bottomNavigation.setTranslucentNavigationEnabled(true);
        bottomNavigation.setTitleState(TitleState.ALWAYS_HIDE);
        bottomNavigation.setAccentColor(Color.parseColor("#FF5722"));
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));
        if (this.isChangePassword != null) {
            this.fragment = new ProfileFragment();
            this.transaction.add((int) C0709R.id.main_container, this.fragment).commit();
            bottomNavigation.setCurrentItem(3);
        } else {
            this.fragment = new HomeFragment();
            this.transaction.add((int) C0709R.id.main_container, this.fragment).commit();
            bottomNavigation.setCurrentItem(View.VISIBLE);
        }
        bottomNavigation.setOnTabSelectedListener(new C12643());
        if (GlobalData.profileModel != null) {
            getDisputeMessage();
        }
    }

    private void getDisputeMessage() {
        this.apiInterface.getDisputeList().enqueue(new C12654());
    }

    private void getLocation() {
        try {
            this.mLastLocation = LocationServices.FusedLocationApi.getLastLocation(this.mGoogleApiClient);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        if (this.mLastLocation == null) {
            this.mLocationRequest = new LocationRequest();
            this.mLocationRequest = new LocationRequest();
            this.mLocationRequest.setInterval(1000);
            this.mLocationRequest.setFastestInterval(1000);
            this.mLocationRequest.setPriority(102);
            if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
                LocationServices.FusedLocationApi.requestLocationUpdates(this.mGoogleApiClient, this.mLocationRequest, (LocationListener) this);
                return;
            }
            return;
        }
        latitude = this.mLastLocation.getLatitude();
        longitude = this.mLastLocation.getLongitude();
        GlobalData.latitude = this.mLastLocation.getLatitude();
        GlobalData.longitude = this.mLastLocation.getLongitude();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this.mLastLocation.getLatitude());
        Log.e("latitude", stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this.mLastLocation.getLongitude());
        Log.e("longitude", stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(GlobalData.latitude);
        Log.e("GlobalData.latitude", stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(GlobalData.longitude);
        Log.e("GlobalData.longitude ", stringBuilder.toString());
        getAddress();
    }

    public void getAddress() {
        this.retrofit = new Builder().baseUrl("https://maps.googleapis.com/maps/api/geocode/").addConverterFactory(GsonConverterFactory.create()).build();
        this.apiInterface = (ApiInterface) this.retrofit.create(ApiInterface.class);
        ApiInterface apiInterface = this.apiInterface;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(latitude);
        stringBuilder.append(",");
        stringBuilder.append(longitude);
        apiInterface.getResponse(stringBuilder.toString(), this.context.getResources().getString(C0709R.string.google_api_key)).enqueue(new C12665());
    }

    protected synchronized void buildGoogleApiClient() {
        this.mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        this.mGoogleApiClient.connect();
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(100);
        LocationServices.SettingsApi.checkLocationSettings(this.mGoogleApiClient, new LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build()).setResultCallback(new C12676());
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

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == CredentialsApi.CREDENTIAL_PICKER_REQUEST_CODE) {
            switch (i2) {
                case -1:
                    getLocation();
                    return;
                case 0:
                    return;
                default:
                    return;
            }
        }
    }

    public void getNotificationItemCount() {
        if (GlobalData.addCart != null && GlobalData.addCart.getProductList().size() != 0) {
            this.itemCount = GlobalData.addCart.getProductList().size();
            int i = 0;
            for (int i2 = 0; i2 < this.itemCount; i2++) {
                i += ((Cart) GlobalData.addCart.getProductList().get(i2)).getQuantity().intValue();
            }
            GlobalData.notificationCount = i;
        }
    }

    public void onResume() {
        super.onResume();
        this.connectionHelper.isConnectingToInternet();
        updateNotificationCount(this.context, GlobalData.notificationCount);
    }

    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit!", 0).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Connection failed: ConnectionResult.getErrorCode() = ");
        stringBuilder.append(connectionResult.getErrorCode());
        Log.i(str, stringBuilder.toString());
    }

    public void onConnected(Bundle bundle) {
        getLocation();
    }

    public void onConnectionSuspended(int i) {
        this.mGoogleApiClient.connect();
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
                    Snackbar.make(findViewById(16908290), (CharSequence) "Please Grant Permissions to start service", (int) -2).setAction((CharSequence) "ENABLE", new C07057()).show();
                } else {
                    getLocation();
                }
            }
        }
    }

    public void showToast(String str) {
        Toast.makeText(this, str, 0).show();
    }

    public static void updateNotificationCount(Context context, int i) {
        if (i == 0) {
            notification = null;
            if (bottomNavigation != null) {
                bottomNavigation.setNotification(notification, 2);
            }
        } else if (bottomNavigation != null) {
            bottomNavigation.setNotificationBackgroundColor(ContextCompat.getColor(context, C0709R.color.theme));
            bottomNavigation.setNotification(String.valueOf(i), 2);
        }
    }

    public void onLocationChanged(Location location) {
        this.mLastLocation = location;
        latitude = this.mLastLocation.getLatitude();
        longitude = this.mLastLocation.getLongitude();
        GlobalData.latitude = this.mLastLocation.getLatitude();
        GlobalData.longitude = this.mLastLocation.getLongitude();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this.mLastLocation.getLatitude());
        Log.e("latitude2", stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(this.mLastLocation.getLongitude());
        Log.e("longitude2", stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(GlobalData.latitude);
        Log.e("GlobalData.latitude2", stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(GlobalData.longitude);
        Log.e("GlobalData.longitude2 ", stringBuilder.toString());
        getAddress();
    }
}
