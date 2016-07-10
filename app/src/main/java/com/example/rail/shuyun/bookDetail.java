package com.example.rail.shuyun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;

import com.gc.materialdesign.views.ButtonRectangle;

/**
 * Created by rail on 2016/7/6.
 */
public class bookDetail extends AppCompatActivity {

    private ButtonRectangle question,resourceShare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("书籍详情");
        setContentView(R.layout.activity_bookdetail);
        question=(ButtonRectangle)findViewById(R.id.bookdetail_question);
        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(bookDetail.this,question.class);
                startActivity(intent);
            }
        });
        resourceShare=(ButtonRectangle)findViewById(R.id.bookdetail_resourceshare);
        resourceShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(bookDetail.this,resourceShare.class);
                startActivity(intent1);
            }
        });
    }

}
