package com.chenlx.crouter;

import android.util.Log;

import com.chenlx.crouter_annotation.ModuleFunction;
import com.chenlx.crouter_api.IModuleMain;

@ModuleFunction
public class MainMain implements IModuleMain {


    @Override
    public void callModule(String tag) {

        Log.i("com.chenlx.couter_module_test", "new  callModule");

    }
}
