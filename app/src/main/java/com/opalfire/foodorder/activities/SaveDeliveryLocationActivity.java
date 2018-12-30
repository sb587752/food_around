package com.opalfire.foodorder.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetBehavior.BottomSheetCallback;
import android.support.design.widget.CoordinatorLayout;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.internal.FacebookRequestErrorClassification;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.GoogleMap.OnCameraMoveListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CameraPosition.Builder;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.opalfire.foodorder.HomeActivity;
import com.opalfire.foodorder.R;
import com.opalfire.foodorder.build.api.ApiClient;
import com.opalfire.foodorder.build.api.ApiInterface;
import com.opalfire.foodorder.build.api.ErrorUtils;
import com.opalfire.foodorder.helper.CustomDialog;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.models.Address;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.PrintStream;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SaveDeliveryLocationActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, OnCameraMoveListener, OnCameraIdleListener, ConnectionCallbacks, OnConnectionFailedListener {
    Runnable action = new C07607();
    Address address = null;
    @BindView(2131296306)
    EditText addressEdit;
    @BindView(2131296322)
    ImageView animationLineCartAdd;
    ApiInterface apiInterface = ((ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class));
    AnimatedVectorDrawableCompat avdProgress;
    @BindView(2131296335)
    ImageView backArrow;
    @BindView(2131296360)
    CardView bottomSheet;
    @BindView(2131296386)
    TextView cancelTxt;
    Context context;
    @BindView(2131296442)
    CoordinatorLayout coordinatorLayout;
    @BindView(2131296448)
    ImageView currentLocImg;
    CustomDialog customDialog;
    @BindView(2131296502)
    ImageView dummyImageView;
    @BindView(2131296552)
    EditText flatNoEdit;
    @BindView(2131296584)
    RadioButton homeRadio;
    @BindView(2131296594)
    ImageView imgCurrentLoc;
    boolean isAddressSave = false;
    boolean isSkipVisible = false;
    @BindView(2131296611)
    EditText landmark;
    FusedLocationProviderClient mFusedLocationClient;
    @BindView(2131296703)
    EditText otherAddressHeaderEt;
    @BindView(2131296704)
    RelativeLayout otherAddressTitleLayout;
    @BindView(2131296706)
    RadioButton otherRadio;
    Retrofit retrofit;
    @BindView(2131296800)
    Button save;
    @BindView(2131296851)
    TextView skipTxt;
    Animation slide_down;
    Animation slide_up;
    @BindView(2131296934)
    RadioGroup typeRadiogroup;
    @BindView(2131296974)
    RadioButton workRadio;
    private String TAG = "SaveDelivery";
    private String addressHeader = "";
    private BottomSheetBehavior behavior;
    private Double crtLat;
    private Double crtLng;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private GoogleMap mMap;
    private Double srcLat;
    private Double srcLng;
    private int value = 0;

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    public void onConnectionSuspended(int i) {
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_save_delivery_location);
        getWindow().setFlags(1024, 1024);
        ButterKnife.bind((Activity) this);
        this.context = this;
        this.address = new Address();
        this.customDialog = new CustomDialog(this.context);
        this.address.setType(FacebookRequestErrorClassification.KEY_OTHER);
        initializeAvd();
        this.mFusedLocationClient = LocationServices.getFusedLocationProviderClient((Activity) this);
        this.slide_down = AnimationUtils.loadAnimation(this.context, R.anim.slide_down);
        this.slide_up = AnimationUtils.loadAnimation(this.context, R.anim.slide_up);
        this.isAddressSave = getIntent().getBooleanExtra("get_address", false);
        this.isSkipVisible = getIntent().getBooleanExtra("skip_visible", false);
        if (this.isSkipVisible != null) {
            this.skipTxt.setVisibility(View.VISIBLE);
        } else {
            this.skipTxt.setVisibility(View.GONE);
        }
        if (VERSION.SDK_INT < 23) {
            buildGoogleApiClient();
            this.mFusedLocationClient.getLastLocation().addOnSuccessListener((Activity) this, new C13492());
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == null) {
            buildGoogleApiClient();
            this.mFusedLocationClient.getLastLocation().addOnSuccessListener((Activity) this, new C13481());
        }
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        this.behavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        this.behavior.setState(3);
        this.dummyImageView.setVisibility(View.VISIBLE);
        this.behavior.setBottomSheetCallback(new C13503());
        this.otherRadio.setOnClickListener(new C07584());
        this.typeRadiogroup.setOnCheckedChangeListener(new C07595());
        bundle = getIntent().getExtras();
        if (bundle != null) {
            String string = bundle.getString("place_id");
            bundle = bundle.getString("edit");
            if (string != null) {
                Places.GeoDataApi.getPlaceById(this.mGoogleApiClient, string).setResultCallback(new C13516());
            }
            if (bundle != null && bundle.equals("yes") != null && GlobalData.selectedAddress != null) {
                this.address = GlobalData.selectedAddress;
                this.addressEdit.setText(this.address.getMapAddress());
                this.flatNoEdit.setText(this.address.getBuilding());
                this.landmark.setText(this.address.getLandmark());
                bundle = this.address.getType();
                Object obj = -1;
                int hashCode = bundle.hashCode();
                if (hashCode != 3208415) {
                    if (hashCode == 3655441) {
                        if (bundle.equals("work") != null) {
                            obj = 1;
                        }
                    }
                } else if (bundle.equals("home") != null) {
                    obj = null;
                }
                switch (obj) {
                    case null:
                        this.homeRadio.setChecked(true);
                        return;
                    case 1:
                        this.workRadio.setChecked(true);
                        return;
                    default:
                        this.otherAddressHeaderEt.setText(this.address.getType());
                        this.otherRadio.setChecked(true);
                        return;
                }
            }
        }
    }

    private void initializeAvd() {
        this.avdProgress = AnimatedVectorDrawableCompat.create(getApplicationContext(), R.drawable.avd_line);
        this.animationLineCartAdd.setBackground(this.avdProgress);
        repeatAnimation();
    }

    private void repeatAnimation() {
        this.animationLineCartAdd.setVisibility(View.VISIBLE);
        this.avdProgress.start();
        this.animationLineCartAdd.postDelayed(this.action, 1500);
    }

    public void onMapReady(com.google.android.gms.maps.GoogleMap r3) {
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
        r2 = this;
        r0 = 2131755013; // 0x7f100005 float:1.9140893E38 double:1.0532269173E-314;
        r0 = com.google.android.gms.maps.model.MapStyleOptions.loadRawResourceStyle(r2, r0);	 Catch:{ NotFoundException -> 0x001d }
        r0 = r3.setMapStyle(r0);	 Catch:{ NotFoundException -> 0x001d }
        if (r0 != 0) goto L_0x0015;	 Catch:{ NotFoundException -> 0x001d }
    L_0x000d:
        r0 = "Map:Style";	 Catch:{ NotFoundException -> 0x001d }
        r1 = "Style parsing failed.";	 Catch:{ NotFoundException -> 0x001d }
        android.util.Log.i(r0, r1);	 Catch:{ NotFoundException -> 0x001d }
        goto L_0x0024;	 Catch:{ NotFoundException -> 0x001d }
    L_0x0015:
        r0 = "Map:Style";	 Catch:{ NotFoundException -> 0x001d }
        r1 = "Style Applied.";	 Catch:{ NotFoundException -> 0x001d }
        android.util.Log.i(r0, r1);	 Catch:{ NotFoundException -> 0x001d }
        goto L_0x0024;
    L_0x001d:
        r0 = "Map:Style";
        r1 = "Can't find style. Error: ";
        android.util.Log.i(r0, r1);
    L_0x0024:
        r2.mMap = r3;
        r3 = r2.mMap;
        if (r3 == 0) goto L_0x0056;
    L_0x002a:
        r3 = r2.mMap;
        r3 = r3.getUiSettings();
        r0 = 0;
        r3.setCompassEnabled(r0);
        r3 = r2.mMap;
        r1 = 1;
        r3.setBuildingsEnabled(r1);
        r3 = r2.mMap;
        r3.setOnCameraMoveListener(r2);
        r3 = r2.mMap;
        r3.setOnCameraIdleListener(r2);
        r3 = r2.mMap;
        r3 = r3.getUiSettings();
        r3.setRotateGesturesEnabled(r0);
        r3 = r2.mMap;
        r3 = r3.getUiSettings();
        r3.setTiltGesturesEnabled(r0);
    L_0x0056:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.entriver.orderaround.activities.SaveDeliveryLocationActivity.onMapReady(com.google.android.gms.maps.GoogleMap):void");
    }

    protected synchronized void buildGoogleApiClient() {
        this.mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Places.GEO_DATA_API).addApi(LocationServices.API).build();
        this.mGoogleApiClient.connect();
    }

    public void onLocationChanged(Location location) {
        System.out.println("onLocationChanged ");
        if (this.value == 0) {
            this.value = 1;
            if (this.address.getId() == null) {
                this.mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new Builder().target(new LatLng(location.getLatitude(), location.getLongitude())).zoom(16.0f).build()));
            } else {
                this.mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new Builder().target(new LatLng(this.address.getLatitude().doubleValue(), this.address.getLongitude().doubleValue())).zoom(16.0f).build()));
            }
            getAddress(location.getLatitude(), location.getLongitude());
        }
        this.crtLat = Double.valueOf(location.getLatitude());
        this.crtLng = Double.valueOf(location.getLongitude());
    }

    private void getAddress(double d, double d2) {
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("GetAddress ");
        stringBuilder.append(d);
        stringBuilder.append(" | ");
        stringBuilder.append(d2);
        printStream.println(stringBuilder.toString());
        try {
            List fromLocation = new Geocoder(this, Locale.getDefault()).getFromLocation(d, d2, 1);
            if (fromLocation == null || fromLocation.size() <= 0) {
                getAddress(this.context, d, d2);
                return;
            }
            android.location.Address address = (android.location.Address) fromLocation.get(View.VISIBLE);
            StringBuilder stringBuilder2 = new StringBuilder();
            if (address.getMaxAddressLineIndex() > 0) {
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    stringBuilder2.append(address.getAddressLine(i));
                    stringBuilder2.append("");
                }
            } else {
                stringBuilder2.append(address.getAddressLine(0));
                stringBuilder2.append("");
            }
            this.addressEdit.setText(stringBuilder2.toString());
            android.location.Address address2 = (android.location.Address) fromLocation.get(View.VISIBLE);
            this.address.setCity(address2.getLocality());
            this.address.setState(address2.getAdminArea());
            this.address.setCountry(address2.getCountryName());
            this.address.setLatitude(Double.valueOf(address2.getLatitude()));
            this.address.setLongitude(Double.valueOf(address2.getLongitude()));
            this.address.setPincode(address2.getPostalCode());
            this.addressHeader = address2.getFeatureName();
        } catch (Exception e) {
            e.printStackTrace();
            getAddress(this.context, d, d2);
        }
    }

    public void getAddress(Context context, double d, double d2) {
        this.retrofit = new Retrofit.Builder().baseUrl("https://maps.googleapis.com/maps/api/geocode/").addConverterFactory(GsonConverterFactory.create()).build();
        this.apiInterface = (ApiInterface) this.retrofit.create(ApiInterface.class);
        ApiInterface apiInterface = this.apiInterface;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(d);
        stringBuilder.append(",");
        stringBuilder.append(d2);
        final double d3 = d;
        final double d4 = d2;
        final Context context2 = context;
        apiInterface.getResponse(stringBuilder.toString(), context.getResources().getString(R.string.google_api_key)).enqueue(new Callback<ResponseBody>() {
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("SUCESS");
                stringBuilder.append(response.body());
                Log.e("sUCESS", stringBuilder.toString());
                if (response.body() != null) {
                    try {
                        call = new String(((ResponseBody) response.body()).bytes());
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("bodyString");
                        stringBuilder.append(call);
                        Log.e("sUCESS", stringBuilder.toString());
                        call = new JSONObject(call).optJSONArray("results");
                        if (call.length() > null) {
                            JSONArray optJSONArray = call.optJSONObject(0).optJSONArray("address_components");
                            optJSONArray.optJSONObject(0).optString("long_name");
                            SaveDeliveryLocationActivity.this.address.setCity(optJSONArray.optJSONObject(2).optString("long_name"));
                            SaveDeliveryLocationActivity.this.address.setState(optJSONArray.optJSONObject(3).optString("long_name"));
                            if (optJSONArray.optJSONObject(4).optString("long_name") != null) {
                                SaveDeliveryLocationActivity.this.address.setCountry(optJSONArray.optJSONObject(4).optString("long_name"));
                            }
                            SaveDeliveryLocationActivity.this.address.setLatitude(Double.valueOf(d3));
                            SaveDeliveryLocationActivity.this.address.setLongitude(Double.valueOf(d4));
                            SaveDeliveryLocationActivity.this.address.setPincode(optJSONArray.optJSONObject(5).optString("long_name"));
                            SaveDeliveryLocationActivity.this.addressHeader = optJSONArray.optJSONObject(0).optString("long_name");
                            call = call.optJSONObject(0).optString("formatted_address");
                            SaveDeliveryLocationActivity.this.addressEdit.setText(call);
                            SaveDeliveryLocationActivity.this.addressHeader = call;
                            response = new StringBuilder();
                            response.append("");
                            response.append(GlobalData.addressHeader);
                            Log.v("Formatted Address", response.toString());
                        } else {
                            call = SaveDeliveryLocationActivity.this;
                            response = new StringBuilder();
                            response.append("");
                            response.append(d3);
                            response.append("");
                            response.append(d4);
                            call.addressHeader = response.toString();
                        }
                    } catch (Call<ResponseBody> call2) {
                        call2.printStackTrace();
                    } catch (Call<ResponseBody> call22) {
                        call22.printStackTrace();
                    }
                } else {
                    call22 = SaveDeliveryLocationActivity.this;
                    response = new StringBuilder();
                    response.append("");
                    response.append(d3);
                    response.append("");
                    response.append(d4);
                    call22.addressHeader = response.toString();
                }
                call22 = new Intent("location");
                call22.putExtra("message", "This is my message!");
                LocalBroadcastManager.getInstance(context2).sendBroadcast(call22);
            }

            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable th) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onFailure");
                stringBuilder.append(call.request().url());
                Log.e("onFailure", stringBuilder.toString());
                call = SaveDeliveryLocationActivity.this;
                th = new StringBuilder();
                th.append("");
                th.append(d3);
                th.append("");
                th.append(d4);
                call.addressHeader = th.toString();
            }
        });
    }

    public void onCameraIdle() {
        try {
            CameraPosition cameraPosition = this.mMap.getCameraPosition();
            this.srcLat = Double.valueOf(cameraPosition.target.latitude);
            this.srcLng = Double.valueOf(cameraPosition.target.longitude);
            initializeAvd();
            getAddress(this.srcLat.doubleValue(), this.srcLng.doubleValue());
            this.skipTxt.setAlpha(1.0f);
            this.skipTxt.setClickable(true);
            this.skipTxt.setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCameraMove() {
        this.behavior.setState(4);
        this.dummyImageView.setVisibility(View.GONE);
        this.addressEdit.setText(getResources().getString(R.string.getting_address));
        this.animationLineCartAdd.setVisibility(View.VISIBLE);
        this.skipTxt.setAlpha(0.5f);
        this.skipTxt.setClickable(false);
        this.skipTxt.setEnabled(false);
    }

    public void onConnected(@Nullable Bundle bundle) {
        this.mLocationRequest = new LocationRequest();
        this.mLocationRequest.setInterval(1000);
        this.mLocationRequest.setFastestInterval(1000);
        this.mLocationRequest.setPriority(102);
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(this.mGoogleApiClient, this.mLocationRequest, (LocationListener) this);
        }
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    private void saveAddress() {
        if (this.address != null && this.address.getMapAddress() != null && validate()) {
            this.customDialog.show();
            this.apiInterface = (ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class);
            this.apiInterface.saveAddress(this.address).enqueue(new C13539());
        }
    }

    private void updateAddress() {
        if (this.address.getType().equalsIgnoreCase(FacebookRequestErrorClassification.KEY_OTHER)) {
            this.address.setType(this.otherAddressHeaderEt.getText().toString());
        }
        if (this.address != null && this.address.getId() != null && validate()) {
            this.customDialog.show();
            this.apiInterface = (ApiInterface) ApiClient.getRetrofit().create(ApiInterface.class);
            this.apiInterface.updateAddress(this.address.getId().intValue(), this.address).enqueue(new Callback<Address>() {
                public void onResponse(@NonNull Call<Address> call, @NonNull Response<Address> response) {
                    SaveDeliveryLocationActivity.this.customDialog.dismiss();
                    if (response.isSuccessful() != null) {
                        GlobalData.selectedAddress = (Address) response.body();
                        SaveDeliveryLocationActivity.this.finish();
                        return;
                    }
                    Toast.makeText(SaveDeliveryLocationActivity.this, (CharSequence) ErrorUtils.parseError(response).getType().get(0), 0).show();
                }

                public void onFailure(@NonNull Call<Address> call, @NonNull Throwable th) {
                    Log.e(SaveDeliveryLocationActivity.this.TAG, th.toString());
                    SaveDeliveryLocationActivity.this.customDialog.dismiss();
                    Toast.makeText(SaveDeliveryLocationActivity.this, "Something went wrong", 1).show();
                }
            });
        }
    }

    private boolean validate() {
        if (this.address.getMapAddress().isEmpty() && this.address.getMapAddress().equals(getResources().getString(R.string.getting_address))) {
            Toast.makeText(this, "Please enter address", 0).show();
            return false;
        } else if (this.address.getBuilding().isEmpty()) {
            Toast.makeText(this, "Please enter Flat No", 0).show();
            return false;
        } else if (this.address.getLandmark().isEmpty()) {
            Toast.makeText(this, "Please enter landmark", 0).show();
            return false;
        } else {
            if (this.address.getLatitude() != null) {
                if (this.address.getLongitude() != null) {
                    return true;
                }
            }
            Toast.makeText(this, "Lat & long cannot be left blank", 0).show();
            return false;
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (this.behavior.getState() == 3) {
            this.behavior.setState(4);
        }
        finish();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
    }

    @OnClick({2131296335, 2131296800, 2131296594, 2131296386, 2131296851})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backArrow:
                onBackPressed();
                return;
            case R.id.cancel_txt:
                this.otherAddressHeaderEt.setVisibility(View.VISIBLE);
                view = AnimationUtils.loadAnimation(this.context, R.anim.slide_out_right);
                view.setDuration(500);
                this.otherAddressHeaderEt.startAnimation(view);
                view = AnimationUtils.loadAnimation(this.context, R.anim.slide_in_left);
                view.setDuration(500);
                this.typeRadiogroup.startAnimation(view);
                this.typeRadiogroup.setVisibility(View.VISIBLE);
                this.otherRadio.setChecked(true);
                return;
            case R.id.imgCurrentLoc:
                if (this.crtLat != null && this.crtLng != null) {
                    this.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new Builder().target(new LatLng(this.crtLat.doubleValue(), this.crtLng.doubleValue())).zoom(16.0f).build()));
                    return;
                }
                return;
            case R.id.save:
                this.address.setMapAddress(this.addressEdit.getText().toString());
                this.address.setBuilding(this.flatNoEdit.getText().toString());
                this.address.setLandmark(this.landmark.getText().toString());
                if (!(this.address.getType().equalsIgnoreCase(FacebookRequestErrorClassification.KEY_OTHER) == null && this.address.getType().equalsIgnoreCase("") == null)) {
                    this.address.setType(this.otherAddressHeaderEt.getText().toString());
                }
                if (this.address.getBuilding().equalsIgnoreCase("") != null) {
                    Toast.makeText(this.context, "Please enter House/ flat no ", 0).show();
                    return;
                } else if (this.address.getLandmark().equalsIgnoreCase("") != null) {
                    Toast.makeText(this.context, "Please enter landmark ", 0).show();
                    return;
                } else {
                    if (this.address.getType().equalsIgnoreCase("") != null) {
                        this.address.setType(FacebookRequestErrorClassification.KEY_OTHER);
                    }
                    if (this.address.getId() != null) {
                        updateAddress();
                        return;
                    } else {
                        saveAddress();
                        return;
                    }
                }
            case R.id.skip_txt:
                this.address.setMapAddress(this.addressEdit.getText().toString());
                this.address.setType(this.addressHeader);
                GlobalData.selectedAddress = this.address;
                startActivity(new Intent(this.context, HomeActivity.class).addFlags(67108864));
                finish();
                return;
            default:
                return;
        }
    }

    /* renamed from: com.entriver.orderaround.activities.SaveDeliveryLocationActivity$4 */
    class C07584 implements OnClickListener {
        C07584() {
        }

        public void onClick(View view) {
            SaveDeliveryLocationActivity.this.currentLocImg.setBackgroundResource(R.drawable.ic_other_marker);
            SaveDeliveryLocationActivity.this.otherAddressTitleLayout.setVisibility(View.VISIBLE);
            view = AnimationUtils.loadAnimation(SaveDeliveryLocationActivity.this.context, R.anim.slide_in_right);
            view.setDuration(500);
            Animation loadAnimation = AnimationUtils.loadAnimation(SaveDeliveryLocationActivity.this.context, R.anim.slide_out_left);
            loadAnimation.setDuration(500);
            SaveDeliveryLocationActivity.this.typeRadiogroup.startAnimation(loadAnimation);
            SaveDeliveryLocationActivity.this.otherAddressTitleLayout.startAnimation(view);
            SaveDeliveryLocationActivity.this.typeRadiogroup.setVisibility(View.GONE);
        }
    }

    /* renamed from: com.entriver.orderaround.activities.SaveDeliveryLocationActivity$5 */
    class C07595 implements OnCheckedChangeListener {
        C07595() {
        }

        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            RadioButton radioButton = (RadioButton) radioGroup.findViewById(i);
            if (radioButton.getText().toString().toLowerCase().equals("home") != 0) {
                SaveDeliveryLocationActivity.this.currentLocImg.setBackgroundResource(R.drawable.ic_hoem_marker);
            } else if (radioButton.getText().toString().toLowerCase().equals("work") != 0) {
                SaveDeliveryLocationActivity.this.currentLocImg.setBackgroundResource(R.drawable.ic_work_marker);
            } else if (radioButton.getText().toString().equalsIgnoreCase(SaveDeliveryLocationActivity.this.getResources().getString(R.string.other)) != 0) {
                SaveDeliveryLocationActivity.this.currentLocImg.setBackgroundResource(R.drawable.ic_other_marker);
                SaveDeliveryLocationActivity.this.otherAddressTitleLayout.setVisibility(View.VISIBLE);
                i = AnimationUtils.loadAnimation(SaveDeliveryLocationActivity.this.context, R.anim.slide_in_right);
                i.setDuration(500);
                Animation loadAnimation = AnimationUtils.loadAnimation(SaveDeliveryLocationActivity.this.context, R.anim.slide_out_left);
                loadAnimation.setDuration(500);
                SaveDeliveryLocationActivity.this.typeRadiogroup.startAnimation(loadAnimation);
                SaveDeliveryLocationActivity.this.otherAddressTitleLayout.startAnimation(i);
                SaveDeliveryLocationActivity.this.typeRadiogroup.setVisibility(View.GONE);
            }
            i = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("typeRadiogroup ");
            stringBuilder.append(radioButton.getText().toString().toLowerCase());
            i.println(stringBuilder.toString());
            SaveDeliveryLocationActivity.this.address.setType(radioButton.getText().toString().toLowerCase());
        }
    }

    /* renamed from: com.entriver.orderaround.activities.SaveDeliveryLocationActivity$7 */
    class C07607 implements Runnable {
        C07607() {
        }

        public void run() {
            SaveDeliveryLocationActivity.this.avdProgress.stop();
            if (SaveDeliveryLocationActivity.this.animationLineCartAdd != null) {
                SaveDeliveryLocationActivity.this.animationLineCartAdd.setVisibility(4);
            }
        }
    }

    /* renamed from: com.entriver.orderaround.activities.SaveDeliveryLocationActivity$1 */
    class C13481 implements OnSuccessListener<Location> {
        C13481() {
        }

        public void onSuccess(Location location) {
            if (location != null) {
                SaveDeliveryLocationActivity.this.getAddress(location.getLatitude(), location.getLongitude());
            }
        }
    }

    /* renamed from: com.entriver.orderaround.activities.SaveDeliveryLocationActivity$2 */
    class C13492 implements OnSuccessListener<Location> {
        C13492() {
        }

        public void onSuccess(Location location) {
            if (location != null) {
                SaveDeliveryLocationActivity.this.getAddress(location.getLatitude(), location.getLongitude());
            }
        }
    }

    /* renamed from: com.entriver.orderaround.activities.SaveDeliveryLocationActivity$3 */
    class C13503 extends BottomSheetCallback {
        C13503() {
        }

        public void onStateChanged(@NonNull View view, int i) {
            if (4 == i) {
                SaveDeliveryLocationActivity.this.dummyImageView.startAnimation(SaveDeliveryLocationActivity.this.slide_down);
                SaveDeliveryLocationActivity.this.dummyImageView.setVisibility(View.GONE);
            } else if (3 == i) {
                SaveDeliveryLocationActivity.this.dummyImageView.setVisibility(View.VISIBLE);
                SaveDeliveryLocationActivity.this.dummyImageView.startAnimation(SaveDeliveryLocationActivity.this.slide_up);
            }
        }

        public void onSlide(@NonNull View view, float f) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(f);
            Log.e("Slide :", stringBuilder.toString());
            if (((double) f) < 0.9d) {
                SaveDeliveryLocationActivity.this.dummyImageView.setVisibility(1.1E-44f);
                SaveDeliveryLocationActivity.this.dummyImageView.startAnimation(SaveDeliveryLocationActivity.this.slide_down);
            }
        }
    }

    /* renamed from: com.entriver.orderaround.activities.SaveDeliveryLocationActivity$6 */
    class C13516 implements ResultCallback<PlaceBuffer> {
        C13516() {
        }

        public void onResult(@NonNull PlaceBuffer placeBuffer) {
            if (!placeBuffer.getStatus().isSuccess() || placeBuffer.getCount() <= 0) {
                System.out.println("Place not found");
            } else {
                Place place = placeBuffer.get(View.VISIBLE);
                SaveDeliveryLocationActivity.this.addressEdit.setText(place.getAddress());
                LatLng latLng = place.getLatLng();
                SaveDeliveryLocationActivity.this.value = 1;
                SaveDeliveryLocationActivity.this.mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new Builder().target(latLng).zoom(16.0f).build()));
            }
            placeBuffer.release();
        }
    }

    /* renamed from: com.entriver.orderaround.activities.SaveDeliveryLocationActivity$9 */
    class C13539 implements Callback<Address> {
        C13539() {
        }

        public void onResponse(@NonNull Call<Address> call, @NonNull Response<Address> response) {
            SaveDeliveryLocationActivity.this.customDialog.dismiss();
            if (response.isSuccessful() == null) {
                Toast.makeText(SaveDeliveryLocationActivity.this, (CharSequence) ErrorUtils.parseError(response).getType().get(0), 0).show();
            } else if (SaveDeliveryLocationActivity.this.isAddressSave != null) {
                call = new Intent();
                GlobalData.selectedAddress = (Address) response.body();
                GlobalData.addressList.getAddresses().add(response.body());
                SaveDeliveryLocationActivity.this.setResult(-1, call);
                SaveDeliveryLocationActivity.this.finish();
            } else {
                GlobalData.selectedAddress = (Address) response.body();
                GlobalData.addressList.getAddresses().add(response.body());
                SaveDeliveryLocationActivity.this.finish();
            }
        }

        public void onFailure(@NonNull Call<Address> call, @NonNull Throwable th) {
            Log.e(SaveDeliveryLocationActivity.this.TAG, th.toString());
            SaveDeliveryLocationActivity.this.customDialog.dismiss();
            Toast.makeText(SaveDeliveryLocationActivity.this, "Something went wrong", 1).show();
        }
    }
}
