package com.example.rail.shuyun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import util.CountDownTimerUtils;

/**
 * Created by rail on 2016/6/29.
 */
public class forgetPassword extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
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
        textView=(TextView)findViewById(R.id.fp_identifying_code);
    }
}
