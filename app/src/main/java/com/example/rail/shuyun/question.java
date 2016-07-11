package com.example.rail.shuyun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFloat;
import com.rey.material.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rail on 2016/7/6.
 */
public class question extends AppCompatActivity {

    private ArrayList<HashMap<String,Object>>arrayList;
    private SimpleAdapter simpleAdapter;
    private ListView listView;
    private ImageView questionImage;
    private TextView questionPerson;
    private TextView question;
    private TextView quetionTime;
    private Toolbar toolbar;
    private ButtonFloat addQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        initView();
        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(question.this,editQuestion.class);
                startActivity(intent);
            }
        });
    }

    private void initView()
    {   addQuestion=(ButtonFloat)findViewById(R.id.question_float);
        toolbar=(Toolbar)findViewById(R.id.toolbar_question);
        toolbar.setTitle("问题解答");
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        arrayList=new ArrayList<HashMap<String, Object>>();
        listView=(ListView)findViewById(R.id.question_list);

        questionImage=(ImageView)findViewById(R.id.question_image);
        questionPerson=(TextView)findViewById(R.id.question_person);
        question=(TextView)findViewById(R.id.question);
        quetionTime=(TextView)findViewById(R.id.question_time);
        HashMap<String,Object>map1=new HashMap<String,Object>();
        map1.put("questionImage",R.drawable.b);
        map1.put("questionPerson","Rail");
        map1.put("question","我想知道泰勒展开怎么展？二阶求导怎么求？TCP协议的三次握手怎么握？IP协议的格式是什么？");
        map1.put("quetionTime", "1小时前");
        arrayList.add(map1);
        HashMap<String,Object>map2=new HashMap<String,Object>();
        map2.put("questionImage",R.drawable.b);
        map2.put("questionPerson","Rail");
        map2.put("question","我想知道泰勒展开怎么展？二阶求导怎么求？TCP协议的三次握手怎么握？IP协议的格式是什么？");
        map2.put("quetionTime","1小时前");
        arrayList.add(map2);
        simpleAdapter=new SimpleAdapter(this,arrayList,R.layout.question_item,new String[]{"questionImage","questionPerson","question","quetionTime"},
                new int[]{R.id.question_image,R.id.question_person,R.id.question,R.id.question_time});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(question.this,questionDetail.class);
                Bundle bundle=new Bundle();
                HashMap<String,Object>map=arrayList.get(position);
                int imgid=(int)map.get("questionImage");
                String username=(String)map.get("questionPerson");
                String question=(String)map.get("question");
                String questiontime=(String)map.get("quesitonTime");
                bundle.putInt("questionImage",imgid);
                bundle.putString("questionPerson", username);
                bundle.putString("question",question);
                bundle.putString("quesitonTime","一小时前");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
