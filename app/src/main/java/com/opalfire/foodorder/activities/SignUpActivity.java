package com.opalfire.foodorder.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.internal.ServerProtocol;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.opalfire.foodorder.CountryPicker.Country;
import com.opalfire.foodorder.CountryPicker.CountryPicker;
import com.opalfire.foodorder.CountryPicker.CountryPickerListener;
import com.opalfire.foodorder.HomeActivity;
import com.opalfire.foodorder.R;
import com.opalfire.foodorder.build.api.ApiClient;
import com.opalfire.foodorder.build.api.ApiInterface;
import com.opalfire.foodorder.build.configure.BuildConfigure;
import com.opalfire.foodorder.helper.ConnectionHelper;
import com.opalfire.foodorder.helper.CustomDialog;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.helper.SharedHelper;
import com.opalfire.foodorder.models.AddCart;
import com.opalfire.foodorder.models.AddressList;
import com.opalfire.foodorder.models.LoginModel;
import com.opalfire.foodorder.models.Otp;
import com.opalfire.foodorder.models.RegisterModel;
import com.opalfire.foodorder.models.User;
import com.opalfire.foodorder.utils.TextUtils;
import com.opalfire.foodorder.utils.Utils;

import org.json.JSONObject;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignUpActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION = 1450;
    String GRANT_TYPE = "password";
    String TAG = "Login";
    Activity activity;
    ApiInterface apiInterface = ((ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class));
    @BindView(2131296325)
    ImageView appLogo;
    @BindView(2131296336)
    ImageView backImg;
    @BindView(2131296433)
    EditText confirmPassword;
    @BindView(2131296434)
    ImageView confirmPasswordEyeImg;
    @BindView(2131296435)
    RelativeLayout confirmPasswordLayout;
    ConnectionHelper connectionHelper;
    Context context;
    @BindView(2131296443)
    ImageView countryImage;
    @BindView(2131296444)
    TextView countryNumber;
    String country_code = "+91";
    CustomDialog customDialog;
    String device_UDID;
    String device_token;
    String email;
    @BindView(2131296508)
    EditText emailEdit;
    @BindView(2131296528)
    EditText etMobileNumber;
    @BindView(2131296621)
    LinearLayout linearLayout;
    GoogleApiClient mGoogleApiClient;
    @BindView(2131296652)
    RelativeLayout mobileNumberLayout;
    String name;
    @BindView(2131296659)
    EditText nameEdit;
    String password;
    @BindView(2131296718)
    EditText passwordEdit;
    @BindView(2131296719)
    ImageView passwordEyeImg;
    @BindView(2131296720)
    RelativeLayout passwordLayout;
    @BindView(2131296848)
    Button signUpBtn;
    String strConfirmPassword;
    Utils utils = new Utils();
    private CountryPicker mCountryPicker;

    protected void onCreate(Bundle r6) {
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
        r5 = this;
        super.onCreate(r6);
        r6 = r5.getApplicationContext();
        com.facebook.FacebookSdk.sdkInitialize(r6);
        r6 = 2131492924; // 0x7f0c003c float:1.8609314E38 double:1.053097428E-314;
        r5.setContentView(r6);
        r6 = r5.getWindow();
        r0 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r6.setFlags(r0, r0);
        butterknife.ButterKnife.bind(r5);
        r5.context = r5;
        r5.activity = r5;
        r6 = new com.opalfire.foodorder.helper.ConnectionHelper;
        r0 = r5.context;
        r6.<init>(r0);
        r5.connectionHelper = r6;
        r6 = new com.opalfire.foodorder.helper.CustomDialog;
        r0 = r5.context;
        r6.<init>(r0);
        r5.customDialog = r6;
        r6 = "Select Country";
        r6 = com.opalfire.foodorder.CountryPicker.CountryPicker.newInstance(r6);
        r5.mCountryPicker = r6;
        r6 = r5.passwordEyeImg;
        r0 = 1;
        r1 = java.lang.Integer.valueOf(r0);
        r6.setTag(r1);
        r6 = r5.confirmPasswordEyeImg;
        r0 = java.lang.Integer.valueOf(r0);
        r6.setTag(r0);
        r6 = com.opalfire.foodorder.helper.GlobalData.loginBy;
        r0 = "manual";
        r6 = r6.equals(r0);
        r0 = 8;
        r1 = 0;
        if (r6 != 0) goto L_0x0078;
    L_0x005a:
        r6 = r5.confirmPasswordLayout;
        r6.setVisibility(r0);
        r6 = r5.passwordLayout;
        r6.setVisibility(r0);
        r6 = r5.mobileNumberLayout;
        r6.setVisibility(r1);
        r6 = r5.nameEdit;
        r0 = com.opalfire.foodorder.helper.GlobalData.name;
        r6.setText(r0);
        r6 = r5.emailEdit;
        r0 = com.opalfire.foodorder.helper.GlobalData.email;
        r6.setText(r0);
        goto L_0x0087;
    L_0x0078:
        r6 = r5.confirmPasswordLayout;
        r6.setVisibility(r1);
        r6 = r5.passwordLayout;
        r6.setVisibility(r1);
        r6 = r5.mobileNumberLayout;
        r6.setVisibility(r0);
    L_0x0087:
        r6 = com.opalfire.foodorder.CountryPicker.Country.getAllCountries();
        r0 = new com.opalfire.foodorder.activities.SignUpActivity$1;
        r0.<init>();
        java.util.Collections.sort(r6, r0);
        r0 = r5.mCountryPicker;
        r0.setCountriesList(r6);
        r5.setListener();
        r5.signOut();
        r6 = com.facebook.login.LoginManager.getInstance();
        r6.logOut();
        r6 = r5.getPackageManager();	 Catch:{ NameNotFoundException -> 0x00d6, NameNotFoundException -> 0x00d6 }
        r0 = "com.foodie.user";	 Catch:{ NameNotFoundException -> 0x00d6, NameNotFoundException -> 0x00d6 }
        r2 = 64;	 Catch:{ NameNotFoundException -> 0x00d6, NameNotFoundException -> 0x00d6 }
        r6 = r6.getPackageInfo(r0, r2);	 Catch:{ NameNotFoundException -> 0x00d6, NameNotFoundException -> 0x00d6 }
        r6 = r6.signatures;	 Catch:{ NameNotFoundException -> 0x00d6, NameNotFoundException -> 0x00d6 }
        r0 = r6.length;	 Catch:{ NameNotFoundException -> 0x00d6, NameNotFoundException -> 0x00d6 }
        r2 = 0;	 Catch:{ NameNotFoundException -> 0x00d6, NameNotFoundException -> 0x00d6 }
    L_0x00b5:
        if (r2 >= r0) goto L_0x00d6;	 Catch:{ NameNotFoundException -> 0x00d6, NameNotFoundException -> 0x00d6 }
    L_0x00b7:
        r3 = r6[r2];	 Catch:{ NameNotFoundException -> 0x00d6, NameNotFoundException -> 0x00d6 }
        r4 = "SHA";	 Catch:{ NameNotFoundException -> 0x00d6, NameNotFoundException -> 0x00d6 }
        r4 = java.security.MessageDigest.getInstance(r4);	 Catch:{ NameNotFoundException -> 0x00d6, NameNotFoundException -> 0x00d6 }
        r3 = r3.toByteArray();	 Catch:{ NameNotFoundException -> 0x00d6, NameNotFoundException -> 0x00d6 }
        r4.update(r3);	 Catch:{ NameNotFoundException -> 0x00d6, NameNotFoundException -> 0x00d6 }
        r3 = "KeyHash:";	 Catch:{ NameNotFoundException -> 0x00d6, NameNotFoundException -> 0x00d6 }
        r4 = r4.digest();	 Catch:{ NameNotFoundException -> 0x00d6, NameNotFoundException -> 0x00d6 }
        r4 = android.util.Base64.encodeToString(r4, r1);	 Catch:{ NameNotFoundException -> 0x00d6, NameNotFoundException -> 0x00d6 }
        android.util.Log.d(r3, r4);	 Catch:{ NameNotFoundException -> 0x00d6, NameNotFoundException -> 0x00d6 }
        r2 = r2 + 1;
        goto L_0x00b5;
    L_0x00d6:
        r5.getDeviceToken();
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.opalfire.foodorder.activities.SignUpActivity.onCreate(android.os.Bundle):void");
    }

    private void setListener() {
        this.mCountryPicker.setListener(new C13622());
        this.countryNumber.setOnClickListener(new C07633());
        this.countryImage.setOnClickListener(new C07644());
        getUserCountryInfo();
    }

    private void getUserCountryInfo() {
        Locale locale = getResources().getConfiguration().locale;
        Country countryFromSIM = Country.getCountryFromSIM(this.context);
        if (countryFromSIM != null) {
            this.countryImage.setImageResource(countryFromSIM.getFlag());
            this.countryNumber.setText(countryFromSIM.getDialCode());
            this.country_code = countryFromSIM.getDialCode();
            return;
        }
        countryFromSIM = new Country("US", "United States", "+1", R.drawable.flag_us);
        this.countryImage.setImageResource(countryFromSIM.getFlag());
        this.countryNumber.setText(countryFromSIM.getDialCode());
        this.country_code = countryFromSIM.getDialCode();
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
        throw new UnsupportedOperationException("Method not decompiled: com.opalfire.foodorder.activities.SignUpActivity.getDeviceToken():void");
    }

    @OnClick({2131296848, 2131296336, 2131296719, 2131296434})
    public void onViewClicked(View view) {
        view = view.getId();
        if (view == R.id.back_img) {
            onBackPressed();
        } else if (view != R.id.confirm_password_eye_img) {
            if (view != R.id.password_eye_img) {
                if (view == R.id.sign_up) {
                    initValues();
                }
            } else if (this.passwordEyeImg.getTag().equals(Integer.valueOf(1)) != null) {
                this.passwordEdit.setTransformationMethod(null);
                this.passwordEyeImg.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_eye_close));
                this.passwordEyeImg.setTag(Integer.valueOf(0));
            } else {
                this.passwordEyeImg.setTag(Integer.valueOf(1));
                this.passwordEdit.setTransformationMethod(new PasswordTransformationMethod());
                this.passwordEyeImg.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_eye_open));
            }
        } else if (this.confirmPasswordEyeImg.getTag().equals(Integer.valueOf(1)) != null) {
            this.confirmPassword.setTransformationMethod(null);
            this.confirmPasswordEyeImg.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_eye_close));
            this.confirmPasswordEyeImg.setTag(Integer.valueOf(0));
        } else {
            this.confirmPasswordEyeImg.setTag(Integer.valueOf(1));
            this.confirmPassword.setTransformationMethod(new PasswordTransformationMethod());
            this.confirmPasswordEyeImg.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_eye_open));
        }
    }

    public void signup(HashMap<String, String> hashMap) {
        this.customDialog.show();
        this.apiInterface.postRegister(hashMap).enqueue(new C13635());
    }

    private void signOut() {
        this.mGoogleApiClient = new Builder(this.context).addApi(Auth.GOOGLE_SIGN_IN_API, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()).build();
        this.mGoogleApiClient.connect();
        this.mGoogleApiClient.registerConnectionCallbacks(new C13656());
    }

    private void login(HashMap<String, String> hashMap) {
        this.apiInterface.postLogin(hashMap).enqueue(new C13667());
    }

    public void initValues() {
        this.name = this.nameEdit.getText().toString();
        this.email = this.emailEdit.getText().toString();
        this.strConfirmPassword = this.confirmPassword.getText().toString();
        if (!GlobalData.loginBy.equals("manual")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.country_code);
            stringBuilder.append(this.etMobileNumber.getText().toString());
            GlobalData.mobile = stringBuilder.toString();
        }
        this.password = this.passwordEdit.getText().toString();
        if (TextUtils.isEmpty(this.name)) {
            Toast.makeText(this, getResources().getString(R.string.please_enter_username), 0).show();
        } else if (TextUtils.isEmpty(this.email)) {
            Toast.makeText(this, getResources().getString(R.string.please_enter_your_email), 0).show();
        } else if (!TextUtils.isValidEmail(this.email)) {
            Toast.makeText(this, getResources().getString(R.string.please_enter_valid_email), 0).show();
        } else if (!isValidMobile(this.etMobileNumber.getText().toString()) && !GlobalData.loginBy.equals("manual")) {
            Toast.makeText(this, getResources().getString(R.string.please_enter_your_mobile_number), 0).show();
        } else if (TextUtils.isEmpty(this.password) && GlobalData.loginBy.equals("manual")) {
            Toast.makeText(this, getResources().getString(R.string.please_enter_password), 0).show();
        } else if (TextUtils.isEmpty(this.strConfirmPassword) && GlobalData.loginBy.equals("manual")) {
            Toast.makeText(this, getResources().getString(R.string.please_enter_your_confirm_password), 0).show();
        } else if (this.strConfirmPassword.equalsIgnoreCase(this.password) || !GlobalData.loginBy.equals("manual")) {
            HashMap hashMap = new HashMap();
            hashMap.put("name", this.name);
            hashMap.put("email", this.email);
            hashMap.put("phone", GlobalData.mobile);
            hashMap.put("password", this.password);
            hashMap.put("password_confirmation", this.strConfirmPassword);
            if (!this.connectionHelper.isConnectingToInternet()) {
                Utils.displayMessage(this.activity, this.context, getString(R.string.oops_connect_your_internet));
            } else if (GlobalData.loginBy.equals("manual")) {
                signup(hashMap);
            } else {
                hashMap = new HashMap();
                hashMap.put("phone", GlobalData.mobile);
                hashMap.put("login_by", GlobalData.loginBy);
                hashMap.put("accessToken", GlobalData.access_token);
                getOtpVerification(hashMap);
            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.password_and_confirm_password_doesnot_match), 0).show();
        }
    }

    public void getOtpVerification(HashMap<String, String> hashMap) {
        this.customDialog.show();
        this.apiInterface.postOtp(hashMap).enqueue(new C13678());
    }

    private void getProfile() {
        HashMap hashMap = new HashMap();
        hashMap.put("device_type", "android");
        hashMap.put("device_id", this.device_UDID);
        hashMap.put("device_token", this.device_token);
        this.apiInterface.getProfile(hashMap).enqueue(new C13689());
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
    }

    private boolean isValidMobile(String str) {
        if (Pattern.matches("[a-zA-Z]+", str) || str.length() < 6) {
            return false;
        }
        if (str.length() > 13) {
            return false;
        }
        return true;
    }

    /* renamed from: com.opalfire.foodorder.activities.SignUpActivity$1 */
    class C07621 implements Comparator<Country> {
        C07621() {
        }

        public int compare(Country country, Country country2) {
            return country.getName().compareToIgnoreCase(country2.getName());
        }
    }

    /* renamed from: com.opalfire.foodorder.activities.SignUpActivity$3 */
    class C07633 implements OnClickListener {
        C07633() {
        }

        public void onClick(View view) {
            SignUpActivity.this.mCountryPicker.show(SignUpActivity.this.getSupportFragmentManager(), "COUNTRY_PICKER");
        }
    }

    /* renamed from: com.opalfire.foodorder.activities.SignUpActivity$4 */
    class C07644 implements OnClickListener {
        C07644() {
        }

        public void onClick(View view) {
            SignUpActivity.this.mCountryPicker.show(SignUpActivity.this.getSupportFragmentManager(), "COUNTRY_PICKER");
        }
    }

    /* renamed from: com.opalfire.foodorder.activities.SignUpActivity$2 */
    class C13622 implements CountryPickerListener {
        C13622() {
        }

        public void onSelectCountry(String str, String str2, String str3, int i) {
            SignUpActivity.this.countryNumber.setText(str3);
            SignUpActivity.this.country_code = str3;
            SignUpActivity.this.countryImage.setImageResource(i);
            SignUpActivity.this.mCountryPicker.dismiss();
        }
    }

    /* renamed from: com.opalfire.foodorder.activities.SignUpActivity$5 */
    class C13635 implements Callback<RegisterModel> {
        C13635() {
        }

        public void onFailure(@NonNull Call<RegisterModel> call, @NonNull Throwable th) {
        }

        public void onResponse(@NonNull Call<RegisterModel> call, @NonNull Response<RegisterModel> response) {
            if (response.body() != null) {
                call = new HashMap();
                call.put("username", GlobalData.mobile);
                call.put("password", SignUpActivity.this.password);
                call.put("grant_type", SignUpActivity.this.GRANT_TYPE);
                call.put("client_id", BuildConfigure.CLIENT_ID);
                call.put("client_secret", BuildConfigure.CLIENT_SECRET);
                SignUpActivity.this.login(call);
            } else if (response.errorBody() != null) {
                SignUpActivity.this.customDialog.dismiss();
                try {
                    JSONObject jSONObject = new JSONObject(response.errorBody().string());
                    if (jSONObject.has("email") != null) {
                        Toast.makeText(SignUpActivity.this.context, jSONObject.optString("email"), 1).show();
                    } else if (jSONObject.has("password") != null) {
                        Toast.makeText(SignUpActivity.this.context, jSONObject.optString("password"), 1).show();
                    } else if (jSONObject.has("error") != null) {
                        Toast.makeText(SignUpActivity.this.context, jSONObject.optString("error"), 1).show();
                    } else {
                        Toast.makeText(SignUpActivity.this.context, "Invalid", 1).show();
                    }
                } catch (Response<RegisterModel> response2) {
                    Toast.makeText(SignUpActivity.this.context, response2.getMessage(), 1).show();
                }
            }
        }
    }

    /* renamed from: com.opalfire.foodorder.activities.SignUpActivity$6 */
    class C13656 implements ConnectionCallbacks {

        C13656() {
        }

        public void onConnected(@Nullable Bundle bundle) {
            if (SignUpActivity.this.mGoogleApiClient.isConnected() != null) {
                Auth.GoogleSignInApi.signOut(SignUpActivity.this.mGoogleApiClient).setResultCallback(new C13641());
            }
        }

        public void onConnectionSuspended(int i) {
            Log.d("MAin", "Google API Client Connection Suspended");
        }

        /* renamed from: com.opalfire.foodorder.activities.SignUpActivity$6$1 */
        class C13641 implements ResultCallback<Status> {
            C13641() {
            }

            public void onResult(@NonNull Status status) {
                if (status.isSuccess() != null) {
                    Log.d("MainAct", "Google User Logged out");
                }
            }
        }
    }

    /* renamed from: com.opalfire.foodorder.activities.SignUpActivity$7 */
    class C13667 implements Callback<LoginModel> {
        C13667() {
        }

        public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable th) {
        }

        public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
            if (response.body() != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(((LoginModel) response.body()).getTokenType());
                stringBuilder.append(" ");
                stringBuilder.append(((LoginModel) response.body()).getAccessToken());
                SharedHelper.putKey(SignUpActivity.this.context, "access_token", stringBuilder.toString());
                call = new StringBuilder();
                call.append(((LoginModel) response.body()).getTokenType());
                call.append(" ");
                call.append(((LoginModel) response.body()).getAccessToken());
                GlobalData.accessToken = call.toString();
                SignUpActivity.this.getProfile();
            }
        }
    }

    /* renamed from: com.opalfire.foodorder.activities.SignUpActivity$8 */
    class C13678 implements Callback<Otp> {
        C13678() {
        }

        public void onResponse(@NonNull Call<Otp> call, @NonNull Response<Otp> response) {
            SignUpActivity.this.customDialog.dismiss();
            if (response.isSuccessful() != null) {
                call = SignUpActivity.this.context;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(((Otp) response.body()).getMessage());
                Toast.makeText(call, stringBuilder.toString(), 0).show();
                GlobalData.otpValue = ((Otp) response.body()).getOtp().intValue();
                SignUpActivity.this.startActivity(new Intent(SignUpActivity.this.context, OtpActivity.class));
                SignUpActivity.this.finish();
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject(response.errorBody().string());
                if (jSONObject.has("phone") != null) {
                    Toast.makeText(SignUpActivity.this.context, jSONObject.optString("phone"), 1).show();
                } else if (jSONObject.has("email") != null) {
                    Toast.makeText(SignUpActivity.this.context, jSONObject.optString("email"), 1).show();
                } else {
                    Toast.makeText(SignUpActivity.this.context, jSONObject.optString("error"), 1).show();
                }
            } catch (Response<Otp> response2) {
                Toast.makeText(SignUpActivity.this.context, response2.getMessage(), 1).show();
            }
        }

        public void onFailure(@NonNull Call<Otp> call, @NonNull Throwable th) {
            SignUpActivity.this.customDialog.dismiss();
            Toast.makeText(SignUpActivity.this, "Something went wrong", 0).show();
        }
    }

    /* renamed from: com.opalfire.foodorder.activities.SignUpActivity$9 */
    class C13689 implements Callback<User> {
        C13689() {
        }

        public void onFailure(@NonNull Call<User> call, @NonNull Throwable th) {
        }

        public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
            if (response.isSuccessful() != null) {
                SharedHelper.putKey(SignUpActivity.this.context, "logged", ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
                GlobalData.profileModel = (User) response.body();
                GlobalData.addCart = new AddCart();
                GlobalData.addCart.setProductList(((User) response.body()).getCart());
                GlobalData.addressList = new AddressList();
                GlobalData.addressList.setAddresses(((User) response.body()).getAddresses());
                SignUpActivity.this.startActivity(new Intent(SignUpActivity.this.context, HomeActivity.class).addFlags(67108864));
                SignUpActivity.this.finish();
                return;
            }
            if (response.code() == 401) {
                SharedHelper.putKey(SignUpActivity.this.context, "logged", "false");
                SignUpActivity.this.startActivity(new Intent(SignUpActivity.this.context, LoginActivity.class));
                SignUpActivity.this.finish();
            }
            try {
                Toast.makeText(SignUpActivity.this.context, new JSONObject(response.errorBody().toString()).optString("error"), 1).show();
            } catch (Response<User> response2) {
                Toast.makeText(SignUpActivity.this.context, response2.getMessage(), 1).show();
            }
        }
    }
}
