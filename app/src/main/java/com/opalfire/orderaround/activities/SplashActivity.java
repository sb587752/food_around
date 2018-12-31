package com.opalfire.orderaround.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.facebook.internal.ServerProtocol;
import com.opalfire.orderaround.BuildConfig;
import com.opalfire.orderaround.HomeActivity;
import com.opalfire.orderaround.R;
import com.opalfire.orderaround.build.api.ApiClient;
import com.opalfire.orderaround.build.api.ApiInterface;
import com.opalfire.orderaround.helper.ConnectionHelper;
import com.opalfire.orderaround.helper.GlobalData;
import com.opalfire.orderaround.helper.SharedHelper;
import com.opalfire.orderaround.models.AddCart;
import com.opalfire.orderaround.models.AddressList;
import com.opalfire.orderaround.models.Cart;
import com.opalfire.orderaround.models.User;
import com.opalfire.orderaround.utils.Utils;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.HashMap;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashActivity extends AppCompatActivity {
    String TAG = "Login";
    ApiInterface apiInterface = ((ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class));
    ConnectionHelper connectionHelper;
    Context context;
    String device_UDID;
    String device_token;
    int retryCount = 0;
    Utils utils = new Utils();

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Fabric.with(this, new Kit[]{new Crashlytics()});
        setContentView((int) R.layout.activity_splash);
        this.context = this;
        this.connectionHelper = new ConnectionHelper(this.context);
        getDeviceToken();
        new Handler().postDelayed(new C07651(), 3000);
        getHashKey();
    }

    private void getHashKey() {
        try {
            for (Signature signature : getPackageManager().getPackageInfo(BuildConfig.APPLICATION_ID, 64).signatures) {
                MessageDigest instance = MessageDigest.getInstance("SHA");
                instance.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(instance.digest(), 0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDeviceToken() {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/193388045.run(Unknown Source)
*/
        /*
        r4 = this;
        r0 = r4.context;	 Catch:{ Exception -> 0x0091 }
        r1 = "device_token";	 Catch:{ Exception -> 0x0091 }
        r0 = com.entriver.orderaround.helper.SharedHelper.getKey(r0, r1);	 Catch:{ Exception -> 0x0091 }
        r1 = "";	 Catch:{ Exception -> 0x0091 }
        r0 = r0.equals(r1);	 Catch:{ Exception -> 0x0091 }
        if (r0 != 0) goto L_0x003d;	 Catch:{ Exception -> 0x0091 }
    L_0x0010:
        r0 = r4.context;	 Catch:{ Exception -> 0x0091 }
        r1 = "device_token";	 Catch:{ Exception -> 0x0091 }
        r0 = com.entriver.orderaround.helper.SharedHelper.getKey(r0, r1);	 Catch:{ Exception -> 0x0091 }
        if (r0 == 0) goto L_0x003d;	 Catch:{ Exception -> 0x0091 }
    L_0x001a:
        r0 = r4.context;	 Catch:{ Exception -> 0x0091 }
        r1 = "device_token";	 Catch:{ Exception -> 0x0091 }
        r0 = com.entriver.orderaround.helper.SharedHelper.getKey(r0, r1);	 Catch:{ Exception -> 0x0091 }
        r4.device_token = r0;	 Catch:{ Exception -> 0x0091 }
        r0 = r4.TAG;	 Catch:{ Exception -> 0x0091 }
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0091 }
        r1.<init>();	 Catch:{ Exception -> 0x0091 }
        r2 = "GCM Registration Token: ";	 Catch:{ Exception -> 0x0091 }
        r1.append(r2);	 Catch:{ Exception -> 0x0091 }
        r2 = r4.device_token;	 Catch:{ Exception -> 0x0091 }
        r1.append(r2);	 Catch:{ Exception -> 0x0091 }
        r1 = r1.toString();	 Catch:{ Exception -> 0x0091 }
        android.util.Log.d(r0, r1);	 Catch:{ Exception -> 0x0091 }
        goto L_0x009c;	 Catch:{ Exception -> 0x0091 }
    L_0x003d:
        r0 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0091 }
        r0.<init>();	 Catch:{ Exception -> 0x0091 }
        r1 = "";	 Catch:{ Exception -> 0x0091 }
        r0.append(r1);	 Catch:{ Exception -> 0x0091 }
        r1 = com.google.firebase.iid.FirebaseInstanceId.getInstance();	 Catch:{ Exception -> 0x0091 }
        r1 = r1.getToken();	 Catch:{ Exception -> 0x0091 }
        r0.append(r1);	 Catch:{ Exception -> 0x0091 }
        r0 = r0.toString();	 Catch:{ Exception -> 0x0091 }
        r4.device_token = r0;	 Catch:{ Exception -> 0x0091 }
        r0 = r4.context;	 Catch:{ Exception -> 0x0091 }
        r1 = "device_token";	 Catch:{ Exception -> 0x0091 }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0091 }
        r2.<init>();	 Catch:{ Exception -> 0x0091 }
        r3 = "";	 Catch:{ Exception -> 0x0091 }
        r2.append(r3);	 Catch:{ Exception -> 0x0091 }
        r3 = com.google.firebase.iid.FirebaseInstanceId.getInstance();	 Catch:{ Exception -> 0x0091 }
        r3 = r3.getToken();	 Catch:{ Exception -> 0x0091 }
        r2.append(r3);	 Catch:{ Exception -> 0x0091 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0091 }
        com.entriver.orderaround.helper.SharedHelper.putKey(r0, r1, r2);	 Catch:{ Exception -> 0x0091 }
        r0 = r4.TAG;	 Catch:{ Exception -> 0x0091 }
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0091 }
        r1.<init>();	 Catch:{ Exception -> 0x0091 }
        r2 = "Failed to complete token refresh: ";	 Catch:{ Exception -> 0x0091 }
        r1.append(r2);	 Catch:{ Exception -> 0x0091 }
        r2 = r4.device_token;	 Catch:{ Exception -> 0x0091 }
        r1.append(r2);	 Catch:{ Exception -> 0x0091 }
        r1 = r1.toString();	 Catch:{ Exception -> 0x0091 }
        android.util.Log.d(r0, r1);	 Catch:{ Exception -> 0x0091 }
        goto L_0x009c;
    L_0x0091:
        r0 = "COULD NOT GET FCM TOKEN";
        r4.device_token = r0;
        r0 = r4.TAG;
        r1 = "Failed to complete token refresh";
        android.util.Log.d(r0, r1);
    L_0x009c:
        r0 = r4.getContentResolver();	 Catch:{ Exception -> 0x00c1 }
        r1 = "android_id";	 Catch:{ Exception -> 0x00c1 }
        r0 = android.provider.Settings.Secure.getString(r0, r1);	 Catch:{ Exception -> 0x00c1 }
        r4.device_UDID = r0;	 Catch:{ Exception -> 0x00c1 }
        r0 = r4.TAG;	 Catch:{ Exception -> 0x00c1 }
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00c1 }
        r1.<init>();	 Catch:{ Exception -> 0x00c1 }
        r2 = "Device UDID:";	 Catch:{ Exception -> 0x00c1 }
        r1.append(r2);	 Catch:{ Exception -> 0x00c1 }
        r2 = r4.device_UDID;	 Catch:{ Exception -> 0x00c1 }
        r1.append(r2);	 Catch:{ Exception -> 0x00c1 }
        r1 = r1.toString();	 Catch:{ Exception -> 0x00c1 }
        android.util.Log.d(r0, r1);	 Catch:{ Exception -> 0x00c1 }
        goto L_0x00d0;
    L_0x00c1:
        r0 = move-exception;
        r1 = "COULD NOT GET UDID";
        r4.device_UDID = r1;
        r0.printStackTrace();
        r0 = r4.TAG;
        r1 = "Failed to complete device UDID";
        android.util.Log.d(r0, r1);
    L_0x00d0:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.entriver.orderaround.activities.SplashActivity.getDeviceToken():void");
    }

    private void getProfile() {
        this.retryCount++;
        HashMap hashMap = new HashMap();
        hashMap.put("device_type", "android");
        hashMap.put("device_id", this.device_UDID);
        hashMap.put("device_token", this.device_token);
        this.apiInterface.getProfile(hashMap).enqueue(new C13732());
    }

    public void displayMessage(java.lang.String r4) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/193388045.run(Unknown Source)
*/
        /*
        r3 = this;
        r0 = r3.getCurrentFocus();	 Catch:{ Exception -> 0x0014 }
        r1 = -1;	 Catch:{ Exception -> 0x0014 }
        r0 = android.support.design.widget.Snackbar.make(r0, r4, r1);	 Catch:{ Exception -> 0x0014 }
        r1 = "Action";	 Catch:{ Exception -> 0x0014 }
        r2 = 0;	 Catch:{ Exception -> 0x0014 }
        r0 = r0.setAction(r1, r2);	 Catch:{ Exception -> 0x0014 }
        r0.show();	 Catch:{ Exception -> 0x0014 }
        goto L_0x0034;
    L_0x0014:
        r0 = r3.context;	 Catch:{ Exception -> 0x0030 }
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0030 }
        r1.<init>();	 Catch:{ Exception -> 0x0030 }
        r2 = "";	 Catch:{ Exception -> 0x0030 }
        r1.append(r2);	 Catch:{ Exception -> 0x0030 }
        r1.append(r4);	 Catch:{ Exception -> 0x0030 }
        r4 = r1.toString();	 Catch:{ Exception -> 0x0030 }
        r1 = 0;	 Catch:{ Exception -> 0x0030 }
        r4 = android.widget.Toast.makeText(r0, r4, r1);	 Catch:{ Exception -> 0x0030 }
        r4.show();	 Catch:{ Exception -> 0x0030 }
        goto L_0x0034;
    L_0x0030:
        r4 = move-exception;
        r4.printStackTrace();
    L_0x0034:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.entriver.orderaround.activities.SplashActivity.displayMessage(java.lang.String):void");
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    /* renamed from: com.entriver.orderaround.activities.SplashActivity$1 */
    class C07651 implements Runnable {
        C07651() {
        }

        public void run() {
            if (!SharedHelper.getKey(SplashActivity.this.context, "logged").equalsIgnoreCase(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE) || SharedHelper.getKey(SplashActivity.this.context, "logged") == null) {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, WelcomeScreenActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                SplashActivity.this.finish();
                return;
            }
            GlobalData.accessToken = SharedHelper.getKey(SplashActivity.this.context, "access_token");
            if (SplashActivity.this.connectionHelper.isConnectingToInternet()) {
                SplashActivity.this.getProfile();
            } else {
                SplashActivity.this.displayMessage(SplashActivity.this.getString(R.string.oops_connect_your_internet));
            }
        }
    }

    /* renamed from: com.entriver.orderaround.activities.SplashActivity$2 */
    class C13732 implements Callback<User> {
        C13732() {
        }

        public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
            if (response.isSuccessful() != null) {
                SharedHelper.putKey(SplashActivity.this.context, "logged", ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
                GlobalData.profileModel = (User) response.body();
                GlobalData.addCart = new AddCart();
                GlobalData.addCart.setProductList(((User) response.body()).getCart());
                GlobalData.addressList = new AddressList();
                GlobalData.addressList.setAddresses(((User) response.body()).getAddresses());
                if (!(GlobalData.addCart.getProductList() == null || GlobalData.addCart.getProductList().size() == null)) {
                    GlobalData.addCartShopId = ((Cart) GlobalData.addCart.getProductList().get(null)).getProduct().getShopId().intValue();
                }
                SplashActivity.this.startActivity(new Intent(SplashActivity.this.context, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                SplashActivity.this.finish();
                return;
            }
            if (response.code() == 401) {
                SharedHelper.putKey(SplashActivity.this.context, "logged", "false");
                SplashActivity.this.startActivity(new Intent(SplashActivity.this.context, LoginActivity.class));
                SplashActivity.this.finish();
            }
            try {
                Toast.makeText(SplashActivity.this.context, new JSONObject(response.errorBody().toString()).optString("error"), 1).show();
            } catch (Response<User> response2) {
                Toast.makeText(SplashActivity.this.context, response2.getMessage(), 1).show();
            }
        }

        public void onFailure(@NonNull Call<User> call, @NonNull Throwable th) {
            if (SplashActivity.this.retryCount < 5) {
                SplashActivity.this.getProfile();
            }
        }
    }
}
