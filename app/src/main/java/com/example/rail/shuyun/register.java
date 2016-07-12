package com.example.rail.shuyun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rail.shuyun.entity.Person;
import com.gc.materialdesign.views.ButtonRectangle;
import com.rengwuxian.materialedittext.MaterialEditText;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import util.CountDownTimerUtils;

/**
 * Created by rail on 2016/6/29.
 */
public class register extends AppCompatActivity {


    private TextView textView;
    private MaterialEditText userName, code, password;
    private ButtonRectangle btn;

    private ImageButton titile_leftBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        final CountDownTimerUtils countDownTimerUtils = new CountDownTimerUtils(textView, 60000, 1000);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimerUtils.start();
            }
        });
    }

    public void initView() {
        titile_leftBtn= (ImageButton) findViewById(R.id.title_leftImageBtn);
        textView = (TextView) findViewById(R.id.register_identifying_code);
        userName= (MaterialEditText) findViewById(R.id.register_user_text);
        code= (MaterialEditText) findViewById(R.id.register_identifying);
        password= (MaterialEditText) findViewById(R.id.register_password);
        btn= (ButtonRectangle) findViewById(R.id.register);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId=userName.getText().toString();
                String userPassword=password.getText().toString();
                if(!TextUtils.isEmpty(userId)&&!TextUtils.isEmpty(userPassword)){
                    SendToRegister(userId,userPassword);
                    userName.setText("");
                    password.setText("");
                }
            }
        });
        titile_leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register.this.finish();
            }
        });
    }
    private void SendToRegister(String phone, final String password)
    {
        BmobUser bu = new BmobUser();
        bu.setUsername(phone);
        bu.setPassword(password);
        bu.signUp(new SaveListener<Person>() {
            @Override
            public void done(Person s, BmobException e) {
                if(e==null){
                    //注册成功
                    Toast.makeText(register.this,"登陆成功",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(register.this,LoginAty.class);
                    startActivity(intent);
                }else{
                    //注册失败，抛出错误
                }
            }
        });
    }
}
