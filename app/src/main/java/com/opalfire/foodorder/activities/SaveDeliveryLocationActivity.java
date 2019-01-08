package com.opalfire.foodorder.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.support.v4.app.ActivityCompat;
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
import com.opalfire.foodorder.HomeActivity;
import com.opalfire.foodorder.R;
import com.opalfire.foodorder.build.api.ApiClient;
import com.opalfire.foodorder.build.api.ApiInterface;
import com.opalfire.foodorder.build.api.ErrorUtils;
import com.opalfire.foodorder.helper.CustomDialog;
import com.opalfire.foodorder.helper.GlobalData;
import com.opalfire.foodorder.models.Address;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
    public static ApiInterface apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
    Address addressModel = null;
    AnimatedVectorDrawableCompat avdProgress;
    Context context;
    CustomDialog customDialog;
    boolean isAddressSave = false;
    boolean isSkipVisible = false;
    FusedLocationProviderClient mFusedLocationClient;
    Retrofit retrofit;
    Animation slide_down;
    Animation slide_up;
    @BindView(R.id.imgCurrentLoc)
    ImageView imgCurrentLoc;
    @BindView(R.id.current_loc_img)
    ImageView currentLocImg;
    @BindView(R.id.dummy_image_view)
    ImageView dummyImageView;
    @BindView(R.id.backArrow)
    ImageView backArrow;
    @BindView(R.id.animation_line_cart_add)
    ImageView animationLineCartAdd;
    Runnable action = new Runnable() {
        @Override
        public void run() {
            avdProgress.stop();
            if (animationLineCartAdd != null) {
                animationLineCartAdd.setVisibility(View.INVISIBLE);
            }
        }
    };
    @BindView(R.id.skip_txt)
    TextView skipTxt;
    @BindView(R.id.flat_no)
    EditText flatNo;
    @BindView(R.id.landmark)
    EditText landmark;
    @BindView(R.id.home_radio)
    RadioButton homeRadio;
    @BindView(R.id.work_radio)
    RadioButton workRadio;
    @BindView(R.id.other_radio)
    RadioButton otherRadio;
    @BindView(R.id.type_radiogroup)
    RadioGroup typeRadiogroup;
    @BindView(R.id.other_address_header_et)
    EditText otherAddressHeaderEt;
    @BindView(R.id.cancel_txt)
    TextView cancelTxt;
    @BindView(R.id.other_address_title_layout)
    RelativeLayout otherAddressTitleLayout;
    @BindView(R.id.save)
    Button save;
    @BindView(R.id.bottom_sheet)
    CardView bottomSheet;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.address_edit)
    EditText addressEdit;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_delivery_location);
        getWindow().setFlags(1024, 1024);
        ButterKnife.bind(this);
        context = this;
        addressModel = new Address();
        customDialog = new CustomDialog(context);
        addressModel.setType(FacebookRequestErrorClassification.KEY_OTHER);
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
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        getAddress(location.getLatitude(), location.getLongitude());
                    }
                }
            });
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            buildGoogleApiClient();
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        getAddress(location.getLatitude(), location.getLongitude());
                    }
                }
            });
        }
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        behavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        dummyImageView.setVisibility(View.VISIBLE);
        behavior.setBottomSheetCallback(new BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        dummyImageView.setVisibility(View.VISIBLE);
                        dummyImageView.startAnimation(slide_up);
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        dummyImageView.startAnimation(slide_down);
                        dummyImageView.setVisibility(View.GONE);
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                if (((double) v) < 0.9d) {
                    dummyImageView.setVisibility(View.GONE);
                    dummyImageView.startAnimation(slide_down);
                }
            }
        });
        otherRadio.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                currentLocImg.setBackgroundResource(R.drawable.ic_other_marker);
                otherAddressTitleLayout.setVisibility(View.VISIBLE);
                Animation slide_in_right = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
                slide_in_right.setDuration(500);
                Animation slide_out_left = AnimationUtils.loadAnimation(context, R.anim.slide_out_left);
                slide_out_left.setDuration(500);
                typeRadiogroup.startAnimation(slide_out_left);
                otherAddressTitleLayout.startAnimation(slide_in_right);
                typeRadiogroup.setVisibility(View.GONE);
            }
        });
        typeRadiogroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = radioGroup.findViewById(i);
                if (radioButton.getText().toString().toLowerCase().equals("home")) {
                    currentLocImg.setBackgroundResource(R.drawable.ic_hoem_marker);
                } else if (radioButton.getText().toString().toLowerCase().equals("work")) {
                    currentLocImg.setBackgroundResource(R.drawable.ic_work_marker);
                } else if (radioButton.getText().toString().equalsIgnoreCase(getResources().getString(R.string.other))) {
                    currentLocImg.setBackgroundResource(R.drawable.ic_other_marker);
                    otherAddressTitleLayout.setVisibility(View.VISIBLE);
                    Animation slide_in_right = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
                    slide_in_right.setDuration(500);
                    Animation slide_out_left = AnimationUtils.loadAnimation(context, R.anim.slide_out_left);
                    slide_out_left.setDuration(500);
                    typeRadiogroup.startAnimation(slide_out_left);
                    otherAddressTitleLayout.startAnimation(slide_in_right);
                    typeRadiogroup.setVisibility(View.GONE);
                }
                addressModel.setType(radioButton.getText().toString().toLowerCase());
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String string = bundle.getString("place_id");
            String editString = bundle.getString("edit");
            if (string != null) {
                Places.GeoDataApi.getPlaceById(mGoogleApiClient, string).setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(@NonNull PlaceBuffer places) {
                        if (!places.getStatus().isSuccess() || places.getCount() <= 0) {
                            Log.d(TAG, "Place not found");
                        } else {
                            Place place = places.get(View.VISIBLE);
                            addressEdit.setText(place.getAddress());
                            LatLng latLng = place.getLatLng();
                            value = 1;
                            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new Builder().target(latLng).zoom(16.0f).build()));
                        }
                        places.release();
                    }
                });
            }
            if (editString != null && editString.equals("yes") && GlobalData.selectedAddress != null) {
                addressModel = GlobalData.selectedAddress;
                addressEdit.setText(addressModel.getMapAddress());
                flatNo.setText(addressModel.getBuilding());
                landmark.setText(addressModel.getLandmark());
                String addressType = addressModel.getType();
                switch (addressType) {
                    case "work":
                        homeRadio.setChecked(true);
                        break;
                    case "home":
                        workRadio.setChecked(true);
                        break;
                    default:
                        otherAddressHeaderEt.setText(addressModel.getType());
                        otherRadio.setChecked(true);
                        break;
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

    @Override
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
        mMap = googleMap;
        if (mMap != null) {
            mMap.getUiSettings().setCompassEnabled(false);
            mMap.setBuildingsEnabled(true);
            mMap.setOnCameraMoveListener(this);
            mMap.setOnCameraIdleListener(this);
            mMap.getUiSettings().setRotateGesturesEnabled(false);
            mMap.getUiSettings().setTiltGesturesEnabled(false);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Places.GEO_DATA_API).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();

    }

    @Override
    public void onLocationChanged(Location location) {
        System.out.println("onLocationChanged ");
        if (value == 0) {
            value = 1;
            if (addressModel.getId() == null) {
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new Builder().target(new LatLng(location.getLatitude(), location.getLongitude())).zoom(16.0f).build()));
            } else {
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new Builder().target(new LatLng(addressModel.getLatitude(), addressModel.getLongitude())).zoom(16.0f).build()));
            }
            getAddress(location.getLatitude(), location.getLongitude());
        }
        crtLat = location.getLatitude();
        crtLng = location.getLongitude();
    }

    private void getAddress(double d, double d2) {
        Log.d(TAG, "getAddress: " + d + " | " + d2);
        try {
            List fromLocation = new Geocoder(this, Locale.getDefault()).getFromLocation(d, d2, 1);
            if (fromLocation == null || fromLocation.size() <= 0) {
                getAddress(context, d, d2);
                return;
            }
            android.location.Address address = (android.location.Address) fromLocation.get(0);
            StringBuilder stringBuilder2 = new StringBuilder();
            if (address.getMaxAddressLineIndex() > 0) {
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    stringBuilder2.append(address.getAddressLine(i));
                }
            } else {
                stringBuilder2.append(address.getAddressLine(0));
            }
            addressEdit.setText(stringBuilder2.toString());
            addressModel.setCity(address.getLocality());
            addressModel.setState(address.getAdminArea());
            addressModel.setCountry(address.getCountryName());
            addressModel.setLatitude(address.getLatitude());
            addressModel.setLongitude(address.getLongitude());
            addressModel.setPincode(address.getPostalCode());
            addressHeader = address.getFeatureName();
        } catch (Exception e) {
            e.printStackTrace();
            getAddress(context, d, d2);
        }
    }

    public void getAddress(final Context context, final double d, final double d2) {
        retrofit = new Retrofit.Builder().baseUrl("https://maps.googleapis.com/maps/api/geocode/").addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(ApiInterface.class);

        apiInterface.getResponse(d + "," + d2, context.getResources().getString(R.string.google_api_key)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                Log.e(TAG, "onResponse: " + response.toString());
                if (response.body() != null) {
                    try {

                        JSONArray resArr = new JSONObject(response.body().string()).optJSONArray("results");
                        if (resArr.length() > 0) {
                            JSONArray optJSONArray = resArr.optJSONObject(0).optJSONArray("address_components");
                            optJSONArray.optJSONObject(0).optString("long_name");
                            addressModel.setCity(optJSONArray.optJSONObject(2).optString("long_name"));
                            addressModel.setState(optJSONArray.optJSONObject(3).optString("long_name"));
                            if (optJSONArray.optJSONObject(4).optString("long_name") != null) {
                                addressModel.setCountry(optJSONArray.optJSONObject(4).optString("long_name"));
                            }
                            addressModel.setLatitude(d);
                            addressModel.setLongitude(d2);
                            addressModel.setPincode(optJSONArray.optJSONObject(5).optString("long_name"));
                            addressHeader = optJSONArray.optJSONObject(0).optString("long_name");
                            String formattedAddress = resArr.optJSONObject(0).optString("formatted_address");
                            addressEdit.setText(formattedAddress);
                        } else {
                            Log.e(TAG, "onResponse: " + response.toString());
                        }

                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e(TAG, "onResponse: " + response.toString());
                }
                Intent intent = new Intent("location");
                intent.putExtra("message", "This is my message!");
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable th) {
                Log.e(TAG, "onFailure: Failed to get addressModel", th);
            }
        });
    }

    @Override
    public void onCameraIdle() {
        try {
            CameraPosition cameraPosition = mMap.getCameraPosition();
            srcLat = cameraPosition.target.latitude;
            srcLng = cameraPosition.target.longitude;
            initializeAvd();
            getAddress(srcLat, srcLng);
            skipTxt.setAlpha(1.0f);
            skipTxt.setClickable(true);
            skipTxt.setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCameraMove() {
        behavior.setState(4);
        dummyImageView.setVisibility(View.GONE);
        addressEdit.setText(getResources().getString(R.string.getting_address));
        animationLineCartAdd.setVisibility(View.VISIBLE);
        skipTxt.setAlpha(0.5f);
        skipTxt.setClickable(false);
        skipTxt.setEnabled(false);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    private void saveAddress() {
        if (addressModel != null && addressModel.getMapAddress() != null && validate()) {
            customDialog.show();
            apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
            apiInterface.saveAddress(addressModel).enqueue(new Callback<Address>() {

                @Override
                public void onResponse(@NonNull Call<Address> call, @NonNull Response<Address> response) {
                    customDialog.dismiss();
                    if (response.isSuccessful()) {
                        Toast.makeText(context, ErrorUtils.parseError(response).getType().get(0), Toast.LENGTH_LONG).show();
                    } else if (isAddressSave) {
                        GlobalData.selectedAddress = response.body();
                        GlobalData.addressList.getAddresses().add(response.body());
                        setResult(-1, new Intent());
                        finish();
                    } else {
                        GlobalData.selectedAddress = response.body();
                        GlobalData.addressList.getAddresses().add(response.body());
                        finish();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Address> call, @NonNull Throwable th) {
                    Log.e(TAG, "failed to save the address", th);
                    customDialog.dismiss();
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void updateAddress() {
        if (addressModel.getType().equalsIgnoreCase(FacebookRequestErrorClassification.KEY_OTHER)) {
            addressModel.setType(otherAddressHeaderEt.getText().toString());
        }
        if (addressModel != null && addressModel.getId() != null && validate()) {
            customDialog.show();
            apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
            apiInterface.updateAddress(addressModel.getId(), addressModel).enqueue(new Callback<Address>() {
                public void onResponse(@NonNull Call<Address> call, @NonNull Response<Address> response) {
                    customDialog.dismiss();
                    if (response.isSuccessful()) {
                        GlobalData.selectedAddress = response.body();
                        finish();
                        return;
                    }
                    Toast.makeText(context, ErrorUtils.parseError(response).getType().get(0), Toast.LENGTH_LONG).show();
                }

                public void onFailure(@NonNull Call<Address> call, @NonNull Throwable th) {
                    Log.e(TAG, th.toString());
                    customDialog.dismiss();
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private boolean validate() {
        if (addressModel.getMapAddress().isEmpty() && addressModel.getMapAddress().equals(getResources().getString(R.string.getting_address))) {
            Toast.makeText(context, "Please enter addressModel", Toast.LENGTH_SHORT).show();
            return false;
        } else if (addressModel.getBuilding().isEmpty()) {
            Toast.makeText(context, "Please enter Flat No", Toast.LENGTH_SHORT).show();
            return false;
        } else if (addressModel.getLandmark().isEmpty()) {
            Toast.makeText(context, "Please enter landmark", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (addressModel.getLatitude() != null) {
                if (addressModel.getLongitude() != null) {
                    return true;
                }
            }
            Toast.makeText(context, "Lat & long cannot be left blank", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (behavior.getState() == 3) {
            behavior.setState(4);
        }
        finish();
        overridePendingTransition(R.anim.anim_nothing, R.anim.slide_out_right);
    }

    @OnClick({R.id.backArrow, R.id.cancel_txt, R.id.imgCurrentLoc, R.id.save, R.id.skip_txt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backArrow:
                onBackPressed();
                return;
            case R.id.cancel_txt:
                otherAddressHeaderEt.setVisibility(View.VISIBLE);
                Animation slide_out_right = AnimationUtils.loadAnimation(context, R.anim.slide_out_right);
                slide_out_right.setDuration(500);
                otherAddressHeaderEt.startAnimation(slide_out_right);
                Animation slide_in_left = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
                slide_in_left.setDuration(500);
                typeRadiogroup.startAnimation(slide_in_left);
                typeRadiogroup.setVisibility(View.VISIBLE);
                otherRadio.setChecked(true);
                return;
            case R.id.imgCurrentLoc:
                if (crtLat != null && crtLng != null) {
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new Builder().target(new LatLng(crtLat, crtLng)).zoom(16.0f).build()));
                    return;
                }
                return;
            case R.id.save:
                addressModel.setMapAddress(addressEdit.getText().toString());
                addressModel.setBuilding(flatNo.getText().toString());
                addressModel.setLandmark(landmark.getText().toString());
                if (!(addressModel.getType().equalsIgnoreCase(FacebookRequestErrorClassification.KEY_OTHER) && addressModel.getType().equalsIgnoreCase(""))) {
                    addressModel.setType(otherAddressHeaderEt.getText().toString());
                }
                if (addressModel.getBuilding().equalsIgnoreCase("")) {
                    Toast.makeText(context, "Please enter House/ flat no ", Toast.LENGTH_SHORT).show();
                    return;
                } else if (addressModel.getLandmark().equalsIgnoreCase("")) {
                    Toast.makeText(context, "Please enter landmark ", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (addressModel.getType().equalsIgnoreCase("")) {
                        addressModel.setType(FacebookRequestErrorClassification.KEY_OTHER);
                    }
                    if (addressModel.getId() != null) {
                        updateAddress();
                        return;
                    } else {
                        saveAddress();
                        return;
                    }
                }
            case R.id.skip_txt:
                addressModel.setMapAddress(addressEdit.getText().toString());
                addressModel.setType(addressHeader);
                GlobalData.selectedAddress = addressModel;
                startActivity(new Intent(context, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                return;
            default:
                return;
        }
    }

}
