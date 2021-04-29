package com.chenlx.crouter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.chenlx.crouter_annotation.ModuleFunction;
import com.chenlx.crouter_api.IModuleMain;

@ModuleFunction(interceptor = {MainInterceptor.class})
public class MainMain implements IModuleMain {


    @Override
    public void callModule(String tag) {

        Log.i("com.chenlx.couter_module_test", "new  callModule");


    }

    @Override
    public void startSecond(Context context, String tag) {


        ComponentName componentName = new ComponentName("com.chenlx.crouter",
                "com.chenlx.crouter_module_test.SecondActivity");
        context.startActivity(new Intent().setComponent(componentName));
    }

    @Override
    public void intercepter(String s) {

        Toast.makeText(App.getContext(), "s拦截了", Toast.LENGTH_LONG).show();
        Log.i("com.chenlx.couter_module_test", "intercepter  " + s);

    }


}
