package com.example.rail.shuyun.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rail.shuyun.R;

import java.util.List;

import recyclerview.Item;

/**
 * Created by qq on 2016/7/12.
 */
public class BookListAdapter extends BaseAdapter{

    private Context context;
    private List<Item> list;

    public BookListAdapter(Context context,List<Item> list){
        this.context=context;
        this.list=list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView == null) {
            viewHolder=new ViewHolder();
            convertView = View.inflate(context, R.layout.item_booklist, null);
            viewHolder.bookName= (TextView) convertView.findViewById(R.id.item_booklist_bookName);
            viewHolder.bookAuthor= (TextView) convertView.findViewById(R.id.item_booklist_bookAuthor);
            viewHolder.bookNumber= (TextView) convertView.findViewById(R.id.item_booklist_number);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bookName.setText(list.get(position).getBookname());
        viewHolder.bookAuthor.setText(list.get(position).getAuthor());
        viewHolder.bookNumber.setText((position+1)+".");
        return convertView;
    }

    private static class ViewHolder{
        TextView bookName;
        TextView bookAuthor;
        TextView bookNumber;
    }
}
