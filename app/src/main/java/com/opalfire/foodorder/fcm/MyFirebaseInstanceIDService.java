package com.opalfire.foodorder.fcm;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.opalfire.foodorder.helper.SharedHelper;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseInstanceIDService";

    @Override
    public void onNewToken(String mToken) {
        super.onNewToken(mToken);
        SharedHelper.putKey(getApplicationContext(), "device_token", mToken);
    }


}
