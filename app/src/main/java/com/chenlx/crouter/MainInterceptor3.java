package com.chenlx.crouter;


import android.util.Log;

import com.chenlx.crouter_api.Interceptor;

public class MainInterceptor3 extends Interceptor {
    @Override
    public boolean interceptor() {

        Log.i("MainInterceptor", "3");

        return true;
    }
}
