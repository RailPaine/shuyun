package com.example.rail.shuyun.write;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.rail.shuyun.R;
import com.example.rail.shuyun.entity.BookMessageDetail;
import com.example.rail.shuyun.entity.Person;
import com.example.rail.shuyun.entity.Question;
import com.example.rail.shuyun.questionAty;


import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by qq on 2016/7/12.
 */
public class WriteQuestionAty extends Activity {

    private ImageButton title_imageBtn;
    private EditText content;
    private Button SureBtn;


    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_question);
        title_imageBtn = (ImageButton) findViewById(R.id.title_leftImageBtn);
        content = (EditText) findViewById(R.id.write_social_content);
        SureBtn = (Button) findViewById(R.id.write_social_btn);
        bundle = getIntent().getExtras();
        initListener();

    }

    private void initListener() {
        title_imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        SureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(content.getText().toString())) {
                    Person person = BmobUser.getCurrentUser(Person.class);
                    final Question post = new Question();
                    post.setAuthor(person);
                    post.setContent(content.getText().toString());
                    BookMessageDetail bookMessageDetail = new BookMessageDetail();
                    bookMessageDetail.setObjectId(bundle.getString("bookId"));
                    post.setBook(bookMessageDetail);
                    post.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                Toast.makeText(WriteQuestionAty.this, "数据新建成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(WriteQuestionAty.this, questionAty.class);
                                intent.putExtras(bundle);
                                startActivity(intent);

                            } else {
                                Toast.makeText(WriteQuestionAty.this, "数据新建失败", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(WriteQuestionAty.this, questionAty.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });


    }


}