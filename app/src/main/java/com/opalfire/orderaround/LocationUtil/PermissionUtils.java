package com.opalfire.orderaround.LocationUtil;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Build.VERSION;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog.Builder;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class PermissionUtils {
    Context context;
    Activity current_activity;
    String dialog_content = "";
    ArrayList<String> listPermissionsNeeded = new ArrayList();
    PermissionResultCallback permissionResultCallback;
    ArrayList<String> permission_list = new ArrayList();
    int req_code;

    public PermissionUtils(Context context) {
        this.context = context;
        this.current_activity = (Activity) context;
        this.permissionResultCallback = (PermissionResultCallback) context;
    }

    public PermissionUtils(Context context, PermissionResultCallback permissionResultCallback) {
        this.context = context;
        this.current_activity = (Activity) context;
        this.permissionResultCallback = permissionResultCallback;
    }

    public void check_permission(ArrayList<String> arrayList, String str, int i) {
        this.permission_list = arrayList;
        this.dialog_content = str;
        this.req_code = i;
        if (VERSION.SDK_INT < 23) {
            this.permissionResultCallback.PermissionGranted(i);
            Log.i("all permissions", "granted");
            Log.i("proceed", "to callback");
        } else if (checkAndRequestPermissions(arrayList, i) != null) {
            this.permissionResultCallback.PermissionGranted(i);
            Log.i("all permissions", "granted");
            Log.i("proceed", "to callback");
        }
    }

    private boolean checkAndRequestPermissions(ArrayList<String> arrayList, int i) {
        if (arrayList.size() > 0) {
            this.listPermissionsNeeded = new ArrayList();
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                if (ContextCompat.checkSelfPermission(this.current_activity, (String) arrayList.get(i2)) != 0) {
                    this.listPermissionsNeeded.add(arrayList.get(i2));
                }
            }
            if (this.listPermissionsNeeded.isEmpty() == null) {
                ActivityCompat.requestPermissions(this.current_activity, (String[]) this.listPermissionsNeeded.toArray(new String[this.listPermissionsNeeded.size()]), i);
                return false;
            }
        }
        return true;
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 1) {
            if (iArr.length > 0) {
                i = new HashMap();
                for (int i2 = 0; i2 < strArr.length; i2++) {
                    i.put(strArr[i2], Integer.valueOf(iArr[i2]));
                }
                strArr = new ArrayList();
                for (int i3 = 0; i3 < this.listPermissionsNeeded.size(); i3++) {
                    if (((Integer) i.get(this.listPermissionsNeeded.get(i3))).intValue() != null) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this.current_activity, (String) this.listPermissionsNeeded.get(i3)) != null) {
                            strArr.add(this.listPermissionsNeeded.get(i3));
                        } else {
                            Log.i("Go to settings", "and enable permissions");
                            this.permissionResultCallback.NeverAskAgain(this.req_code);
                            Toast.makeText(this.current_activity, "Go to settings and enable permissions", 1).show();
                            return;
                        }
                    }
                }
                if (strArr.size() > 0) {
                    showMessageOKCancel(this.dialog_content, new OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            switch (i) {
                                case -2:
                                    Log.i("permisson", "not fully given");
                                    if (PermissionUtils.this.permission_list.size() == strArr.size()) {
                                        PermissionUtils.this.permissionResultCallback.PermissionDenied(PermissionUtils.this.req_code);
                                        return;
                                    } else {
                                        PermissionUtils.this.permissionResultCallback.PartialPermissionGranted(PermissionUtils.this.req_code, strArr);
                                        return;
                                    }
                                case -1:
                                    PermissionUtils.this.check_permission(PermissionUtils.this.permission_list, PermissionUtils.this.dialog_content, PermissionUtils.this.req_code);
                                    return;
                                default:
                                    return;
                            }
                        }
                    });
                } else {
                    Log.i("all", "permissions granted");
                    Log.i("proceed", "to next step");
                    this.permissionResultCallback.PermissionGranted(this.req_code);
                }
            }
        }
    }

    private void showMessageOKCancel(String str, OnClickListener onClickListener) {
        new Builder(this.current_activity).setMessage((CharSequence) str).setPositiveButton((CharSequence) "Ok", onClickListener).setNegativeButton((CharSequence) "Cancel", onClickListener).create().show();
    }

    public interface PermissionResultCallback {
        void NeverAskAgain(int i);

        void PartialPermissionGranted(int i, ArrayList<String> arrayList);

        void PermissionDenied(int i);

        void PermissionGranted(int i);
    }
}
