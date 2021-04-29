package com.chenlx.crouter;

import android.app.Application;
import android.util.Log;

import com.chenlx.crouter_init.CRouter;

public class App extends Application {

    private static final String TAG = "CRouter_app";

    private volatile static boolean hasInit = false;


    @Override
    public void onCreate() {
        super.onCreate();

        try {
            /**
             *防止多进程多次调用
             */
            if (!CRouter.isHasInit()) {
                CRouter.init(this, getPackageName(), BuildConfig.EXCLUDE_PACKAGE,
                        BuildConfig.API_PATH);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "init e:" + e);
        }
    }
}
