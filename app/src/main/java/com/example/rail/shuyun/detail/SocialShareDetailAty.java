package com.example.rail.shuyun.detail;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rail.shuyun.R;
import com.example.rail.shuyun.SocialShareAty;
import com.example.rail.shuyun.adapter.DetailCommentAdapter;
import com.example.rail.shuyun.entity.BookMessageDetail;
import com.example.rail.shuyun.entity.Comment;
import com.example.rail.shuyun.entity.Person;
import com.example.rail.shuyun.entity.Post;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
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
 * Created by qq on 2016/7/12.
 */
public class SocialShareDetailAty extends Activity {


    private PullToRefreshListView listView;
    private View headerView;
    private ImageView userImage, postImage;
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
        headerView=View.inflate(this,R.layout.item_socialshare,null);
        userImage= (ImageView) headerView.findViewById(R.id.social_userImage);
        postImage= (ImageView) headerView.findViewById(R.id.social_postImage);
        userName= (TextView) headerView.findViewById(R.id.social_userName);
        postContent= (TextView) headerView.findViewById(R.id.social_content);
        postTime= (TextView) headerView.findViewById(R.id.social_time);

        imageLoader = ImageLoader.getInstance();

        listView= (PullToRefreshListView) findViewById(R.id.detail_socail_listView);
        ListView listView1=listView.getRefreshableView();
        listView1.addHeaderView(headerView);

        title_leftBtn= (ImageButton) findViewById(R.id.title_leftImageBtn);

        commentContent= (EditText) findViewById(R.id.detail_socail_editText);
        commentBtn= (Button) findViewById(R.id.detail_socail_btn);

    }

    private void initListener(){
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                initData();
            }
        });
        title_leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SocialShareDetailAty.this, SocialShareAty.class);
                intent.putExtras(bundle);
                startActivity(intent);
                SocialShareDetailAty.this.finish();
            }
        });
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(commentContent.getText().toString())){
                    final Comment comment=new Comment();
                    comment.setContent(commentContent.getText().toString());

                    Post post=new Post();
                    post.setObjectId(PostId);
                    comment.setPost(post);

                    Person person= BmobUser.getCurrentUser(Person.class);
                    comment.setAuthor(person);

                    comment.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                Toast.makeText(SocialShareDetailAty.this,"评论成功",Toast.LENGTH_SHORT).show();
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
        Post post=new Post();
        post.setObjectId(PostId);

        bmobQuery.addWhereEqualTo("post", new BmobPointer(post));
        bmobQuery.include("author,post.Author");
        bmobQuery.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if (e == null) {
                    CommentList = list;
                    adapter = new DetailCommentAdapter(SocialShareDetailAty.this, CommentList, handler);
                    listView.setAdapter(adapter);
                    setData();
                }
            }
        });
    }
    private void setData(){
        if(CommentList.size()>0) {
            Post post = CommentList.get(0).getPost();
            imageLoader.displayImage(post.getPostImage().getFileUrl(), userImage);
            imageLoader.displayImage(post.getPostImage().getFileUrl(), postImage);
            userName.setText(post.getAuthor().getUserName());
            postContent.setText(post.getContent());
            postTime.setText(post.getCreatedAt());
        }else{
            BmobQuery<Post> bmobQuery=new BmobQuery<>();
            bmobQuery.include("Author");
            bmobQuery.getObject(PostId, new QueryListener<Post>() {
                @Override
                public void done(Post post, BmobException e) {
                    imageLoader.displayImage(post.getPostImage().getFileUrl(), userImage);
                    imageLoader.displayImage(post.getPostImage().getFileUrl(), postImage);
                    userName.setText(post.getAuthor().getUserName());
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
                    Toast.makeText(SocialShareDetailAty.this, "删除成功", Toast.LENGTH_SHORT).show();
                    CommentList.remove(position);
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(SocialShareDetailAty.this,"删除失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
