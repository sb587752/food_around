package com.opalfire.foodorder.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.CallbackManager.Factory;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequest.GraphJSONObjectCallback;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.opalfire.foodorder.CountryPicker.Country;
import com.opalfire.foodorder.CountryPicker.CountryPicker;
import com.opalfire.foodorder.CountryPicker.CountryPickerListener;
import com.opalfire.foodorder.R;
import com.opalfire.foodorder.build.api.ApiClient;
import com.opalfire.foodorder.build.api.ApiInterface;
import com.opalfire.foodorder.helper.ConnectionHelper;
import com.opalfire.foodorder.helper.CustomDialog;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.helper.SharedHelper;
import com.opalfire.foodorder.models.ForgotPassword;
import com.opalfire.foodorder.models.Otp;

import org.json.JSONObject;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MobileNumberActivity extends AppCompatActivity implements OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 100;
    private static final int REQ_SIGN_IN_REQUIRED = 100;
    public static int APP_REQUEST_CODE = 99;
    String TAG = "ActivitySocialLogin";
    String accessToken = "";
    AccessTokenTracker accessTokenTracker;
    @BindView(2131296315)
    TextView alreadyHaveAacountTxt;
    ApiInterface apiInterface = ((ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class));
    @BindView(2131296325)
    ImageView appLogo;
    @BindView(2131296336)
    ImageView backImg;
    CallbackManager callbackManager;
    @BindView(2131296437)
    TextView connectWith;
    Context context;
    String country_code = "+91";
    CustomDialog customDialog;
    @BindView(2131296528)
    EditText etMobileNumber;
    @BindView(2131296538)
    ImageButton facebookLogin;
    String fb_email = "";
    String fb_first_name = "";
    String fb_id = "";
    String fb_last_name = "";
    Button fb_login;
    @BindView(2131296568)
    ImageButton googleLogin;
    ConnectionHelper helper;
    Boolean isInternet;
    boolean isSignUp = true;
    JSONObject json;
    String loginBy = "";
    @BindView(2131296444)
    TextView mCountryDialCodeTextView;
    @BindView(2131296443)
    ImageView mCountryFlagImageView;
    GoogleApiClient mGoogleApiClient;
    @BindView(2131296666)
    Button nextBtn;
    String profile_img = "";
    private CountryPicker mCountryPicker;

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp((Context) this);
        setContentView((int) R.layout.activity_mobile_number);
        ButterKnife.bind((Activity) this);
        this.mCountryPicker = CountryPicker.newInstance("Select Country");
        this.context = this;
        this.customDialog = new CustomDialog(this.context);
        this.helper = new ConnectionHelper(this.context);
        this.isInternet = Boolean.valueOf(this.helper.isConnectingToInternet());
        this.callbackManager = Factory.create();
        if (VERSION.SDK_INT > 9) {
            StrictMode.setThreadPolicy(new Builder().permitAll().build());
        }
        this.mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()).build();
        bundle = getIntent().getExtras();
        if (bundle != null) {
            this.isSignUp = bundle.getBoolean("signup", true);
        }
        bundle = Country.getAllCountries();
        Collections.sort(bundle, new C07391());
        this.mCountryPicker.setCountriesList(bundle);
        setListener();
    }

    public void fbLogin() {
        if (this.isInternet.booleanValue()) {
            LoginManager.getInstance().logInWithReadPermissions((Activity) this, Arrays.asList(new String[]{"email"}));
            LoginManager.getInstance().registerCallback(this.callbackManager, new C13172());
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setMessage((CharSequence) "Check your Internet").setCancelable(false);
        builder.setNegativeButton((CharSequence) "Cancel", new C07403());
        builder.setPositiveButton((CharSequence) "Setting", new C07414());
        builder.show();
    }

    public void RequestData() {
        if (this.isInternet.booleanValue()) {
            this.customDialog.show();
            GraphRequest newMeRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new C13185());
            Bundle bundle = new Bundle();
            bundle.putString(GraphRequest.FIELDS_PARAM, "id,name,link,email,picture");
            newMeRequest.setParameters(bundle);
            newMeRequest.executeAsync();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setMessage((CharSequence) "Check your Internet").setCancelable(false);
        builder.setNegativeButton((CharSequence) "Cancel", new C07426());
        builder.setPositiveButton((CharSequence) "Setting", new C07437());
        builder.show();
    }

    private void forgotPassord(String str) {
        this.customDialog.show();
        this.apiInterface.forgotPassword(str).enqueue(new C13198());
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    public void getOtpVerification(HashMap<String, String> hashMap) {
        this.customDialog.show();
        this.apiInterface.postOtp(hashMap).enqueue(new C13209());
    }

    private void setListener() {
        this.mCountryPicker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String str, String str2, String str3, int i) {
                MobileNumberActivity.this.mCountryDialCodeTextView.setText(str3);
                MobileNumberActivity.this.country_code = str3;
                MobileNumberActivity.this.mCountryFlagImageView.setImageResource(i);
                MobileNumberActivity.this.mCountryPicker.dismiss();
            }
        });
        this.mCountryDialCodeTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MobileNumberActivity.this.mCountryPicker.show(MobileNumberActivity.this.getSupportFragmentManager(), "COUNTRY_PICKER");
            }
        });
        this.mCountryFlagImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MobileNumberActivity.this.mCountryPicker.show(MobileNumberActivity.this.getSupportFragmentManager(), "COUNTRY_PICKER");
            }
        });
        getUserCountryInfo();
    }

    private void getUserCountryInfo() {
        Locale locale = getResources().getConfiguration().locale;
        Country countryFromSIM = Country.getCountryFromSIM((Context) this);
        if (countryFromSIM != null) {
            this.mCountryFlagImageView.setImageResource(countryFromSIM.getFlag());
            this.mCountryDialCodeTextView.setText(countryFromSIM.getDialCode());
            this.country_code = countryFromSIM.getDialCode();
            return;
        }
        countryFromSIM = new Country("US", "United States", "+1", R.drawable.flag_us);
        this.mCountryFlagImageView.setImageResource(countryFromSIM.getFlag());
        this.mCountryDialCodeTextView.setText(countryFromSIM.getDialCode());
        this.country_code = countryFromSIM.getDialCode();
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        this.callbackManager.onActivityResult(i, i2, intent);
        if (intent != null && i == 100) {
            handleSignInResult(Auth.GoogleSignInApi.getSignInResultFromIntent(intent));
        }
    }

    private void handleSignInResult(GoogleSignInResult googleSignInResult) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("handleSignInResult:");
            stringBuilder.append(googleSignInResult.isSuccess());
            Log.d("Beginscreen", stringBuilder.toString());
            if (googleSignInResult.isSuccess()) {
                googleSignInResult = googleSignInResult.getSignInAccount();
                GlobalData.name = googleSignInResult.getDisplayName();
                GlobalData.email = googleSignInResult.getEmail();
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("");
                stringBuilder2.append(googleSignInResult.getPhotoUrl());
                GlobalData.imageUrl = stringBuilder2.toString();
                stringBuilder = new StringBuilder();
                stringBuilder.append("display_name:");
                stringBuilder.append(googleSignInResult.getDisplayName());
                Log.d("Google", stringBuilder.toString());
                stringBuilder = new StringBuilder();
                stringBuilder.append("mail:");
                stringBuilder.append(googleSignInResult.getEmail());
                Log.d("Google", stringBuilder.toString());
                stringBuilder = new StringBuilder();
                stringBuilder.append("photo:");
                stringBuilder.append(googleSignInResult.getPhotoUrl());
                Log.d("Google", stringBuilder.toString());
                new RetrieveTokenTask().execute(new String[]{googleSignInResult.getEmail()});
                return;
            }
            Snackbar.make(findViewById(16908290), getResources().getString(R.string.google_login_failed), -1).show();
        } catch (GoogleSignInResult googleSignInResult2) {
            googleSignInResult2.printStackTrace();
        }
    }

    @OnClick({2131296336, 2131296666, 2131296315, 2131296538, 2131296568})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.already_have_aacount_txt:
                startActivity(new Intent(this.context, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
                finish();
                return;
            case R.id.back_img:
                onBackPressed();
                return;
            case R.id.facebook_login:
                fbLogin();
                return;
            case R.id.google_login:
                startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(this.mGoogleApiClient), 100);
                return;
            case R.id.next_btn:
                view = new StringBuilder();
                view.append(this.country_code);
                view.append(this.etMobileNumber.getText().toString());
                view = view.toString();
                if (isValidMobile(view)) {
                    GlobalData.mobile = view;
                    GlobalData.loginBy = "manual";
                    if (this.isSignUp) {
                        HashMap hashMap = new HashMap();
                        hashMap.put("phone", view);
                        getOtpVerification(hashMap);
                        return;
                    }
                    forgotPassord(view);
                    return;
                }
                Toast.makeText(this, "Please enter valid mobile number", 0).show();
                return;
            default:
                return;
        }
    }

    private boolean isValidMobile(String str) {
        return (str == null || str.length() < 6 || str.length() > 13 || Patterns.PHONE.matcher(str).matches() == null) ? null : true;
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
    }

    /* renamed from: com.entriver.foodorder.activities.MobileNumberActivity$1 */
    class C07391 implements Comparator<Country> {
        C07391() {
        }

        public int compare(Country country, Country country2) {
            return country.getName().compareToIgnoreCase(country2.getName());
        }
    }

    /* renamed from: com.entriver.foodorder.activities.MobileNumberActivity$3 */
    class C07403 implements OnClickListener {
        C07403() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }

    /* renamed from: com.entriver.foodorder.activities.MobileNumberActivity$4 */
    class C07414 implements OnClickListener {
        C07414() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            MobileNumberActivity.this.startActivity(new Intent("android.settings.SETTINGS"));
        }
    }

    /* renamed from: com.entriver.foodorder.activities.MobileNumberActivity$6 */
    class C07426 implements OnClickListener {
        C07426() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }

    /* renamed from: com.entriver.foodorder.activities.MobileNumberActivity$7 */
    class C07437 implements OnClickListener {
        C07437() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            MobileNumberActivity.this.startActivity(new Intent("android.settings.SETTINGS"));
        }
    }

    private class RetrieveTokenTask extends AsyncTask<String, Void, String> {
        private RetrieveTokenTask() {
        }

        protected String doInBackground(String... strArr) {
            try {
                return GoogleAuthUtil.getToken(MobileNumberActivity.this.getApplicationContext(), strArr[0], "oauth2:profile email");
            } catch (String[] strArr2) {
                Log.e(MobileNumberActivity.this.TAG, strArr2.getMessage());
                return null;
            } catch (String[] strArr22) {
                MobileNumberActivity.this.startActivityForResult(strArr22.getIntent(), 100);
                return null;
            } catch (String[] strArr222) {
                Log.e(MobileNumberActivity.this.TAG, strArr222.getMessage());
                return null;
            }
        }

        protected void onPostExecute(String str) {
            super.onPostExecute(str);
            Log.e("Token", str);
            GlobalData.access_token = str;
            GlobalData.loginBy = "google";
            MobileNumberActivity.this.startActivity(new Intent(MobileNumberActivity.this.context, SignUpActivity.class));
            MobileNumberActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
            MobileNumberActivity.this.finish();
        }
    }

    /* renamed from: com.entriver.foodorder.activities.MobileNumberActivity$2 */
    class C13172 implements FacebookCallback<LoginResult> {
        C13172() {
        }

        public void onCancel() {
        }

        public void onError(FacebookException facebookException) {
        }

        public void onSuccess(LoginResult loginResult) {
            if (AccessToken.getCurrentAccessToken() != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(loginResult.getAccessToken().getToken());
                Log.e("loginresult", stringBuilder.toString());
                SharedHelper.putKey(MobileNumberActivity.this, "access_token", loginResult.getAccessToken().getToken());
                GlobalData.access_token = loginResult.getAccessToken().getToken();
                MobileNumberActivity.this.RequestData();
            }
        }
    }

    /* renamed from: com.entriver.foodorder.activities.MobileNumberActivity$5 */
    class C13185 implements GraphJSONObjectCallback {
        C13185() {
        }

        public void onCompleted(JSONObject jSONObject, GraphResponse graphResponse) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(graphResponse);
            Log.e("response", stringBuilder.toString());
            MobileNumberActivity.this.json = graphResponse.getJSONObject();
            graphResponse = new StringBuilder();
            graphResponse.append("");
            graphResponse.append(MobileNumberActivity.this.json);
            Log.e("FB JSON", graphResponse.toString());
            try {
                if (MobileNumberActivity.this.json != null) {
                    GlobalData.name = MobileNumberActivity.this.json.optString("name");
                    GlobalData.email = MobileNumberActivity.this.json.optString("email");
                    jSONObject = Profile.getCurrentProfile().getId();
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(jSONObject);
                    Log.e("FBUserID", stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("https://graph.facebook.com/");
                    stringBuilder.append(jSONObject);
                    stringBuilder.append("/picture?type=large");
                    GlobalData.imageUrl = new URL(stringBuilder.toString()).toString();
                    graphResponse = new StringBuilder();
                    graphResponse.append("");
                    graphResponse.append(GlobalData.name);
                    Log.e("Connected FB", graphResponse.toString());
                    graphResponse = new StringBuilder();
                    graphResponse.append("");
                    graphResponse.append(GlobalData.email);
                    Log.e("Connected FB", graphResponse.toString());
                    Log.e("FBUserPhoto FB", GlobalData.imageUrl);
                    MobileNumberActivity.this.customDialog.dismiss();
                    GlobalData.loginBy = "facebook";
                    MobileNumberActivity.this.startActivity(new Intent(MobileNumberActivity.this.context, SignUpActivity.class));
                    MobileNumberActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
                    MobileNumberActivity.this.finish();
                }
            } catch (JSONObject jSONObject2) {
                jSONObject2.printStackTrace();
            }
        }
    }

    /* renamed from: com.entriver.foodorder.activities.MobileNumberActivity$8 */
    class C13198 implements Callback<ForgotPassword> {
        C13198() {
        }

        public void onFailure(@NonNull Call<ForgotPassword> call, @NonNull Throwable th) {
        }

        public void onResponse(@NonNull Call<ForgotPassword> call, @NonNull Response<ForgotPassword> response) {
            MobileNumberActivity.this.customDialog.dismiss();
            if (response.isSuccessful() != null) {
                GlobalData.profileModel = ((ForgotPassword) response.body()).getUser();
                GlobalData.otpValue = Integer.parseInt(((ForgotPassword) response.body()).getUser().getOtp());
                MobileNumberActivity.this.startActivity(new Intent(MobileNumberActivity.this.context, OtpActivity.class).putExtra("signup", false));
                MobileNumberActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.anim_nothing);
                MobileNumberActivity.this.finish();
                return;
            }
            try {
                Toast.makeText(MobileNumberActivity.this.context, new JSONObject(response.errorBody().string()).optString("phone"), 1).show();
            } catch (Response<ForgotPassword> response2) {
                Toast.makeText(MobileNumberActivity.this.context, response2.getMessage(), 1).show();
            }
        }
    }

    /* renamed from: com.entriver.foodorder.activities.MobileNumberActivity$9 */
    class C13209 implements Callback<Otp> {
        C13209() {
        }

        public void onResponse(@NonNull Call<Otp> call, @NonNull Response<Otp> response) {
            MobileNumberActivity.this.customDialog.dismiss();
            if (response.isSuccessful() != null) {
                Toast.makeText(MobileNumberActivity.this, ((Otp) response.body()).getMessage(), 0).show();
                GlobalData.otpValue = ((Otp) response.body()).getOtp().intValue();
                MobileNumberActivity.this.startActivity(new Intent(MobileNumberActivity.this, OtpActivity.class));
                MobileNumberActivity.this.finish();
                return;
            }
            try {
                JSONObject jSONObject = new JSONObject(response.errorBody().string());
                if (jSONObject.has("phone") != null) {
                    if (jSONObject.optJSONArray("phone") != null) {
                        Toast.makeText(MobileNumberActivity.this.context, jSONObject.optJSONArray("phone").get(0).toString(), 1).show();
                    }
                } else if (jSONObject.has("email") != null) {
                    Toast.makeText(MobileNumberActivity.this.context, jSONObject.optString("email"), 1).show();
                } else {
                    Toast.makeText(MobileNumberActivity.this.context, jSONObject.optString("error"), 1).show();
                }
            } catch (Response<Otp> response2) {
                Toast.makeText(MobileNumberActivity.this.context, response2.getMessage(), 1).show();
            }
        }

        public void onFailure(@NonNull Call<Otp> call, @NonNull Throwable th) {
            MobileNumberActivity.this.customDialog.dismiss();
        }
    }
}
