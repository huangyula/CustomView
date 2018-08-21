package com.hy.customview.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hy.customview.R;
import com.hy.customview.view.SuccessView;

public class LoadSuccessViewActivity extends AppCompatActivity {
    private SuccessView mSuccessView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_success);


    }

    public void successDialog(View v){
        new AlertDialog.Builder(this).setView(R.layout.layout).setNegativeButton("OK",null).create().show();
    }

    public void loadingDialog(View v) {
        View view = View.inflate(this, R.layout.view_dialog, null);
        AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();
        dialog.getWindow().setLayout(dip2px(400), dip2px(200));
        dialog.show();
    }



    public int dip2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
