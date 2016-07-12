package com.example.rail.shuyun;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.example.rail.shuyun.entity.BookList;
import com.gc.materialdesign.views.ButtonRectangle;
import com.rey.material.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private android.support.v7.widget.Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ListView listView;
    public ArrayList<HashMap<String, Object>> arrayList;
    private SimpleAdapter simpleAdapter;

    //底部的两个按钮
    private ButtonRectangle sureBtn,backBtn;

    //几个下拉框
    private Spinner School,College,Major,Grade;
    private String SchoolName,ColleageName,MajorName,GradeName;
    public final  String[] TotalSchool={"华南理工大学","中山大学","广东工业大学","广东外语外贸大学","广州美术学院"};
    public final  String[] TotalCollege={"软件学院","法学院","计算机学院"};
    public final  String[] TotalMajor={"软件工程","计算机科学","法学"};
    public final  String[] TotalGrade={"大一","大二","大三","大四"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        toolbar.setTitle("选择你要的书籍");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        actionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        ButtonRectangle sure = (ButtonRectangle) findViewById(R.id.sure_main);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookListAty.class);
                startActivity(intent);
            }
        });
        //两个控件初始化
        initView();
    }
    private void initView(){
        School= (Spinner) findViewById(R.id.spinner_university_main);
        College= (Spinner) findViewById(R.id.spinner_school_main);
        Major= (Spinner) findViewById(R.id.spinner_major_main);
        Grade= (Spinner) findViewById(R.id.spinner_grade_main);

        School.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SchoolName = TotalSchool[((int) id)];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                SchoolName=TotalSchool[0];
            }
        });
        College.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ColleageName = TotalCollege[((int) id)];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ColleageName=TotalCollege[0];
            }
        });
        Major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MajorName = TotalMajor[((int) id)];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                MajorName = TotalMajor[0];
            }
        });

        Grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GradeName = TotalGrade[((int) id)];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                GradeName = TotalGrade[0];
            }
        });
        sureBtn= (ButtonRectangle) findViewById(R.id.sure_main);
        backBtn= (ButtonRectangle) findViewById(R.id.back_main);
        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookListAty.class);
                Bundle bundle=new Bundle();
                bundle.putString("School", SchoolName);
                bundle.putString("Colleage", ColleageName);
                bundle.putString("Major", MajorName);
                bundle.putString("Grade", GradeName);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        });

    }



    private void findView() {
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        listView = (ListView) findViewById(R.id.list_drawer);
        arrayList = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("title", "我的分享");
        map1.put("image", R.drawable.ic_share_black_24dp);
        arrayList.add(map1);
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put("title", "我的问题");
        map2.put("image", R.drawable.ic_question_answer_black_24dp);
        arrayList.add(map2);
        HashMap<String, Object> map3 = new HashMap<String, Object>();
        map3.put("title", "我的资源分享");
        map3.put("image", R.drawable.ic_content_paste_black_24dp);
        arrayList.add(map3);
        HashMap<String, Object> map4 = new HashMap<String, Object>();
        map4.put("title", "我的下载");
        map4.put("image", R.drawable.ic_cloud_download_black_24dp);
        arrayList.add(map4);
        HashMap<String, Object> map5 = new HashMap<String, Object>();
        map5.put("title", "我的评论");
        map5.put("image", R.drawable.ic_insert_comment_black_24dp);
        arrayList.add(map5);
        HashMap<String, Object> map6 = new HashMap<String, Object>();
        map6.put("title", "个人信息");
        map6.put("image", R.drawable.ic_info_outline_black_24dp);
        arrayList.add(map6);
        HashMap<String, Object> map7 = new HashMap<String, Object>();
        map7.put("title", "设置");
        map7.put("image", R.drawable.ic_settings_black_24dp);
        arrayList.add(map7);
        simpleAdapter = new SimpleAdapter(this, arrayList, R.layout.list_item, new String[]{"image", "title"}, new int[]{R.id.list_iv, R.id.list_tv});

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        AbsListView.LayoutParams ap = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 250);
        View headerView = layoutInflater.inflate(R.layout.drawer_headerview, null);
        headerView.setLayoutParams(ap);
        listView.addHeaderView(headerView);
        listView.setAdapter(simpleAdapter);
    }
}
