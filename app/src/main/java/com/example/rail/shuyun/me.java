package com.example.rail.shuyun;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import recyclerview.Item;

/**
 * Created by rail on 2016/6/30.
 */
public class me extends AppCompatActivity {
    private List<Item>itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
    }
}
