package com.hy.customview.activity;

import android.os.Bundle;
import android.widget.SeekBar;

import com.hy.customview.R;
import com.hy.customview.view.HyWeChatEye;

/**
 * Created by and on 2018-08-27.
 */

public class WeChatEyeActivity extends BaseActivity {

    private SeekBar mSeekBar;
    private HyWeChatEye mWeChatEye;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechateye);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        mWeChatEye = (HyWeChatEye) findViewById(R.id.weChat);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mWeChatEye.setPercent(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
