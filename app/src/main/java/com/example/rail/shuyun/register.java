package com.example.rail.shuyun;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import util.CountDownTimerUtils;

/**
 * Created by rail on 2016/6/29.
 */
public class register extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
         initView();
        final CountDownTimerUtils countDownTimerUtils=new CountDownTimerUtils(textView,60000,1000);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimerUtils.start();
            }
        });
    }
    public void initView()
    {
        textView=(TextView)findViewById(R.id.register_identifying_code);
    }
}
