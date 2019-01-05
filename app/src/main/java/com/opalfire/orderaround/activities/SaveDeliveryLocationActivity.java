package com.opalfire.orderaround.activities;

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
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.opalfire.orderaround.HomeActivity;
import com.opalfire.orderaround.R;
import com.opalfire.orderaround.build.api.ApiClient;
import com.opalfire.orderaround.build.api.ApiInterface;
import com.opalfire.orderaround.build.api.ErrorUtils;
import com.opalfire.orderaround.helper.CustomDialog;
import com.opalfire.orderaround.helper.GlobalData;
import com.opalfire.orderaround.models.Address;

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
    ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_save_delivery_location);
        getWindow().setFlags(1024, 1024);
        ButterKnife.bind(this);
        context = this;
        address = new Address();
        customDialog = new CustomDialog(context);
        address.setType(FacebookRequestErrorClassification.KEY_OTHER);
        initializeAvd();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        slide_down = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        slide_up = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        isAddressSave = getIntent().getBooleanExtra("get_address", false);
        isSkipVisible = getIntent().getBooleanExtra("skip_visible", false);
        if (isSkipVisible) {
            skipTxt.setVisibility(View.VISIBLE);
        } else {
            skipTxt.setVisibility(View.GONE);
        }
        if (VERSION.SDK_INT < 23) {
            buildGoogleApiClient();
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new C13492());
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == null) {
            buildGoogleApiClient();
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new C13481());
        }
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        behavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        behavior.setState(3);
        dummyImageView.setVisibility(View.VISIBLE);
        behavior.setBottomSheetCallback(new C13503());
        otherRadio.setOnClickListener(new C07584());
        typeRadiogroup.setOnCheckedChangeListener(new C07595());
        bundle = getIntent().getExtras();
        if (bundle != null) {
            String string = bundle.getString("place_id");
            bundle = bundle.getString("edit");
            if (string != null) {
                Places.GeoDataApi.getPlaceById(mGoogleApiClient, string).setResultCallback(new C13516());
            }
            if (bundle != null && bundle.equals("yes") && GlobalData.selectedAddress != null) {
                address = GlobalData.selectedAddress;
                addressEdit.setText(address.getMapAddress());
                flatNoEdit.setText(address.getBuilding());
                landmark.setText(address.getLandmark());
                bundle = address.getType();
                Object obj = -1;
                int hashCode = bundle.hashCode();
                if (hashCode != 3208415) {
                    if (hashCode == 3655441) {
                        if (bundle.equals("work")) {
                            obj = 1;
                        }
                    }
                } else if (bundle.equals("home")) {
                    obj = null;
                }
                switch (obj) {
                    case null:
                        homeRadio.setChecked(true);
                        return;
                    case 1:
                        workRadio.setChecked(true);
                        return;
                    default:
                        otherAddressHeaderEt.setText(address.getType());
                        otherRadio.setChecked(true);
                        return;
                }
            }
        }
    }

    private void initializeAvd() {
        avdProgress = AnimatedVectorDrawableCompat.create(getApplicationContext(), R.drawable.avd_line);
        animationLineCartAdd.setBackground(avdProgress);
        repeatAnimation();
    }

    private void repeatAnimation() {
        animationLineCartAdd.setVisibility(View.VISIBLE);
        avdProgress.start();
        animationLineCartAdd.postDelayed(action, 1500);
    }

    public void onMapReady(GoogleMap googleMap) {
        try {
            if (!googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json))) {
                Log.i("Map:Style", "Style parsing failed.");
            } else {
                Log.i("Map:Style", "Style Applied.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("Map:Style", "Can't find style. Error: ");
        this.mMap = googleMap;
        if (this.mMap != null) {
            this.mMap.getUiSettings().setCompassEnabled(false);
            this.mMap.setBuildingsEnabled(true);
            this.mMap.setOnCameraMoveListener(this);
            this.mMap.setOnCameraIdleListener(this);
            this.mMap.getUiSettings().setRotateGesturesEnabled(false);
            this.mMap.getUiSettings().setTiltGesturesEnabled(false);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Places.GEO_DATA_API).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    public void onLocationChanged(Location location) {
        System.out.println("onLocationChanged ");
        if (value == 0) {
            value = 1;
            if (address.getId() == null) {
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new Builder().target(new LatLng(location.getLatitude(), location.getLongitude())).zoom(16.0f).build()));
            } else {
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new Builder().target(new LatLng(address.getLatitude().doubleValue(), address.getLongitude().doubleValue())).zoom(16.0f).build()));
            }
            getAddress(location.getLatitude(), location.getLongitude());
        }
        crtLat = Double.valueOf(location.getLatitude());
        crtLng = Double.valueOf(location.getLongitude());
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
                getAddress(context, d, d2);
                return;
            }
            android.location.Address address = (android.location.Address) fromLocation.get(View.VISIBLE);
            StringBuilder stringBuilder2 = new StringBuilder();
            if (address.getMaxAddressLineIndex() > 0) {
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    stringBuilder2.append(address.getAddressLine(i));
                }
            } else {
                stringBuilder2.append(address.getAddressLine(0));
            }
            addressEdit.setText(stringBuilder2.toString());
            android.location.Address address2 = (android.location.Address) fromLocation.get(View.VISIBLE);
            address.setCity(address2.getLocality());
            address.setState(address2.getAdminArea());
            address.setCountry(address2.getCountryName());
            address.setLatitude(Double.valueOf(address2.getLatitude()));
            address.setLongitude(Double.valueOf(address2.getLongitude()));
            address.setPincode(address2.getPostalCode());
            addressHeader = address2.getFeatureName();
        } catch (Exception e) {
            e.printStackTrace();
            getAddress(context, d, d2);
        }
    }

    public void getAddress(Context context, double d, double d2) {
        retrofit = new Retrofit.Builder().baseUrl("https://maps.googleapis.com/maps/api/geocode/").addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(ApiInterface.class);
        ApiInterface apiInterface = apiInterface;
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
                        call = new String(response.body().bytes());
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("bodyString");
                        stringBuilder.append(call);
                        Log.e("sUCESS", stringBuilder.toString());
                        call = new JSONObject(call).optJSONArray("results");
                        if (call.length() > null) {
                            JSONArray optJSONArray = call.optJSONObject(0).optJSONArray("address_components");
                            optJSONArray.optJSONObject(0).optString("long_name");
                            address.setCity(optJSONArray.optJSONObject(2).optString("long_name"));
                            address.setState(optJSONArray.optJSONObject(3).optString("long_name"));
                            if (optJSONArray.optJSONObject(4).optString("long_name") != null) {
                                address.setCountry(optJSONArray.optJSONObject(4).optString("long_name"));
                            }
                            address.setLatitude(Double.valueOf(d3));
                            address.setLongitude(Double.valueOf(d4));
                            address.setPincode(optJSONArray.optJSONObject(5).optString("long_name"));
                            addressHeader = optJSONArray.optJSONObject(0).optString("long_name");
                            call = call.optJSONObject(0).optString("formatted_address");
                            addressEdit.setText(call);
                            addressHeader = call;
                            response = new StringBuilder();
                            response.append("");
                            response.append(GlobalData.addressHeader);
                            Log.v("Formatted Address", response.toString());
                        } else {
                            call = this;
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
                    call22 = this;
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
                call = this;
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
            CameraPosition cameraPosition = mMap.getCameraPosition();
            srcLat = Double.valueOf(cameraPosition.target.latitude);
            srcLng = Double.valueOf(cameraPosition.target.longitude);
            initializeAvd();
            getAddress(srcLat.doubleValue(), srcLng.doubleValue());
            skipTxt.setAlpha(1.0f);
            skipTxt.setClickable(true);
            skipTxt.setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCameraMove() {
        behavior.setState(4);
        dummyImageView.setVisibility(View.GONE);
        addressEdit.setText(getResources().getString(R.string.getting_address));
        animationLineCartAdd.setVisibility(View.VISIBLE);
        skipTxt.setAlpha(0.5f);
        skipTxt.setClickable(false);
        skipTxt.setEnabled(false);
    }

    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(102);
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    private void saveAddress() {
        if (address != null && address.getMapAddress() != null && validate()) {
            customDialog.show();
            apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
            apiInterface.saveAddress(address).enqueue(new C13539());
        }
    }

    private void updateAddress() {
        if (address.getType().equalsIgnoreCase(FacebookRequestErrorClassification.KEY_OTHER)) {
            address.setType(otherAddressHeaderEt.getText().toString());
        }
        if (address != null && address.getId() != null && validate()) {
            customDialog.show();
            apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
            apiInterface.updateAddress(address.getId().intValue(), address).enqueue(new Callback<Address>() {
                public void onResponse(@NonNull Call<Address> call, @NonNull Response<Address> response) {
                    customDialog.dismiss();
                    if (response.isSuccessful()) {
                        GlobalData.selectedAddress = response.body();
                        finish();
                        return;
                    }
                    Toast.makeText(this, ErrorUtils.parseError(response).getType().get(0), 0).show();
                }

                public void onFailure(@NonNull Call<Address> call, @NonNull Throwable th) {
                    Log.e(TAG, th.toString());
                    customDialog.dismiss();
                    Toast.makeText(this, "Something went wrong", 1).show();
                }
            });
        }
    }

    private boolean validate() {
        if (address.getMapAddress().isEmpty() && address.getMapAddress().equals(getResources().getString(R.string.getting_address))) {
            Toast.makeText(this, "Please enter address", 0).show();
            return false;
        } else if (address.getBuilding().isEmpty()) {
            Toast.makeText(this, "Please enter Flat No", 0).show();
            return false;
        } else if (address.getLandmark().isEmpty()) {
            Toast.makeText(this, "Please enter landmark", 0).show();
            return false;
        } else {
            if (address.getLatitude() != null) {
                if (address.getLongitude() != null) {
                    return true;
                }
            }
            Toast.makeText(this, "Lat & long cannot be left blank", 0).show();
            return false;
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (behavior.getState() == 3) {
            behavior.setState(4);
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
                otherAddressHeaderEt.setVisibility(View.VISIBLE);
                view = AnimationUtils.loadAnimation(context, R.anim.slide_out_right);
                view.setDuration(500);
                otherAddressHeaderEt.startAnimation(view);
                view = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
                view.setDuration(500);
                typeRadiogroup.startAnimation(view);
                typeRadiogroup.setVisibility(View.VISIBLE);
                otherRadio.setChecked(true);
                return;
            case R.id.imgCurrentLoc:
                if (crtLat != null && crtLng != null) {
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new Builder().target(new LatLng(crtLat.doubleValue(), crtLng.doubleValue())).zoom(16.0f).build()));
                    return;
                }
                return;
            case R.id.save:
                address.setMapAddress(addressEdit.getText().toString());
                address.setBuilding(flatNoEdit.getText().toString());
                address.setLandmark(landmark.getText().toString());
                if (!(address.getType().equalsIgnoreCase(FacebookRequestErrorClassification.KEY_OTHER) == null && address.getType().equalsIgnoreCase("") == null)) {
                    address.setType(otherAddressHeaderEt.getText().toString());
                }
                if (address.getBuilding().equalsIgnoreCase("")) {
                    Toast.makeText(context, "Please enter House/ flat no ", 0).show();
                    return;
                } else if (address.getLandmark().equalsIgnoreCase("")) {
                    Toast.makeText(context, "Please enter landmark ", 0).show();
                    return;
                } else {
                    if (address.getType().equalsIgnoreCase("")) {
                        address.setType(FacebookRequestErrorClassification.KEY_OTHER);
                    }
                    if (address.getId() != null) {
                        updateAddress();
                        return;
                    } else {
                        saveAddress();
                        return;
                    }
                }
            case R.id.skip_txt:
                address.setMapAddress(addressEdit.getText().toString());
                address.setType(addressHeader);
                GlobalData.selectedAddress = address;
                startActivity(new Intent(context, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
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
            currentLocImg.setBackgroundResource(R.drawable.ic_other_marker);
            otherAddressTitleLayout.setVisibility(View.VISIBLE);
            view = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
            view.setDuration(500);
            Animation loadAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_left);
            loadAnimation.setDuration(500);
            typeRadiogroup.startAnimation(loadAnimation);
            otherAddressTitleLayout.startAnimation(view);
            typeRadiogroup.setVisibility(View.GONE);
        }
    }

    /* renamed from: com.entriver.orderaround.activities.SaveDeliveryLocationActivity$5 */
    class C07595 implements OnCheckedChangeListener {
        C07595() {
        }

        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            RadioButton radioButton = radioGroup.findViewById(i);
            if (radioButton.getText().toString().toLowerCase().equals("home") != 0) {
                currentLocImg.setBackgroundResource(R.drawable.ic_hoem_marker);
            } else if (radioButton.getText().toString().toLowerCase().equals("work") != 0) {
                currentLocImg.setBackgroundResource(R.drawable.ic_work_marker);
            } else if (radioButton.getText().toString().equalsIgnoreCase(getResources().getString(R.string.other)) != 0) {
                currentLocImg.setBackgroundResource(R.drawable.ic_other_marker);
                otherAddressTitleLayout.setVisibility(View.VISIBLE);
                i = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
                i.setDuration(500);
                Animation loadAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_left);
                loadAnimation.setDuration(500);
                typeRadiogroup.startAnimation(loadAnimation);
                otherAddressTitleLayout.startAnimation(i);
                typeRadiogroup.setVisibility(View.GONE);
            }
            i = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("typeRadiogroup ");
            stringBuilder.append(radioButton.getText().toString().toLowerCase());
            i.println(stringBuilder.toString());
            address.setType(radioButton.getText().toString().toLowerCase());
        }
    }

    /* renamed from: com.entriver.orderaround.activities.SaveDeliveryLocationActivity$7 */
    class C07607 implements Runnable {
        C07607() {
        }

        public void run() {
            avdProgress.stop();
            if (animationLineCartAdd != null) {
                animationLineCartAdd.setVisibility(4);
            }
        }
    }

    /* renamed from: com.entriver.orderaround.activities.SaveDeliveryLocationActivity$1 */
    class C13481 implements OnSuccessListener<Location> {
        C13481() {
        }

        public void onSuccess(Location location) {
            if (location != null) {
                getAddress(location.getLatitude(), location.getLongitude());
            }
        }
    }

    /* renamed from: com.entriver.orderaround.activities.SaveDeliveryLocationActivity$2 */
    class C13492 implements OnSuccessListener<Location> {
        C13492() {
        }

        public void onSuccess(Location location) {
            if (location != null) {
                getAddress(location.getLatitude(), location.getLongitude());
            }
        }
    }

    /* renamed from: com.entriver.orderaround.activities.SaveDeliveryLocationActivity$3 */
    class C13503 extends BottomSheetCallback {
        C13503() {
        }

        public void onStateChanged(@NonNull View view, int i) {
            if (4 == i) {
                dummyImageView.startAnimation(slide_down);
                dummyImageView.setVisibility(View.GONE);
            } else if (3 == i) {
                dummyImageView.setVisibility(View.VISIBLE);
                dummyImageView.startAnimation(slide_up);
            }
        }

        public void onSlide(@NonNull View view, float f) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(f);
            Log.e("Slide :", stringBuilder.toString());
            if (((double) f) < 0.9d) {
                dummyImageView.setVisibility(1.1E-44f);
                dummyImageView.startAnimation(slide_down);
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
                addressEdit.setText(place.getAddress());
                LatLng latLng = place.getLatLng();
                value = 1;
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new Builder().target(latLng).zoom(16.0f).build()));
            }
            placeBuffer.release();
        }
    }

    /* renamed from: com.entriver.orderaround.activities.SaveDeliveryLocationActivity$9 */
    class C13539 implements Callback<Address> {
        C13539() {
        }

        public void onResponse(@NonNull Call<Address> call, @NonNull Response<Address> response) {
            customDialog.dismiss();
            if (response.isSuccessful() == null) {
                Toast.makeText(this, ErrorUtils.parseError(response).getType().get(0), 0).show();
            } else if (isAddressSave != null) {
                call = new Intent();
                GlobalData.selectedAddress = response.body();
                GlobalData.addressList.getAddresses().add(response.body());
                setResult(-1, call);
                finish();
            } else {
                GlobalData.selectedAddress = response.body();
                GlobalData.addressList.getAddresses().add(response.body());
                finish();
            }
        }

        public void onFailure(@NonNull Call<Address> call, @NonNull Throwable th) {
            Log.e(TAG, th.toString());
            customDialog.dismiss();
            Toast.makeText(this, "Something went wrong", 1).show();
        }
    }
}
