package com.example.rail.shuyun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by rail on 2016/7/10.
 */
public class editShare extends AppCompatActivity {
    private ImageButton done;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editshare);
        editText=(EditText)findViewById(R.id.editshare_ev);
        done=(ImageButton)findViewById(R.id.titlebar_done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("webPath", editText.getText().toString());

                Intent intent=new Intent(editShare.this,resourceShare.class);
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
