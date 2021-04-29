package com.chenlx.crouter;


import com.chenlx.crouter_api.Interceptor;

public class MainInterceptor extends Interceptor {
    @Override
    public boolean interceptor() {


        String time = System.currentTimeMillis() + "";

        String end = time.charAt(time.length() - 1) + "";
        return Integer.parseInt(end) % 2 == 0;
    }
}
