package com.hy.customview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hy.customview.R;
import com.hy.customview.view.CircleView;
import com.hy.customview.view.CustomView;

/**
 * Created by and on 2018-07-12.
 */

public class CustomViewViewActivity extends AppCompatActivity {

    private CustomView mCustomView;
    private CircleView mCircleView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custonview);
        mCircleView=(CircleView)findViewById(R.id.circleView);
        mCircleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCircleView.change();
            }
        });

//        mCustomView = (CustomView) findViewById(R.id.customView);
//
//        //开子线程
//        new Thread(mCustomView).start();

//        new Thread(new Runnable() {//传入Runnable对象
//            @Override
//            public void run() {
//
//            }
//        }).start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mCustomView.stopView();
    }
}
