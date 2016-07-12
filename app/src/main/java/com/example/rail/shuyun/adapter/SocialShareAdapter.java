package com.example.rail.shuyun.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rail.shuyun.R;
import com.example.rail.shuyun.entity.Post;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by qq on 2016/7/12.
 */
public class SocialShareAdapter extends BaseAdapter {

    private Context context;
    private List<Post> list;
    private ImageLoader imageLoader;
    private Handler handler;

    public SocialShareAdapter(Context context, List<Post> list,Handler handler) {
        this.context = context;
        this.list = list;
        this.imageLoader = ImageLoader.getInstance();
        this.handler=handler;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Viewholder viewholder = null;
        if (convertView == null) {
            viewholder = new Viewholder();
            convertView = View.inflate(context, R.layout.item_socialshare, null);
            viewholder.userImage= (ImageView) convertView.findViewById(R.id.social_userImage);
            viewholder.postImage= (ImageView) convertView.findViewById(R.id.social_postImage);
            viewholder.userName= (TextView) convertView.findViewById(R.id.social_userName);
            viewholder.postContent= (TextView) convertView.findViewById(R.id.social_content);
            viewholder.postTime= (TextView) convertView.findViewById(R.id.social_time);
            viewholder.likeBtn= (Button) convertView.findViewById(R.id.social_like);
            convertView.setTag(viewholder);
        } else {
            viewholder = (Viewholder) convertView.getTag();
        }
        String userImage=list.get(position).getAuthor().getImageUrl().getFileUrl();
        String postImage=list.get(position).getPostImage().getFileUrl();
        imageLoader.displayImage(userImage, viewholder.userImage);
        imageLoader.displayImage(postImage,viewholder.postImage);

        viewholder.userName.setText(list.get(position).getAuthor().getUserName());
        viewholder.postContent.setText(list.get(position).getContent());
        viewholder.postTime.setText(list.get(position).getCreatedAt());
//        viewholder.likeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Message message=new Message();
//                message.what=5;
//                message.obj= list.get(position).getObjectId();
//                handler.sendMessage(message);
//            }
//        });


        return convertView;
    }

    public static class Viewholder {
        ImageView userImage, postImage;
        TextView userName, postContent, postTime;
        Button likeBtn;
    }

}
