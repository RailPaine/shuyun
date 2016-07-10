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

import com.rey.material.widget.ListView;

import java.util.ArrayList;

import listviewForShare.MyShareItemAdapter;
import listviewForShare.shareItem;

/**
 * Created by rail on 2016/7/7.
 */
public class resourceShare extends AppCompatActivity{

    private ArrayList<shareItem>list=new ArrayList<shareItem>();
    private ImageButton imageButton;
    private ListView listView;
    private MyShareItemAdapter myShareItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resourceshare);
        imageButton=(ImageButton)findViewById(R.id.titlebar_done);
       imageButton.setImageResource(R.drawable.ic_create_white_24dp);
        registerForContextMenu(imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton.showContextMenu();
            }
        });

        listView=(ListView)findViewById(R.id.listview_resourceshare);
        myShareItemAdapter=new MyShareItemAdapter(this,list);

        listView.setAdapter(myShareItemAdapter);

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
    }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==1&&resultCode==RESULT_OK&&data!=null)
        {
            Bundle bundle1=data.getExtras();
            String filename=bundle1.getString("filename");
            System.out.println(filename);
            shareItem shareItem1=new shareItem(0,R.drawable.b,"Rail","分享了一个"+filename,"www.baidu.com");
            list.add(shareItem1);
            myShareItemAdapter.notifyDataSetChanged();
            Toast.makeText(this,"aaaaaa",Toast.LENGTH_LONG).show();
        }
        else
        {
            System.out.println("fail");
        }

    }


}
