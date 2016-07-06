package com.example.rail.shuyun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import recyclerview.Item;
import recyclerview.MyRecyclerAdapter;

/**
 * Created by rail on 2016/7/5.
 */
public class bookList extends AppCompatActivity {

    private List<Item>booklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("书籍列表");
        setContentView(R.layout.activity_booklist);
        initView();
    }

    private void initView()
    {
        booklist=new ArrayList<Item>();
        booklist.add(new Item("计算机组成","翁灿标"));
        booklist.add(new Item("数据库原理", "翁灿标"));
        booklist.add(new Item("数据结构", "翁灿标"));

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.booklist_recy);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        MyRecyclerAdapter myRecyclerAdapter=new MyRecyclerAdapter(this,booklist);
        myRecyclerAdapter.setOnItemClickListener(new MyRecyclerAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Item item=booklist.get(position);
                String bookname=item.bookname;
                String author=item.author;
                Bundle bundle=new Bundle();
                bundle.putString("bookname",bookname);
                bundle.putString("author",author);
                Intent intent=new Intent(bookList.this,bookDetail.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(myRecyclerAdapter);
    }
}
