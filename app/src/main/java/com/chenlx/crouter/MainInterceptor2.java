package com.chenlx.crouter;


import android.util.Log;

import com.chenlx.crouter_api.Interceptor;

public class MainInterceptor2 extends Interceptor {
    @Override
    public boolean interceptor() {

        Log.i("MainInterceptor", "2");

        return false;
    }
}
