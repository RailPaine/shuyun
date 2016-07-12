package com.example.rail.shuyun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.rey.material.widget.ListView;

import java.util.ArrayList;

import listviewForShare.MyShareItemAdapter;
import listviewForShare.shareItem;

/**
 * Created by rail on 2016/7/7.
 */
public class resourceShare extends AppCompatActivity{

    private ArrayList<shareItem>list=new ArrayList<shareItem>();
    private ImageButton title_leftBtn;
    private Button title_rightBtn;
    private PullToRefreshListView listView;
    private MyShareItemAdapter myShareItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resourceshare);
        initView();
        initListener();
    }

    private void initView(){
        title_leftBtn=(ImageButton)findViewById(R.id.titlebar_done);
        title_rightBtn= (Button) findViewById(R.id.title_rightBtn);
        title_rightBtn.setVisibility(View.VISIBLE);
//        imageButton.setImageResource(R.drawable.ic_create_white_24dp);
        registerForContextMenu(title_rightBtn);
        listView=(PullToRefreshListView)findViewById(R.id.listview_resourceshare);
        myShareItemAdapter=new MyShareItemAdapter(this,list);

        listView.setAdapter(myShareItemAdapter);
    }
    private void initListener(){
        title_rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title_rightBtn.showContextMenu();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,View v,ContextMenu.ContextMenuInfo menuInfo)
    {
        menu.add(0, 1, 0, "文件（小于20M）");

        menu.setHeaderTitle("请选择你要分享的类型");
        SubMenu subMenu=menu.addSubMenu(0,2,1,"链接");
        subMenu.add(1, 3, 0, "试卷");
        subMenu.add(1, 4, 1, "电子书");
        subMenu.add(1, 5, 2, "课后习题答案");
        subMenu.add(1,6,3,"PPT");
        subMenu.setHeaderTitle("请选择你要分享链接的种类");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
    switch (item.getItemId())
    {
        case 1:
            Intent intent1=new Intent(resourceShare.this,uploadFile.class);
            startActivityForResult(intent1, 1);
            break;
        case 3:
            Intent intent2=new Intent(resourceShare.this,editShare.class);
            startActivityForResult(intent2,2);
            break;
        case 4:
            Intent intent3=new Intent(resourceShare.this,editShare.class);
            startActivityForResult(intent3,3);
            break;
        case 5:
            Intent intent4=new Intent(resourceShare.this,editShare.class);
            startActivityForResult(intent4,4);
            break;
        case 6:
            Intent intent5=new Intent(resourceShare.this,editShare.class);
            startActivityForResult(intent5,5);
            break;
        default:
            break;
    }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==1&&resultCode==RESULT_OK&&data!=null)
        {
            Bundle bundle1=data.getExtras();
            String filename=bundle1.getString("filename");
            String filepath=bundle1.getString("filepath");
            shareItem shareItem1=new shareItem(0,R.drawable.b,"Rail","分享了一个"+filename,filepath);
            list.add(shareItem1);
            myShareItemAdapter.notifyDataSetChanged();

        }
        else if (requestCode==2&&resultCode==RESULT_OK&&data!=null)
        {
            Bundle bundle2=data.getExtras();
            String webPath=bundle2.getString("webPath");

            shareItem shareItem1=new shareItem(1,R.drawable.b,"Rail","分享了一份试卷",webPath);
            list.add(shareItem1);
            myShareItemAdapter.notifyDataSetChanged();
        }
        else if (requestCode==3&&resultCode==RESULT_OK&&data!=null)
        {
            Bundle bundle2=data.getExtras();
            String webPath=bundle2.getString("webPath");

            shareItem shareItem1=new shareItem(1,R.drawable.b,"Rail","分享了一份电子书",webPath);
            list.add(shareItem1);
            myShareItemAdapter.notifyDataSetChanged();
        }
        else if (requestCode==4&&resultCode==RESULT_OK&&data!=null)
        {
            Bundle bundle2=data.getExtras();
            String webPath=bundle2.getString("webPath");

            shareItem shareItem1=new shareItem(1,R.drawable.b,"Rail","分享了一份课后习题答案",webPath);
            list.add(shareItem1);
            myShareItemAdapter.notifyDataSetChanged();
        }
        else if (requestCode==5&&resultCode==RESULT_OK&&data!=null)
        {
            Bundle bundle2=data.getExtras();
            String webPath=bundle2.getString("webPath");

            shareItem shareItem1=new shareItem(1,R.drawable.b,"Rail","分享了一份PPT",webPath);
            list.add(shareItem1);
            myShareItemAdapter.notifyDataSetChanged();
        }

    }


}
