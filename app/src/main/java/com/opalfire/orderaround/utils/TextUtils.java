package com.opalfire.orderaround.utils;

import android.util.Patterns;

public class TextUtils {
    public static boolean isEmpty(String str) {
        return str == null || (str.length() == 0);
    }

    public static boolean isValidEmail(String str) {
        return str == null || Patterns.EMAIL_ADDRESS.matcher(str).matches();
    }
}
