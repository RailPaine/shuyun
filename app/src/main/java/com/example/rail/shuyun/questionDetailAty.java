package com.example.rail.shuyun;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rail.shuyun.adapter.DetailCommentAdapter;
import com.example.rail.shuyun.entity.Comment;
import com.example.rail.shuyun.entity.Person;
import com.example.rail.shuyun.entity.Post;
import com.example.rail.shuyun.entity.Question;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.rey.material.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by rail on 2016/7/11.
 */
public class questionDetailAty extends AppCompatActivity {


    private PullToRefreshListView listView;
    private View headerView;
    private ImageView userImage;
    private TextView userName, postContent, postTime;

    private String PostId;

    private DetailCommentAdapter adapter;
    private List<Comment> CommentList = new ArrayList<>();

    private ImageLoader imageLoader;
    private Bundle bundle;

    private ImageButton title_leftBtn;

    private EditText commentContent;
    private Button commentBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_detail);
        bundle=getIntent().getExtras();
        PostId=bundle.getString("ID");
        initView();
        initListener();
        initData();
    }
    private void initView(){
        headerView=View.inflate(this,R.layout.item_question,null);
        userImage= (ImageView) headerView.findViewById(R.id.social_userImage);
        userName= (TextView) headerView.findViewById(R.id.social_userName);
        postContent= (TextView) headerView.findViewById(R.id.social_content);
        postTime= (TextView) headerView.findViewById(R.id.social_time);

        imageLoader = ImageLoader.getInstance();

        listView= (PullToRefreshListView) findViewById(R.id.detail_socail_listView);
        android.widget.ListView listView1=listView.getRefreshableView();
        listView1.addHeaderView(headerView);

        title_leftBtn= (ImageButton) findViewById(R.id.title_leftImageBtn);

        commentContent= (EditText) findViewById(R.id.detail_socail_editText);
        commentBtn= (Button) findViewById(R.id.detail_socail_btn);

    }

    private void initListener(){
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<android.widget.ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<android.widget.ListView> refreshView) {
                initData();
            }
        });
        title_leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(questionDetailAty.this, questionAty.class);
                intent.putExtras(bundle);
                startActivity(intent);
                questionDetailAty.this.finish();
            }
        });
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(commentContent.getText().toString())){
                    final Comment comment=new Comment();
                    comment.setContent(commentContent.getText().toString());

                    Question post=new Question();
                    post.setObjectId(PostId);
                    comment.setQuestion(post);

                    Person person= BmobUser.getCurrentUser(Person.class);
                    comment.setAuthor(person);

                    comment.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                Toast.makeText(questionDetailAty.this, "评论成功", Toast.LENGTH_SHORT).show();
                                CommentList.add(comment);
                                adapter.notifyDataSetChanged();
                                commentContent.setText("");
//                                initData();
                            }
                        }
                    });
                }
            }
        });
    }

    private void initData(){
        BmobQuery<Comment> bmobQuery=new BmobQuery<>();
        Question post=new Question();
        post.setObjectId(PostId);

        bmobQuery.addWhereEqualTo("question", new BmobPointer(post));
        bmobQuery.include("author,question.Author");
        bmobQuery.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if (e == null) {
                    CommentList = list;
                    adapter = new DetailCommentAdapter(questionDetailAty.this, CommentList, handler);
                    listView.setAdapter(adapter);
                    setData();
                }
            }
        });
    }
    private void setData(){
        if(CommentList.size()>0) {
            Question post = CommentList.get(0).getQuestion();
            imageLoader.displayImage(post.getAuthor().getImageUrl().getFileUrl(), userImage);
            userName.setText(post.getAuthor().getUserName());
            postContent.setText(post.getContent());
            postTime.setText(post.getCreatedAt());
        }else{
            BmobQuery<Question> bmobQuery=new BmobQuery<>();
            bmobQuery.include("Author");
            bmobQuery.getObject(PostId, new QueryListener<Question>() {
                @Override
                public void done(Question post, BmobException e) {
                    userName.setText(post.getAuthor().getUserName());
                    imageLoader.displayImage(post.getAuthor().getImageUrl().getFileUrl(), userImage);
                    postContent.setText(post.getContent());
                    postTime.setText(post.getCreatedAt());
                }
            });
        }
        listView.onRefreshComplete();
    }

    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what==-100){
                ShowAlertDialogDelete(msg.arg1);
            }
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
                Log.i("TestSocialShareAty", "你点击的是:" + position);
                SendToDelete(position);
            }
        }).create();
        alertDialog.show();
    }
    private void SendToDelete(final int position) {
        Comment comment=CommentList.get(position);
        comment.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(questionDetailAty.this, "删除成功", Toast.LENGTH_SHORT).show();
                    CommentList.remove(position);
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(questionDetailAty.this,"删除失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
