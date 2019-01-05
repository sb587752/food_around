package com.opalfire.foodorder.helper;

import android.app.ProgressDialog;
import android.content.Context;

public class CustomDialog extends ProgressDialog {
    public CustomDialog(Context context) {
        super(context);
        requestWindowFeature(1);
        setIndeterminate(true);
        setMessage("Please wait...");
        setCancelable(true);
    }
}
