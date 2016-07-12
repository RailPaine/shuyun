package com.example.rail.shuyun.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rail.shuyun.R;
import com.example.rail.shuyun.entity.Comment;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by qq on 2016/4/23.
 */
public class DetailCommentAdapter extends BaseAdapter {

    private Context context;
    private List<Comment> list;
    private ImageLoader imageLoader;
    private Handler handler;

    public DetailCommentAdapter(Context context, List<Comment> list, Handler handler) {
        this.context = context;
        this.list = list;
        imageLoader = ImageLoader.getInstance();
        this.handler = handler;
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

        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_son_comment, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.item_sonComment_imageView);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.item_sonComment_userName);
            viewHolder.time = (TextView) convertView.findViewById(R.id.item_sonComment_time);
            viewHolder.delete = (Button) convertView.findViewById(R.id.item_sonComment_button);
            viewHolder.content = (TextView) convertView.findViewById(R.id.item_sonComment_content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(!TextUtils.isEmpty(list.get(position).getContent())) {
            imageLoader.displayImage(list.get(position).getAuthor().getImageUrl().getFileUrl(), viewHolder.imageView);
            viewHolder.userName.setText(list.get(position).getAuthor().getUserName());
            viewHolder.time.setText(list.get(position).getCreatedAt());
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Message message = new Message();
                    message.what = -100;
                    message.arg1 = position;
                    handler.sendMessage(message);
                }
            });
            viewHolder.content.setText(list.get(position).getContent());
        }
        return convertView;
    }

    public class ViewHolder {
        private ImageView imageView;
        private TextView userName;
        private TextView time;
        private Button delete;
        private TextView content;
    }
}
