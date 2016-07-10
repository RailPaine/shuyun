package com.example.rail.shuyun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonRectangle;

/**
 * Created by rail on 2016/7/8.
 */
public class uploadFile extends AppCompatActivity {

    private ImageButton done;
    private TextView filename_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadfile);
        done=(ImageButton)findViewById(R.id.titlebar_done);
        filename_tv=(TextView)findViewById(R.id.upload_filename);
        filename_tv.setText("计算机组成");
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("filename", filename_tv.getText().toString());

                Intent intent=new Intent(uploadFile.this,resourceShare.class);
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
               finish();
            }
        });
    }
}
