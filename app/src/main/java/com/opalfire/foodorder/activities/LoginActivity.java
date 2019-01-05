package com.opalfire.foodorder.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
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
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.foodorder.user.CountryPicker.Country;
import com.foodorder.user.CountryPicker.CountryPicker;
import com.foodorder.user.CountryPicker.CountryPickerListener;
import com.foodorder.user.HomeActivity;
import com.foodorder.user.build.api.ApiClient;
import com.foodorder.user.build.api.ApiInterface;
import com.foodorder.user.build.configure.BuildConfigure;
import com.foodorder.user.helper.ConnectionHelper;
import com.foodorder.user.helper.CustomDialog;
import com.foodorder.user.helper.GlobalData;
import com.foodorder.user.helper.SharedHelper;
import com.foodorder.user.models.AddCart;
import com.foodorder.user.models.AddressList;
import com.foodorder.user.models.LoginModel;
import com.foodorder.user.models.User;
import com.foodorder.user.utils.TextUtils;
import com.foodorder.user.utils.Utils;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity
  extends AppCompatActivity
  implements OnConnectionFailedListener
{
  private static final int ASK_MULTIPLE_PERMISSION_REQUEST_CODE = 0;
  private static final int RC_SIGN_IN = 100;
  private static final int REQ_SIGN_IN_REQUIRED = 100;
  String GRANT_TYPE = "password";
  String TAG = "Login";
  AccessTokenTracker accessTokenTracker;
  Activity activity;
  ApiInterface apiInterface = (ApiInterface)ApiClient.getRetrofit().create(ApiInterface.class);
  @BindView(2131296325)
  ImageView appLogo;
  @BindView(2131296336)
  ImageView backImg;
  CallbackManager callbackManager;
  @BindView(2131296437)
  TextView connectWith;
  Context context;
  @BindView(2131296443)
  ImageView countryImage;
  @BindView(2131296444)
  TextView countryNumber;
  String country_code = "+91";
  CustomDialog customDialog;
  String device_UDID;
  String device_token;
  @BindView(2131296499)
  TextView donnotHaveAccount;
  @BindView(2131296503)
  EditText edMobileNumber;
  @BindView(2131296504)
  EditText edPassword;
  @BindView(2131296537)
  ImageView eyeImg;
  @BindView(2131296538)
  ImageButton facebookLogin;
  String fb_email = "";
  String fb_first_name = "";
  String fb_id = "";
  String fb_last_name = "";
  Button fb_login;
  @BindView(2131296557)
  TextView forgotPassword;
  @BindView(2131296568)
  ImageButton googleLogin;
  ConnectionHelper helper;
  Boolean isInternet;
  JSONObject json;
  @BindView(2131296631)
  Button loginBtn;
  private CountryPicker mCountryPicker;
  GoogleApiClient mGoogleApiClient;
  String mobile;
  String password;
  String profile_img = "";
  Utils utils = new Utils();
  
  private void getProfile()
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("device_type", "android");
    localHashMap.put("device_id", this.device_UDID);
    localHashMap.put("device_token", this.device_token);
    this.apiInterface.getProfile(localHashMap).enqueue(new Callback()
    {
      public void onFailure(@NonNull Call<User> paramAnonymousCall, @NonNull Throwable paramAnonymousThrowable)
      {
        LoginActivity.this.customDialog.dismiss();
      }
      
      public void onResponse(@NonNull Call<User> paramAnonymousCall, @NonNull Response<User> paramAnonymousResponse)
      {
        LoginActivity.this.customDialog.dismiss();
        SharedHelper.putKey(LoginActivity.this.context, "logged", "true");
        GlobalData.profileModel = (User)paramAnonymousResponse.body();
        GlobalData.addCart = new AddCart();
        GlobalData.addCart.setProductList(((User)paramAnonymousResponse.body()).getCart());
        GlobalData.addressList = new AddressList();
        GlobalData.addressList.setAddresses(((User)paramAnonymousResponse.body()).getAddresses());
        LoginActivity.this.startActivity(new Intent(LoginActivity.this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        LoginActivity.this.finish();
      }
    });
  }
  
  private void getUserCountryInfo()
  {
    Object localObject = getResources().getConfiguration().locale;
    localObject = Country.getCountryFromSIM(this);
    if (localObject != null)
    {
      this.countryImage.setImageResource(((Country)localObject).getFlag());
      this.countryNumber.setText(((Country)localObject).getDialCode());
      this.country_code = ((Country)localObject).getDialCode();
      return;
    }
    localObject = new Country("US", "United States", "+1", 2131231146);
    this.countryImage.setImageResource(((Country)localObject).getFlag());
    this.countryNumber.setText(((Country)localObject).getDialCode());
    this.country_code = ((Country)localObject).getDialCode();
  }
  
  private void handleSignInResult(GoogleSignInResult paramGoogleSignInResult)
  {
    try
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("handleSignInResult:");
      localStringBuilder.append(paramGoogleSignInResult.isSuccess());
      Log.d("Beginscreen", localStringBuilder.toString());
      if (paramGoogleSignInResult.isSuccess())
      {
        paramGoogleSignInResult = paramGoogleSignInResult.getSignInAccount();
        GlobalData.name = paramGoogleSignInResult.getDisplayName();
        GlobalData.email = paramGoogleSignInResult.getEmail();
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("");
        localStringBuilder.append(paramGoogleSignInResult.getPhotoUrl());
        GlobalData.imageUrl = localStringBuilder.toString();
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("display_name:");
        localStringBuilder.append(paramGoogleSignInResult.getDisplayName());
        Log.d("Google", localStringBuilder.toString());
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("mail:");
        localStringBuilder.append(paramGoogleSignInResult.getEmail());
        Log.d("Google", localStringBuilder.toString());
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("photo:");
        localStringBuilder.append(paramGoogleSignInResult.getPhotoUrl());
        Log.d("Google", localStringBuilder.toString());
        new RetrieveTokenTask(null).execute(new String[] { paramGoogleSignInResult.getEmail() });
        return;
      }
      Snackbar.make(findViewById(16908290), getResources().getString(2131820784), -1).show();
      return;
    }
    catch (Exception paramGoogleSignInResult)
    {
      paramGoogleSignInResult.printStackTrace();
    }
  }
  
  private boolean isValidMobile(String paramString)
  {
    return (paramString != null) && (paramString.length() >= 6) && (paramString.length() <= 13) && (Patterns.PHONE.matcher(paramString).matches());
  }
  
  private void setListener()
  {
    this.mCountryPicker.setListener(new CountryPickerListener()
    {
      public void onSelectCountry(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, int paramAnonymousInt)
      {
        LoginActivity.this.countryNumber.setText(paramAnonymousString3);
        LoginActivity.this.country_code = paramAnonymousString3;
        LoginActivity.this.countryImage.setImageResource(paramAnonymousInt);
        LoginActivity.this.mCountryPicker.dismiss();
      }
    });
    this.countryImage.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        LoginActivity.this.mCountryPicker.show(LoginActivity.this.getSupportFragmentManager(), "COUNTRY_PICKER");
      }
    });
    this.countryNumber.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        LoginActivity.this.mCountryPicker.show(LoginActivity.this.getSupportFragmentManager(), "COUNTRY_PICKER");
      }
    });
    getUserCountryInfo();
  }
  
  private void signOut()
  {
    GoogleSignInOptions localGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
    this.mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API, localGoogleSignInOptions).build();
    this.mGoogleApiClient.connect();
    this.mGoogleApiClient.registerConnectionCallbacks(new ConnectionCallbacks()
    {
      public void onConnected(@Nullable Bundle paramAnonymousBundle)
      {
        if (LoginActivity.this.mGoogleApiClient.isConnected()) {
          Auth.GoogleSignInApi.signOut(LoginActivity.this.mGoogleApiClient).setResultCallback(new ResultCallback()
          {
            public void onResult(@NonNull Status paramAnonymous2Status)
            {
              if (paramAnonymous2Status.isSuccess()) {
                Log.d("MainAct", "Google User Logged out");
              }
            }
          });
        }
      }
      
      public void onConnectionSuspended(int paramAnonymousInt)
      {
        Log.d("MAin", "Google API Client Connection Suspended");
      }
    });
  }
  
  protected void attachBaseContext(Context paramContext)
  {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(paramContext));
  }
  
  public void fbLogin()
  {
    if (this.isInternet.booleanValue())
    {
      LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(new String[] { "email" }));
      LoginManager.getInstance().registerCallback(this.callbackManager, new FacebookCallback()
      {
        public void onCancel() {}
        
        public void onError(FacebookException paramAnonymousFacebookException) {}
        
        public void onSuccess(LoginResult paramAnonymousLoginResult)
        {
          if (AccessToken.getCurrentAccessToken() != null)
          {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("");
            localStringBuilder.append(paramAnonymousLoginResult.getAccessToken().getToken());
            Log.e("loginresult", localStringBuilder.toString());
            SharedHelper.putKey(LoginActivity.this, "access_token", paramAnonymousLoginResult.getAccessToken().getToken());
            GlobalData.access_token = paramAnonymousLoginResult.getAccessToken().getToken();
            GlobalData.loginBy = "facebook";
            paramAnonymousLoginResult = new HashMap();
            paramAnonymousLoginResult.put("login_by", GlobalData.loginBy);
            paramAnonymousLoginResult.put("accessToken", GlobalData.access_token);
            LoginActivity.this.login(paramAnonymousLoginResult);
          }
        }
      });
      return;
    }
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this.context);
    localBuilder.setMessage("Check your Internet").setCancelable(false);
    localBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.dismiss();
      }
    });
    localBuilder.setPositiveButton("Setting", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface = new Intent("android.settings.SETTINGS");
        LoginActivity.this.startActivity(paramAnonymousDialogInterface);
      }
    });
    localBuilder.show();
  }
  
  public void getDeviceToken()
  {
    try
    {
      if ((!SharedHelper.getKey(this.context, "device_token").equals("")) && (SharedHelper.getKey(this.context, "device_token") != null))
      {
        this.device_token = SharedHelper.getKey(this.context, "device_token");
        localObject = this.TAG;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("GCM Registration Token: ");
        localStringBuilder.append(this.device_token);
        Log.d((String)localObject, localStringBuilder.toString());
      }
      else
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("");
        ((StringBuilder)localObject).append(FirebaseInstanceId.getInstance().getToken());
        this.device_token = ((StringBuilder)localObject).toString();
        localObject = this.context;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("");
        localStringBuilder.append(FirebaseInstanceId.getInstance().getToken());
        SharedHelper.putKey((Context)localObject, "device_token", localStringBuilder.toString());
        localObject = this.TAG;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("Failed to complete token refresh: ");
        localStringBuilder.append(this.device_token);
        Log.d((String)localObject, localStringBuilder.toString());
      }
    }
    catch (Exception localException2)
    {
      Object localObject;
      StringBuilder localStringBuilder;
      for (;;) {}
    }
    this.device_token = "COULD NOT GET FCM TOKEN";
    Log.d(this.TAG, "Failed to complete token refresh");
    try
    {
      this.device_UDID = Settings.Secure.getString(getContentResolver(), "android_id");
      localObject = this.TAG;
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Device UDID:");
      localStringBuilder.append(this.device_UDID);
      Log.d((String)localObject, localStringBuilder.toString());
      return;
    }
    catch (Exception localException1)
    {
      this.device_UDID = "COULD NOT GET UDID";
      localException1.printStackTrace();
      Log.d(this.TAG, "Failed to complete device UDID");
      return;
    }
  }
  
  public void initValues()
  {
    GlobalData.loginBy = "manual";
    this.mobile = this.edMobileNumber.getText().toString();
    this.password = this.edPassword.getText().toString();
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.country_code);
    ((StringBuilder)localObject).append(this.mobile);
    if (!isValidMobile(((StringBuilder)localObject).toString()))
    {
      Toast.makeText(this, "Please enter valid mobile number 11", 0).show();
      return;
    }
    if (TextUtils.isEmpty(this.password))
    {
      Toast.makeText(this, "Please enter password", 0).show();
      return;
    }
    localObject = new HashMap();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.country_code);
    localStringBuilder.append(this.mobile);
    ((HashMap)localObject).put("username", localStringBuilder.toString());
    ((HashMap)localObject).put("password", this.password);
    ((HashMap)localObject).put("grant_type", this.GRANT_TYPE);
    ((HashMap)localObject).put("client_id", BuildConfigure.CLIENT_ID);
    ((HashMap)localObject).put("client_secret", BuildConfigure.CLIENT_SECRET);
    if (this.helper.isConnectingToInternet())
    {
      login((HashMap)localObject);
      return;
    }
    Utils.displayMessage(this.activity, this.context, getString(2131820829));
  }
  
  public void login(HashMap<String, String> paramHashMap)
  {
    if (!this.customDialog.isShowing())
    {
      this.customDialog = new CustomDialog(this.context);
      this.customDialog.setCancelable(false);
      this.customDialog.show();
    }
    if (GlobalData.loginBy.equals("manual")) {
      paramHashMap = this.apiInterface.postLogin(paramHashMap);
    } else {
      paramHashMap = this.apiInterface.postSocialLogin(paramHashMap);
    }
    paramHashMap.enqueue(new Callback()
    {
      public void onFailure(@NonNull Call<LoginModel> paramAnonymousCall, @NonNull Throwable paramAnonymousThrowable)
      {
        LoginActivity.this.customDialog.dismiss();
      }
      
      public void onResponse(@NonNull Call<LoginModel> paramAnonymousCall, @NonNull Response<LoginModel> paramAnonymousResponse)
      {
        if (paramAnonymousResponse.isSuccessful())
        {
          paramAnonymousCall = LoginActivity.this.context;
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append(((LoginModel)paramAnonymousResponse.body()).getTokenType());
          localStringBuilder.append(" ");
          localStringBuilder.append(((LoginModel)paramAnonymousResponse.body()).getAccessToken());
          SharedHelper.putKey(paramAnonymousCall, "access_token", localStringBuilder.toString());
          paramAnonymousCall = new StringBuilder();
          paramAnonymousCall.append(((LoginModel)paramAnonymousResponse.body()).getTokenType());
          paramAnonymousCall.append(" ");
          paramAnonymousCall.append(((LoginModel)paramAnonymousResponse.body()).getAccessToken());
          GlobalData.accessToken = paramAnonymousCall.toString();
          LoginActivity.this.getProfile();
          return;
        }
        LoginActivity.this.customDialog.dismiss();
        try
        {
          paramAnonymousCall = new JSONObject(paramAnonymousResponse.errorBody().string());
          Toast.makeText(LoginActivity.this.context, paramAnonymousCall.optString("error"), 1).show();
          LoginActivity.this.signOut();
          return;
        }
        catch (Exception paramAnonymousCall)
        {
          Toast.makeText(LoginActivity.this.context, paramAnonymousCall.getMessage(), 1).show();
        }
      }
    });
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    this.callbackManager.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramIntent != null) && (paramInt1 == 100)) {
      handleSignInResult(Auth.GoogleSignInApi.getSignInResultFromIntent(paramIntent));
    }
  }
  
  public void onBackPressed()
  {
    super.onBackPressed();
    finish();
    overridePendingTransition(2130771980, 2130771997);
  }
  
  public void onConnectionFailed(@NonNull ConnectionResult paramConnectionResult) {}
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    FacebookSdk.sdkInitialize(getApplicationContext());
    AppEventsLogger.activateApp(this);
    setContentView(2131492906);
    ButterKnife.bind(this);
    this.context = this;
    this.activity = this;
    this.customDialog = new CustomDialog(this.context);
    this.helper = new ConnectionHelper(this.context);
    this.isInternet = Boolean.valueOf(this.helper.isConnectingToInternet());
    getDeviceToken();
    signOut();
    this.callbackManager = Factory.create();
    StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
    paramBundle = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
    this.mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, paramBundle).build();
    if ((Build.VERSION.SDK_INT >= 23) && ((ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0) || (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") != 0))) {
      ActivityCompat.requestPermissions(this, new String[] { "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA" }, 0);
    }
    this.mCountryPicker = CountryPicker.newInstance("Select Country");
    paramBundle = Country.getAllCountries();
    Collections.sort(paramBundle, new Comparator()
    {
      public int compare(Country paramAnonymousCountry1, Country paramAnonymousCountry2)
      {
        return paramAnonymousCountry1.getName().compareToIgnoreCase(paramAnonymousCountry2.getName());
      }
    });
    this.mCountryPicker.setCountriesList(paramBundle);
    setListener();
    this.eyeImg.setTag(Integer.valueOf(1));
  }
  
  public void onRequestPermissionsResult(int paramInt, @NonNull String[] paramArrayOfString, @NonNull int[] paramArrayOfInt)
  {
    if (paramInt != 0) {
      return;
    }
    if (paramArrayOfInt.length > 0)
    {
      int i = 1;
      if (paramArrayOfInt[1] == 0) {
        paramInt = 1;
      } else {
        paramInt = 0;
      }
      if (paramArrayOfInt[0] != 0) {
        i = 0;
      }
      if ((paramInt != 0) && (i != 0)) {
        return;
      }
      Snackbar.make(findViewById(16908290), "Please Grant Permissions to start service", -2).setAction("ENABLE", new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          ActivityCompat.requestPermissions(LoginActivity.this.activity, new String[] { "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION" }, 0);
        }
      }).show();
    }
  }
  
  @OnClick({2131296631, 2131296557, 2131296499, 2131296336, 2131296537, 2131296538, 2131296568})
  public void onViewClicked(View paramView)
  {
    switch (paramView.getId())
    {
    default: 
      return;
    case 2131296631: 
      initValues();
      return;
    case 2131296568: 
      startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(this.mGoogleApiClient), 100);
      return;
    case 2131296557: 
      startActivity(new Intent(this, MobileNumberActivity.class).putExtra("signup", false));
      return;
    case 2131296538: 
      fbLogin();
      return;
    case 2131296537: 
      if (this.eyeImg.getTag().equals(Integer.valueOf(1)))
      {
        this.edPassword.setTransformationMethod(null);
        this.eyeImg.setImageDrawable(ContextCompat.getDrawable(this.context, 2131231204));
        this.eyeImg.setTag(Integer.valueOf(0));
        return;
      }
      this.eyeImg.setTag(Integer.valueOf(1));
      this.edPassword.setTransformationMethod(new PasswordTransformationMethod());
      this.eyeImg.setImageDrawable(ContextCompat.getDrawable(this.context, 2131231205));
      return;
    case 2131296499: 
      startActivity(new Intent(this, MobileNumberActivity.class));
      return;
    }
    onBackPressed();
  }
  
  private class RetrieveTokenTask
    extends AsyncTask<String, Void, String>
  {
    private RetrieveTokenTask() {}
    
    protected String doInBackground(String... paramVarArgs)
    {
      paramVarArgs = paramVarArgs[0];
      try
      {
        paramVarArgs = GoogleAuthUtil.getToken(LoginActivity.this.getApplicationContext(), paramVarArgs, "oauth2:profile email");
        return paramVarArgs;
      }
      catch (GoogleAuthException paramVarArgs)
      {
        Log.e(LoginActivity.this.TAG, paramVarArgs.getMessage());
      }
      catch (UserRecoverableAuthException paramVarArgs)
      {
        LoginActivity.this.startActivityForResult(paramVarArgs.getIntent(), 100);
      }
      catch (IOException paramVarArgs)
      {
        Log.e(LoginActivity.this.TAG, paramVarArgs.getMessage());
      }
      return null;
    }
    
    protected void onPostExecute(String paramString)
    {
      super.onPostExecute(paramString);
      Log.e("Token", paramString);
      GlobalData.access_token = paramString;
      GlobalData.loginBy = "google";
      paramString = new HashMap();
      paramString.put("login_by", GlobalData.loginBy);
      paramString.put("accessToken", GlobalData.access_token);
      LoginActivity.this.login(paramString);
    }
  }
}

