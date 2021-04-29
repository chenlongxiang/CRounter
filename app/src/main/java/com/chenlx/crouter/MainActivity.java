package com.chenlx.crouter;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.chenlx.crouter_api.IModuleMain;
import com.chenlx.crouter_init.$;

/**
 * 节点，注解编译器生效，能生成Java文件
 */
public class MainActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv_tip);


        try {
            $.addOrReplaceInstance(new MainMain(), IModuleMain.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IModuleMain iModuleMain = $.find(
                        IModuleMain.class);
                iModuleMain.callModule("hhhhh");
            }
        });
    }
}