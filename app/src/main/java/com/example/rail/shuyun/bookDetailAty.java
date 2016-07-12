package com.example.rail.shuyun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.rail.shuyun.entity.BookMessageDetail;
import com.gc.materialdesign.views.ButtonRectangle;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by rail on 2016/7/6.
 */
public class bookDetailAty extends AppCompatActivity {

    private ButtonRectangle question,resourceShare,socialShare;

    private TextView bookName;
    private TextView bookAuthor;
    private TextView bookContent;
    private TextView bookPlace;

    private ImageButton title_leftBtn;
    private TextView title_middle;

    private Bundle bundle;
    private String Name;

    private BookMessageDetail bookdetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("书籍详情");
        setContentView(R.layout.activity_bookdetail);
        initButton();
        initView();
        initListener();
        bundle=getIntent().getExtras();
        Name=bundle.getString("bookName");
//        loadData();
    }
    private void initView(){
        bookName= (TextView) findViewById(R.id.bookdetail_name_tv);
        bookAuthor= (TextView) findViewById(R.id.bookdetail_author_tv);
        bookContent= (TextView) findViewById(R.id.bookdetail_bookContent);
        bookPlace= (TextView) findViewById(R.id.bookdetail_publish_tv);
        title_leftBtn= (ImageButton) findViewById(R.id.title_leftImageBtn);
        title_middle= (TextView) findViewById(R.id.title_middleTextView);
    }
    private void initButton(){
        question=(ButtonRectangle)findViewById(R.id.bookdetail_question);
        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(bookDetailAty.this,question.class);
                startActivity(intent);
            }
        });
        resourceShare=(ButtonRectangle)findViewById(R.id.bookdetail_resourceshare);
        resourceShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(bookDetailAty.this,resourceShare.class);
                startActivity(intent1);
            }
        });
        socialShare=(ButtonRectangle)findViewById(R.id.bookdetail_social);
        socialShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(bookDetailAty.this,SocialShareAty.class);
                startActivity(intent2);
            }
        });
    }

    private void initListener(){
        title_leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(bookDetailAty.this, BookListAty.class);
                intent.putExtras(bundle);
                startActivity(intent);
                bookDetailAty.this.finish();
            }
        });
    }
    private void loadData(){
        BmobQuery<BookMessageDetail> bmobQuery=new BmobQuery<>();
        bmobQuery.addWhereEqualTo("bookName",Name);
        bmobQuery.findObjects(new FindListener<BookMessageDetail>() {
            @Override
            public void done(List<BookMessageDetail> list, BmobException e) {
                if (e == null) {
                    bookdetail = list.get(0);
                    setData();
                }
            }
        });
    }
    private void setData(){
        bookName.setText(bookdetail.getBookName());
        bookAuthor.setText(bookdetail.getBookAuthor());
        bookPlace.setText(bookdetail.getBookPublish());
        bookContent.setText(bookdetail.getBookContent());
    }
}
