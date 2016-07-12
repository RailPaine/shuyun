package com.example.rail.shuyun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rail.shuyun.adapter.BookListAdapter;
import com.example.rail.shuyun.entity.BookList;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import recyclerview.Item;

/**
 * Created by rail on 2016/7/5.
 */
public class BookListAty extends AppCompatActivity {

    private List<Item>booklist=new ArrayList<>();
    private String SchoolName,ColleageName,MajorName,GradeName;
    private Bundle bundle;

    private ImageButton title_leftBtn;
    private TextView title_middle;
    private PullToRefreshListView listView;

    private BookListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_booklist);
        initView();
        initData();
        initListener();
    }

    private void initView(){
        title_leftBtn= (ImageButton) findViewById(R.id.title_leftImageBtn);
        title_middle= (TextView) findViewById(R.id.title_middleTextView);
        listView= (PullToRefreshListView) findViewById(R.id.booklist_listView);
    }
    private void initData(){
        title_middle.setText("书籍列表");
        bundle=getIntent().getExtras();
        SchoolName=bundle.getString("School");
        ColleageName=bundle.getString("Colleage");
        MajorName=bundle.getString("Major");
        GradeName=bundle.getString("Grade");
        //测试数据
        booklist.add(new Item("计算机组成","wencabiam.xjijnfj.xxxf"));
        booklist.add(new Item("计算机组成","wencabiam.xjijnfj.xxxf"));
        booklist.add(new Item("计算机组成","wencabiam.xxx"));
        adapter=new BookListAdapter(this,booklist);
        listView.setAdapter(adapter);
//        getData();
    }
    private void initListener(){
        title_leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookListAty.this.finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(BookListAty.this,bookDetail.class);
                bundle.putString("bookName",booklist.get(position-1).getBookname());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                getData();//从网络加载数据
            }
        });
    }
    /**
     * 根据相应的数据获取书籍集合
     */
    private void getData(){
        BmobQuery<BookList> query=new BmobQuery<>();
        query.addWhereEqualTo("School", SchoolName);
        query.addWhereEqualTo("Colleage", ColleageName);
        query.addWhereEqualTo("Major", MajorName);
        query.addWhereEqualTo("Grade", GradeName);

        query.findObjects(new FindListener<BookList>() {
            @Override
            public void done(List<BookList> list, BmobException e) {
                if (e == null) {
                    Toast.makeText(BookListAty.this, "查询成功", Toast.LENGTH_SHORT).show();
                    booklist=list.get(0).getBookMessage();
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(BookListAty.this, "网络错误，请重新刷新", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
