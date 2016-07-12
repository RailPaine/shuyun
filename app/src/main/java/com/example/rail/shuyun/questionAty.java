package com.example.rail.shuyun;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rail.shuyun.adapter.QuestionAdapter;
import com.example.rail.shuyun.adapter.SocialShareAdapter;
import com.example.rail.shuyun.detail.SocialShareDetailAty;
import com.example.rail.shuyun.entity.BookMessageDetail;
import com.example.rail.shuyun.entity.Post;
import com.example.rail.shuyun.entity.Question;
import com.example.rail.shuyun.entity.TotalPost;
import com.example.rail.shuyun.write.WriteQuestionAty;
import com.example.rail.shuyun.write.WriteSocialAty;
import com.gc.materialdesign.views.ButtonFloat;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rey.material.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by rail on 2016/7/6.
 */
public class questionAty extends Activity {

    private List<Question> Postlist = new ArrayList<>();
    private PullToRefreshListView listView;
    private QuestionAdapter adapter;

    private ImageButton title_leftBtn;
    private Button title_RightBtn;

    private Bundle bundle;
    private String bookId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sosial_share);
        initView();
        initListener();
        bundle = getIntent().getExtras();
        bookId = bundle.getString("bookId");
        initData();
    }

    private void initView() {
        title_leftBtn = (ImageButton) findViewById(R.id.title_leftImageBtn);
        title_RightBtn= (Button) findViewById(R.id.title_rightBtn);
        title_RightBtn.setVisibility(View.VISIBLE);
        listView = (PullToRefreshListView) findViewById(R.id.aty_sosial_listView);
    }

    private void initListener() {
        title_leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(questionAty.this,bookDetailAty.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        title_RightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(questionAty.this, WriteQuestionAty.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<android.widget.ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<android.widget.ListView> refreshView) {
                initData();
            }
        });
        listView.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(questionAty.this, questionDetailAty.class);
                Question post = Postlist.get(position - 1);
                Log.i("TestSocialShareAty", "现在点击的位置是:" + post.getContent());
                Bundle bundle = new Bundle();
                bundle.putString("bookId", bookId);
                bundle.putString("ID", post.getObjectId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        listView.getRefreshableView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ShowAlertDialogDelete(position - 1);
                Log.i("TestSocialShareAty","你点击的是:"+(position-1));
                return true;
            }
        });
    }

    private void initData() {

        BookMessageDetail bookMessageDetail = new BookMessageDetail();
        bookMessageDetail.setObjectId(bookId);

        BmobQuery<Question> query = new BmobQuery<>();
        query.addWhereEqualTo("book", new BmobPointer(bookMessageDetail));
        query.include("Author");
        query.order("-createdAt");
        query.findObjects(new FindListener<Question>() {
            @Override
            public void done(List<Question> list, BmobException e) {
                if (e == null) {
                    Log.i("TestSocialShareAty", "查询所有社交分享内容成功-----" + list.size());
                    Postlist = list;
                    adapter=new QuestionAdapter(questionAty.this,Postlist,handler);
                    listView.setAdapter(adapter);
                    listView.onRefreshComplete();
//                    handler.sendEmptyMessage(0);
                } else {
                    Log.i("TestSocialShareAty", "查询失败");
                }
            }
        });

    }
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
//            if(msg.what==5){
//                Person user= BmobUser.getCurrentUser(Person.class);
//                Post post=new Post();
//                post.setObjectId((String) msg.obj);
//
//            }
            return false;
        }
    });
    private void ShowAlertDialogDelete(final int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setMessage("您真的要删除这条信息吗?").setPositiveButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                SendToDelete(position);
            }
        }).create();
        alertDialog.show();
    }
    private void SendToDelete(final int position) {
        Question post=Postlist.get(position);
        post.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(questionAty.this, "删除成功", Toast.LENGTH_SHORT).show();
                    Postlist.remove(position);
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(questionAty.this,"删除失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
