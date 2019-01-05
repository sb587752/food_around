package com.opalfire.foodorder.LocationUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

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
import com.opalfire.foodorder.LocationUtil.PermissionUtils.PermissionResultCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class LocationHelper implements PermissionResultCallback {
    private static final int PLAY_SERVICES_REQUEST = 1000;
    private static final int REQUEST_CHECK_SETTINGS = 2000;
    public boolean isPermissionGranted;
    private Context mContext;
    private Activity current_activity;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private PermissionUtils permissionUtils;
    private ArrayList<String> permissions = new ArrayList();

    public LocationHelper(Context context) {
        mContext = context;
        current_activity = (Activity) context;
        permissionUtils = new PermissionUtils(mContext, this);
        permissions.add("android.permission.ACCESS_FINE_LOCATION");
        permissions.add("android.permission.ACCESS_COARSE_LOCATION");
    }

    public void checkpermission() {
        permissionUtils.check_permission(permissions, "Need GPS permission for getting your location", 1);
    }

    private boolean isPermissionGranted() {
        return isPermissionGranted;
    }

    public boolean checkPlayServices() {
        GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
        int isGooglePlayServicesAvailable = instance.isGooglePlayServicesAvailable(mContext);
        if (isGooglePlayServicesAvailable == 0) {
            return true;
        }
        if (instance.isUserResolvableError(isGooglePlayServicesAvailable)) {
            instance.getErrorDialog(current_activity, isGooglePlayServicesAvailable, 1000).show();
        } else {
            showToast("This device is not supported.");
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    public Location getLocation() {
        if (isPermissionGranted()) {
            try {
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                return mLastLocation;
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Address getAddress(double d, double d2) {
        try {
            return new Geocoder(mContext, Locale.getDefault()).getFromLocation(d, d2, 1).get(View.VISIBLE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void buildGoogleApiClient() {
        mGoogleApiClient = new Builder(mContext).addConnectionCallbacks((ConnectionCallbacks) mContext).addOnConnectionFailedListener((OnConnectionFailedListener) mContext).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(100);
        LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, new LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build()).setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
            }
        });
    }

    public void connectApiClient() {
        mGoogleApiClient.connect();
    }

    public GoogleApiClient getGoogleApiCLient() {
        return mGoogleApiClient;
    }


    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        permissionUtils.onRequestPermissionsResult(i, strArr, iArr);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == REQUEST_CHECK_SETTINGS) {
            switch (i2) {
                case -1:
                    mLastLocation = getLocation();
                    return;
                case 0:
                    return;
                default:
            }
        }
    }

    public void PermissionGranted(int i) {
        Log.i("PERMISSION", "GRANTED");
        isPermissionGranted = true;
    }

    public void PartialPermissionGranted(int i, ArrayList<String> arrayList) {
        Log.i("PERMISSION PARTIALLY", "GRANTED");
    }

    public void PermissionDenied(int i) {
        Log.i("PERMISSION", "DENIED");
    }

    public void NeverAskAgain(int i) {
        Log.i("PERMISSION", "NEVER ASK AGAIN");
    }

    private void showToast(String str) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
    }

}
