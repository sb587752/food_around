package com.opalfire.foodorder.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.internal.ServerProtocol;
import com.opalfire.foodorder.HomeActivity;
import com.opalfire.foodorder.R;
import com.opalfire.foodorder.build.api.ApiClient;
import com.opalfire.foodorder.build.api.ApiInterface;
import com.opalfire.foodorder.helper.CustomDialog;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.helper.SharedHelper;
import com.opalfire.foodorder.models.AddCart;
import com.opalfire.foodorder.models.AddressList;
import com.opalfire.foodorder.models.LoginModel;
import com.opalfire.foodorder.models.Otp;
import com.opalfire.foodorder.models.RegisterModel;
import com.opalfire.foodorder.models.User;
import com.opalfire.foodorder.utils.Utils;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.philio.pinentry.PinEntryView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class OtpActivity extends AppCompatActivity {
    String TAG = "OTPACTIVITY";
    ApiInterface apiInterface = ((ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class));
    Context context;
    CustomDialog customDialog;
    String device_UDID;
    String device_token;
    boolean isSignUp = true;
    @BindView(2131296653)
    TextView mobileNumberTxt;
    @BindView(2131296707)
    Button otpContinue;
    @BindView(2131296708)
    ImageView otpImage;
    @BindView(2131296709)
    PinEntryView otpValue1;
    @BindView(2131296770)
    RelativeLayout relVerificatinCode;
    Utils utils = new Utils();
    @BindView(2131296951)
    TextView veriTxt1;
    @BindView(2131296952)
    TextView veriTxt2;
    @BindView(2131296953)
    TextView verificationCodeTxt;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_otp);
        ButterKnife.bind((Activity) this);
        this.context = this;
        this.customDialog = new CustomDialog(this.context);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            this.isSignUp = bundle.getBoolean("signup", true);
        }
        this.mobileNumberTxt.setText(GlobalData.mobile);
        this.otpValue1.setText(String.valueOf(GlobalData.otpValue));
        getDeviceToken();
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
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:56)
	at jadx.core.ProcessClass.process(ProcessClass.java:39)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/193388045.run(Unknown Source)
