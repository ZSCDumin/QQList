package com.zscdumin.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.listView)
    ListView listView;

    private int[] images = new int[1000];
    private String[] titles = new String[1000];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        for (int i = 0; i < 1000; i++) {
            images[i] = R.drawable.icon;
            titles[i] = "item" + i;
        }
        MyAdapter myAdapter = new MyAdapter(this, R.layout.list_item, images, titles);
        listView.setAdapter(myAdapter);
    }
}
