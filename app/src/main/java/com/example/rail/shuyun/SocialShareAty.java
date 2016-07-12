package com.example.rail.shuyun;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rail.shuyun.adapter.SocialShareAdapter;
import com.example.rail.shuyun.detail.SocialShareDetailAty;
import com.example.rail.shuyun.entity.BookMessageDetail;
import com.example.rail.shuyun.entity.Comment;
import com.example.rail.shuyun.entity.Person;
import com.example.rail.shuyun.entity.Post;
import com.example.rail.shuyun.entity.TotalPost;
import com.example.rail.shuyun.write.WriteSocialAty;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by qq on 2016/7/12.
 */
public class SocialShareAty extends Activity {


    private List<Post> Postlist = new ArrayList<>();
    private PullToRefreshListView listView;
    private SocialShareAdapter adapter;

    private ImageButton title_leftBtn;
    private Button title_RightBtn;

    private Bundle bundle;
    private String bookId;

    private List<TotalPost> TotalPostlist = new ArrayList<>();


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
                Intent intent=new Intent(SocialShareAty.this,bookDetailAty.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        title_RightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SocialShareAty.this, WriteSocialAty.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                initData();
            }
        });
        listView.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SocialShareAty.this, SocialShareDetailAty.class);
                Post post = Postlist.get(position - 1);
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

        BmobQuery<Post> query = new BmobQuery<>();
        query.addWhereEqualTo("book", new BmobPointer(bookMessageDetail));
        query.include("Author");
        query.order("-createdAt");
        query.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                if (e == null) {
                    Log.i("TestSocialShareAty", "查询所有社交分享内容成功-----" + list.size());
                    Postlist = list;
                    adapter=new SocialShareAdapter(SocialShareAty.this,Postlist,handler);
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
      Post post=Postlist.get(position);
        post.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(SocialShareAty.this,"删除成功",Toast.LENGTH_SHORT).show();
                    Postlist.remove(position);
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(SocialShareAty.this,"删除失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
