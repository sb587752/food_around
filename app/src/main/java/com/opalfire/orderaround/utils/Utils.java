package com.opalfire.orderaround.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.opalfire.orderaround.R;
import com.opalfire.orderaround.build.api.ApiInterface;
import com.opalfire.orderaround.helper.GlobalData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {
    private static final String TAG = Utils.class.getSimpleName();
    public static String address = "";
    public static boolean showLog = true;

    public static void displayMessage(Activity mActivity, Context mContext, String mStr) {
        Toast.makeText(mContext, mStr, Toast.LENGTH_SHORT).show();
    }

    public static void print(String str, String str2) {
        if (showLog) {
            Log.v(str, str2);
        }
    }

    public static boolean isShopChanged(int i) {
        return GlobalData.addCart != null && !GlobalData.addCart.getProductList().isEmpty() && GlobalData.addCart.getProductList().get(0).getProduct().getShopId().equals(i);
    }

    public String getAddress(final Context context, double lat, double lng) {
        Retrofit retrofit = new Builder().baseUrl("https://maps.googleapis.com/maps/api/geocode/").addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        String latlng = String.valueOf(lat) + "," + lng;
        apiInterface.getResponse(latlng, context.getResources().getString(R.string.google_api_key)).enqueue(new Callback<ResponseBody>() {
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    if (response.body() != null) {
                        Log.e(TAG, "onResponse: " + response.body().string());
                        JSONArray resObj = new JSONObject(response.body().string()).optJSONArray("results");
                        GlobalData.addressHeader = resObj.optJSONObject(0).optString("formatted_address");

                    } else {
                    }
                    Intent intent = new Intent("location");
                    intent.putExtra("message", "This is my message!");
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }

            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable th) {
                th.printStackTrace();
            }
        });
        return address;
    }
}
