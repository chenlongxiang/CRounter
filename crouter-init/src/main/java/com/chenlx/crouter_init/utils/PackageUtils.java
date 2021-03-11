package com.chenlx.crouter_init.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import static com.chenlx.crouter_init.utils.Constants.CROUTER_SP_CACHE_KEY;
import static com.chenlx.crouter_init.utils.Constants.LAST_VERSION_CODE;


public class PackageUtils {
    private static int NEW_VERSION_CODE;

    public static boolean isNewVersion(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (null != packageInfo) {
            String versionName = packageInfo.versionName;
            int versionCode = packageInfo.versionCode;

            SharedPreferences sp = context.getSharedPreferences(CROUTER_SP_CACHE_KEY,
                    Context.MODE_PRIVATE);
            if (versionCode != sp.getInt(LAST_VERSION_CODE, -1)) {
                // new version
                NEW_VERSION_CODE = versionCode;

                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static void updateVersion(Context context) {
        if (NEW_VERSION_CODE != 0) {
            SharedPreferences sp = context.getSharedPreferences(CROUTER_SP_CACHE_KEY,
                    Context.MODE_PRIVATE);
            sp.edit().putInt(LAST_VERSION_CODE,
                    NEW_VERSION_CODE).apply();
        }
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
        } catch (Exception ex) {
            Log.e("CRouter_getPackageInfo", "Get package info error.");
        }

        return packageInfo;
    }
}
