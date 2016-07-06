package com.example.rail.shuyun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by rail on 2016/6/29.
 */
public class logIn extends AppCompatActivity {
    private TextView re;
    private TextView fp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(logIn.this, register.class);
                startActivity(intent);
            }
        });
        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(logIn.this,forgetPassword.class);
                startActivity(intent);
            }
        });
    }
    public void initView()
    {
      re=(TextView)findViewById(R.id.re);
       fp=(TextView)findViewById(R.id.forgetPassword);
    }
}
