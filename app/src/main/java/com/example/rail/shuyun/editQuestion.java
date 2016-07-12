package com.example.rail.shuyun;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by rail on 2016/7/6.
 */
public class editQuestion extends AppCompatActivity {

    private ImageView imageView;
    private ImageButton imageButton;
    private ImageButton back;
    private ImageButton done;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editquestion);
        back=(ImageButton)findViewById(R.id.back_titlebar);
        done=(ImageButton)findViewById(R.id.titlebar_done);
        title=(TextView)findViewById(R.id.titlebar_title);
        title.setText("创建问题");
        imageButton=(ImageButton)findViewById(R.id.editquestion_addphoto);
        imageView=(ImageView)findViewById(R.id.editquestion_photo);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MaterialDialog mMaterialDialog = new MaterialDialog(editQuestion.this)
                        .setTitle("提示")
                        .setMessage("退出创建问题？")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(editQuestion.this,questionAty.class);
                                startActivity(intent);

                            }
                        });
                mMaterialDialog.setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });

                mMaterialDialog.show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK&&data!=null)
        {
            Uri selectedImage=data.getData();
            String[]filePath={MediaStore.Images.Media.DATA};
            Cursor cursor=getContentResolver().query(selectedImage,filePath,null,null,null);
            cursor.moveToFirst();
            int colunmIndex=cursor.getColumnIndex(filePath[0]);
            String piturePath=cursor.getString(colunmIndex);
            cursor.close();
            imageView.setImageBitmap(BitmapFactory.decodeFile(piturePath));
        }
    }
}
