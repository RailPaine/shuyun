package com.example.rail.shuyun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.rey.material.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rail on 2016/7/11.
 */
public class questionDetail extends AppCompatActivity {

    private ArrayList<HashMap<String,Object>>arrayList;
    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private View view;
    private LayoutInflater layoutInflater;
    private TextView name,ques,time;
    private ImageView userimg,questionimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questiondetail);
        Bundle bundle=this.getIntent().getExtras();
        int imgid=bundle.getInt("questionImage");
        String username=bundle.getString("questionPerson");
        String question=bundle.getString("question");
        String questiontime=bundle.getString("quesitonTime");
        layoutInflater=LayoutInflater.from(this);
        view=layoutInflater.inflate(R.layout.questiondetail_headerview, null);
        name=(TextView)view.findViewById(R.id.questiondetail_header_name);
        ques=(TextView)view.findViewById(R.id.questiondetail_header_question);
        time=(TextView)view.findViewById(R.id.questiondetail_header_time);
        userimg=(ImageView)view.findViewById(R.id.questiondetail_header_userimg);
        questionimg=(ImageView)view.findViewById(R.id.questiondetail_header_quesimg);
        name.setText(username);
        userimg.setImageResource(imgid);
        ques.setText(question);
        questionimg.setImageResource(R.drawable.b);
        time.setText(questiontime);
        listView=(ListView)findViewById(R.id.questiondetail_listview);
        arrayList=new ArrayList<HashMap<String, Object>>();
        simpleAdapter=new SimpleAdapter(this,arrayList,R.layout.questiondetail_item,
                new String[]{"questionPerson","questionImage","question","quesitonTime"},
                new int[]{R.id.questiondetail_item_name,R.id.questiondetail_item_userimg,R.id.questiondetail_item_answer,R.id.questiondetail_item_time});
        listView.addHeaderView(view);
        listView.setAdapter(simpleAdapter);

    }
}
