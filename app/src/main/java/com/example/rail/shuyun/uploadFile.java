package com.example.rail.shuyun;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.gc.materialdesign.views.ProgressBarDeterminate;
import com.rey.material.widget.ProgressView;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;
import util.FileUtils;

/**
 * Created by rail on 2016/7/8.
 */
public class uploadFile extends AppCompatActivity {

    private Button done;
    private TextView filename_tv,progress_tv;
    private ButtonRectangle choosefile,upload;
    private String filePath,filename;
    private File file;
    private ProgressBarDeterminate progressView;
    private String webPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadfile);
        initView();
        setlistener();
    }

    public void initView()
    {
        done=(Button)findViewById(R.id.title_rightBtn);
        done.setVisibility(View.VISIBLE);
        done.setBackgroundResource(R.drawable.ic_done_white_24dp);
        filename_tv=(TextView)findViewById(R.id.upload_filename);
        filename_tv.setAutoLinkMask(Linkify.WEB_URLS);
        filename_tv.setMovementMethod(LinkMovementMethod.getInstance());
        choosefile=(ButtonRectangle)findViewById(R.id.choosefile);
        upload=(ButtonRectangle)findViewById(R.id.upload);
        progressView=(ProgressBarDeterminate)findViewById(R.id.progress_upload);
        progress_tv=(TextView)findViewById(R.id.prgress_tv);
        progressView.setVisibility(View.GONE);
        upload.setVisibility(View.GONE);
    }

    public void setlistener()
    {
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("filename", filename);
                bundle.putString("filepath",webPath);
                Intent intent=new Intent(uploadFile.this,resourceShare.class);
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        choosefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                try {
                    startActivityForResult(Intent.createChooser(intent,"请选择一个文件"),1);
                }
                catch (android.content.ActivityNotFoundException e)
                {
                    Toast.makeText(uploadFile.this,"请安装文件管理器",Toast.LENGTH_LONG).show();
                }
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               progressView.setVisibility(View.VISIBLE);

                final BmobFile bmobfile=new BmobFile(file);
                bmobfile.uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null) {
                            Toast.makeText(uploadFile.this, "上传成功", Toast.LENGTH_LONG).show();
                            filename_tv.setText(bmobfile.getFileUrl());
                            webPath=bmobfile.getFileUrl();
                        }
                        else
                        {
                            Toast.makeText(uploadFile.this, "上传失败", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onProgress(Integer value) {
                       int progress=value.intValue();
                        progressView.setProgress(progress);
                        progress_tv.setText("已上传"+progress+"%");
                        if(progress==100) {

                            progressView.setVisibility(View.GONE);
                        }
                        super.onProgress(value);
                    }
                });

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==1&&resultCode==RESULT_OK&&data!=null)
        {
            Uri uri=data.getData();
            filePath=null;
            try {
                filePath= FileUtils.getPath(this,uri);
                file=new File(filePath);
                filename=file.getName();
                filename_tv.setText(filename);
                upload.setVisibility(View.VISIBLE);
        }
            catch (android.content.ActivityNotFoundException e)
            {
                Toast.makeText(uploadFile.this,"请先安装文件管理器",Toast.LENGTH_LONG).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
