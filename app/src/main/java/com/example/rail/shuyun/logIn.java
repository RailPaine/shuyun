package com.example.rail.shuyun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rail.shuyun.constants.UrlConstant;
import com.example.rail.shuyun.entity.Person;
import com.gc.materialdesign.views.ButtonRectangle;
import com.rengwuxian.materialedittext.MaterialEditText;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


/**
 * Created by rail on 2016/6/29.
 */
public class logIn extends AppCompatActivity {
    private TextView re;
    private TextView fp;

    private MaterialEditText userName,passWord;
    private ButtonRectangle loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, UrlConstant.APPLICATION_ID);
        initView();
        initListener();
    }

    public void initView() {
        re = (TextView) findViewById(R.id.re);
        fp = (TextView) findViewById(R.id.forgetPassword);
        userName= (MaterialEditText) findViewById(R.id.login_user_text);
        passWord= (MaterialEditText) findViewById(R.id.login_password);
        loginBtn= (ButtonRectangle) findViewById(R.id.login);
    }

    private void initListener() {
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
                Intent intent = new Intent(logIn.this, forgetPassword.class);
                startActivity(intent);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId=userName.getText().toString();
                String userPassword=passWord.getText().toString();
                if(!TextUtils.isEmpty(userId)&&!TextUtils.isEmpty(userPassword)){
                    SendToLogin(userId, userPassword);
                    userName.setText("");
                    passWord.setText("");
                }
            }
        });
    }
    private void SendToLogin(String userName,String userPassword)
    {
        BmobUser bu2 = new BmobUser();
        bu2.setUsername(userName);
        bu2.setPassword(userPassword);
        bu2.login(new SaveListener<Person>() {

            @Override
            public void done(Person bmobUser, BmobException e) {
                if(e==null){
                    Toast.makeText(logIn.this,"登陆成功",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(logIn.this,MainActivity.class);
                    startActivity(intent);
                }else{
                }
            }
        });
    }
}
