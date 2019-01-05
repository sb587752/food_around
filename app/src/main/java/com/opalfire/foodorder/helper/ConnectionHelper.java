package com.opalfire.foodorder.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Objects;

public class ConnectionHelper {
    private Context context;

    public ConnectionHelper(Context context) {
        this.context = context;
    }

    public boolean isConnectingToInternet() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) Objects.requireNonNull(this.context.getSystemService(Context.CONNECTIVITY_SERVICE))).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
