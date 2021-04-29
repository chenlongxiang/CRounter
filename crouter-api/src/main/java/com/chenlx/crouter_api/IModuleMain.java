package com.chenlx.crouter_api;


import android.content.Context;

public interface IModuleMain extends BaseInterface {

    void callModule(String tag);

    void startSecond(Context context, String tag);


}
