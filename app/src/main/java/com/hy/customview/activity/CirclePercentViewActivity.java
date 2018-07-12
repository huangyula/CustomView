package com.hy.customview.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hy.customview.R;
import com.hy.customview.view.CirclePercentView;

import java.util.Random;

/**
 * Created by and on 2018-07-12.
 */

public class CirclePercentViewActivity extends AppCompatActivity {
    private CirclePercentView mCpView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circlepercentview);
        mCpView = (CirclePercentView) findViewById(R.id.cpView);
    }

    public void changePercent(View view){
        Random random=new Random();
        int percent=random.nextInt(101);
        mCpView.setPercent(percent);
    }

}
