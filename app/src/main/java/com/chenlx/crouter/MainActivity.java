package com.chenlx.crouter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.chenlx.crouter_annotation.ModuleAdd;

@ModuleAdd
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}