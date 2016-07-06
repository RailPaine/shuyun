package recyclerview;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rail.shuyun.R;

import java.util.List;

/**
 * Created by rail on 2016/6/30.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> implements View.OnClickListener{

    public MyItemClickListener myItemClickListener;
    private Activity activity;
    private List<Item>list;

    public MyRecyclerAdapter(Activity act,List<Item>list)
    {
        this.activity=act;
        this.list=list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(activity).inflate(R.layout.recy_item,null);
        MyViewHolder holder=new MyViewHolder(v);
        v.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Item item=list.get(position);

        TextView tvbookname=(TextView)holder.itemView.findViewById(R.id.recy_book);
        tvbookname.setText(item.bookname);
        TextView tvauthor=(TextView)holder.itemView.findViewById(R.id.recy_author);
        tvauthor.setText(item.author);
        holder.itemView.setTag(position);
    }

    @Override
    public void onClick(View v) {
        if (myItemClickListener!=null)
        {
            myItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public void setOnItemClickListener(MyItemClickListener listener)
    {
        this.myItemClickListener=listener;
    }

    public interface MyItemClickListener{
        public void onItemClick(View view,int position);
    }
}