*/
        /*
        r4 = this;
        r0 = r4.context;	 Catch:{ Exception -> 0x0091 }
        r1 = "device_token";	 Catch:{ Exception -> 0x0091 }
        r0 = com.opalfire.foodorder.helper.SharedHelper.getKey(r0, r1);	 Catch:{ Exception -> 0x0091 }
        r1 = "";	 Catch:{ Exception -> 0x0091 }
        r0 = r0.equals(r1);	 Catch:{ Exception -> 0x0091 }
        if (r0 != 0) goto L_0x003d;	 Catch:{ Exception -> 0x0091 }
    L_0x0010:
        r0 = r4.context;	 Catch:{ Exception -> 0x0091 }
        r1 = "device_token";	 Catch:{ Exception -> 0x0091 }
        r0 = com.opalfire.foodorder.helper.SharedHelper.getKey(r0, r1);	 Catch:{ Exception -> 0x0091 }
        if (r0 == 0) goto L_0x003d;	 Catch:{ Exception -> 0x0091 }
    L_0x001a:
        r0 = r4.context;	 Catch:{ Exception -> 0x0091 }
        r1 = "device_token";	 Catch:{ Exception -> 0x0091 }
        r0 = com.opalfire.foodorder.helper.SharedHelper.getKey(r0, r1);	 Catch:{ Exception -> 0x0091 }
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
        com.opalfire.foodorder.helper.SharedHelper.putKey(r0, r1, r2);	 Catch:{ Exception -> 0x0091 }
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
        throw new UnsupportedOperationException("Method not decompiled: com.opalfire.foodorder.activities.OtpActivity.getDeviceToken():void");
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    public void signup(HashMap<String, String> hashMap) {
        this.customDialog.show();
        this.apiInterface.postRegister(hashMap).enqueue(new C13311());
    }

    private void login(HashMap<String, String> hashMap) {
        if (GlobalData.loginBy.equals("manual")) {
            hashMap = this.apiInterface.postLogin(hashMap);
        } else {
            hashMap = this.apiInterface.postSocialLogin(hashMap);
        }
        hashMap.enqueue(new C13322());
    }

    private void getProfile() {
        HashMap hashMap = new HashMap();
        hashMap.put("device_type", "android");
        hashMap.put("device_id", this.device_UDID);
        hashMap.put("device_token", this.device_token);
        this.apiInterface.getProfile(hashMap).enqueue(new C13333());
    }

    public void getOtpVerification(HashMap<String, String> hashMap) {
        this.customDialog.show();
        this.apiInterface.postOtp(hashMap).enqueue(new C13344());
    }

    @OnClick({2131296707, 2131296775})
    public void onViewClicked(View view) {
        view = view.getId();
        if (view == R.id.otp_continue) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.otpValue1.getText().toString());
            stringBuilder.append(" = ");
            stringBuilder.append(GlobalData.otpValue);
            Log.d("OtpData", stringBuilder.toString());
            view = this.otpValue1.getText().toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(GlobalData.otpValue);
            if (view.equals(stringBuilder.toString()) == null) {
                Toast.makeText(this, "Enter otp is incorrect", 0).show();
            } else if (GlobalData.loginBy.equals("manual") == null) {
                view = new HashMap();
                view.put("name", GlobalData.name);
                view.put("email", GlobalData.email);
                view.put("phone", GlobalData.mobile);
                view.put("login_by", GlobalData.loginBy);
                view.put("accessToken", GlobalData.access_token);
                signup(view);
            } else if (this.isSignUp != null) {
                startActivity(new Intent(this, SignUpActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
                finish();
            } else {
                startActivity(new Intent(this, ResetPasswordActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
                finish();
            }
        } else if (view == R.id.resend_otp) {
            view = new HashMap();
            view.put("phone", GlobalData.mobileNumber);
            getOtpVerification(view);
        }
    }

    /* renamed from: com.opalfire.foodorder.activities.OtpActivity$1 */
    class C13311 implements Callback<RegisterModel> {
        C13311() {
        }

        public void onResponse(@NonNull Call<com.opalfire.foodorder.models.RegisterModel> r3, @NonNull Response<com.opalfire.foodorder.models.RegisterModel> r4) {
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
            r3 = r4.isSuccessful();
            if (r3 == 0) goto L_0x001f;
        L_0x0006:
            r3 = new java.util.HashMap;
            r3.<init>();
            r4 = "login_by";
            r0 = com.opalfire.foodorder.helper.GlobalData.loginBy;
            r3.put(r4, r0);
            r4 = "accessToken";
            r0 = com.opalfire.foodorder.helper.GlobalData.access_token;
            r3.put(r4, r0);
            r4 = com.opalfire.foodorder.activities.OtpActivity.this;
            r4.login(r3);
            goto L_0x0087;
        L_0x001f:
            r3 = com.opalfire.foodorder.activities.OtpActivity.this;
            r3 = r3.customDialog;
            r3.dismiss();
            r3 = 1;
            r0 = new org.json.JSONObject;	 Catch:{ Exception -> 0x007a }
            r4 = r4.errorBody();	 Catch:{ Exception -> 0x007a }
            r4 = r4.string();	 Catch:{ Exception -> 0x007a }
            r0.<init>(r4);	 Catch:{ Exception -> 0x007a }
            r4 = "phone";	 Catch:{ Exception -> 0x007a }
            r4 = r0.has(r4);	 Catch:{ Exception -> 0x007a }
            if (r4 == 0) goto L_0x004e;	 Catch:{ Exception -> 0x007a }
        L_0x003c:
            r4 = com.opalfire.foodorder.activities.OtpActivity.this;	 Catch:{ Exception -> 0x007a }
            r4 = r4.context;	 Catch:{ Exception -> 0x007a }
            r1 = "phone";	 Catch:{ Exception -> 0x007a }
            r0 = r0.optString(r1);	 Catch:{ Exception -> 0x007a }
            r4 = android.widget.Toast.makeText(r4, r0, r3);	 Catch:{ Exception -> 0x007a }
            r4.show();	 Catch:{ Exception -> 0x007a }
            goto L_0x0087;	 Catch:{ Exception -> 0x007a }
        L_0x004e:
            r4 = "email";	 Catch:{ Exception -> 0x007a }
            r4 = r0.has(r4);	 Catch:{ Exception -> 0x007a }
            if (r4 == 0) goto L_0x0068;	 Catch:{ Exception -> 0x007a }
        L_0x0056:
            r4 = com.opalfire.foodorder.activities.OtpActivity.this;	 Catch:{ Exception -> 0x007a }
            r4 = r4.context;	 Catch:{ Exception -> 0x007a }
            r1 = "email";	 Catch:{ Exception -> 0x007a }
            r0 = r0.optString(r1);	 Catch:{ Exception -> 0x007a }
            r4 = android.widget.Toast.makeText(r4, r0, r3);	 Catch:{ Exception -> 0x007a }
            r4.show();	 Catch:{ Exception -> 0x007a }
            goto L_0x0087;	 Catch:{ Exception -> 0x007a }
        L_0x0068:
            r4 = com.opalfire.foodorder.activities.OtpActivity.this;	 Catch:{ Exception -> 0x007a }
            r4 = r4.context;	 Catch:{ Exception -> 0x007a }
            r1 = "error";	 Catch:{ Exception -> 0x007a }
            r0 = r0.optString(r1);	 Catch:{ Exception -> 0x007a }
            r4 = android.widget.Toast.makeText(r4, r0, r3);	 Catch:{ Exception -> 0x007a }
            r4.show();	 Catch:{ Exception -> 0x007a }
            goto L_0x0087;
        L_0x007a:
            r4 = com.opalfire.foodorder.activities.OtpActivity.this;
            r4 = r4.context;
            r0 = "Something went wrong";
            r3 = android.widget.Toast.makeText(r4, r0, r3);
            r3.show();
        L_0x0087:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.opalfire.foodorder.activities.OtpActivity.1.onResponse(retrofit2.Call, retrofit2.Response):void");
        }

        public void onFailure(@NonNull Call<RegisterModel> call, @NonNull Throwable th) {
            Toast.makeText(OtpActivity.this, "Something went wrong", 0).show();
            OtpActivity.this.customDialog.dismiss();
        }
    }

    /* renamed from: com.opalfire.foodorder.activities.OtpActivity$2 */
    class C13322 implements Callback<LoginModel> {
        C13322() {
        }

        public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable th) {
        }

        public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
            if (response.isSuccessful() != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(((LoginModel) response.body()).getTokenType());
                stringBuilder.append(" ");
                stringBuilder.append(((LoginModel) response.body()).getAccessToken());
                SharedHelper.putKey(OtpActivity.this.context, "access_token", stringBuilder.toString());
                call = new StringBuilder();
                call.append(((LoginModel) response.body()).getTokenType());
                call.append(" ");
                call.append(((LoginModel) response.body()).getAccessToken());
                GlobalData.accessToken = call.toString();
                OtpActivity.this.getProfile();
            }
        }
    }

    /* renamed from: com.opalfire.foodorder.activities.OtpActivity$3 */
    class C13333 implements Callback<User> {
        C13333() {
        }

        public void onFailure(@NonNull Call<User> call, @NonNull Throwable th) {
        }

        public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
            if (response.isSuccessful() != null) {
                SharedHelper.putKey(OtpActivity.this.context, "logged", ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
                GlobalData.profileModel = (User) response.body();
                GlobalData.addCart = new AddCart();
                GlobalData.addCart.setProductList(((User) response.body()).getCart());
                GlobalData.addressList = new AddressList();
                GlobalData.addressList.setAddresses(((User) response.body()).getAddresses());
                OtpActivity.this.startActivity(new Intent(OtpActivity.this.context, HomeActivity.class).addFlags(67108864));
                OtpActivity.this.finish();
                return;
            }
            if (response.code() == 401) {
                SharedHelper.putKey(OtpActivity.this.context, "logged", "false");
                OtpActivity.this.startActivity(new Intent(OtpActivity.this.context, LoginActivity.class));
                OtpActivity.this.finish();
            }
            try {
                Toast.makeText(OtpActivity.this.context, new JSONObject(response.errorBody().toString()).optString("error"), 1).show();
            } catch (Response<User> response2) {
                Toast.makeText(OtpActivity.this.context, response2.getMessage(), 1).show();
            }
        }
    }

    /* renamed from: com.opalfire.foodorder.activities.OtpActivity$4 */
    class C13344 implements Callback<Otp> {
        C13344() {
        }

        public void onResponse(@NonNull Call<Otp> call, @NonNull Response<Otp> response) {
            OtpActivity.this.customDialog.dismiss();
            if (response.isSuccessful() != null) {
                call = OtpActivity.this.context;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(((Otp) response.body()).getMessage());
                Toast.makeText(call, stringBuilder.toString(), 0).show();
                GlobalData.otpValue = ((Otp) response.body()).getOtp().intValue();
                OtpActivity.this.otpValue1.setText(String.valueOf(GlobalData.otpValue));
                return;
            }
            try {
                Toast.makeText(OtpActivity.this.context, new JSONObject(response.errorBody().string()).optString("error"), 1).show();
            } catch (Response<Otp> response2) {
                Toast.makeText(OtpActivity.this.context, response2.getMessage(), 1).show();
            }
        }

        public void onFailure(@NonNull Call<Otp> call, @NonNull Throwable th) {
            OtpActivity.this.customDialog.dismiss();
        }
    }
}
