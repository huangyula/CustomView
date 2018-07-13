package com.hy.customview;

import android.os.Bundle;
import android.view.View;

import com.hy.customview.activity.BaseActivity;
import com.hy.customview.activity.CirclePercentViewActivity;
import com.hy.customview.activity.PopupViewActivity;
import com.hy.customview.activity.SimpleLineChartActivity;
import com.hy.customview.activity.ValidViewActivity;
import com.hy.customview.activity.WaveViewActivity;

public class MainActivity extends BaseActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void onValidCodeView(View view){
		gotoActivity(ValidViewActivity.class);
	}

	public void onPopupView(View view){
		gotoActivity(PopupViewActivity.class);
	}

	public void onWaveView(View view){
		gotoActivity(WaveViewActivity.class);
	}

	public void onCirclePercentView(View view){
		gotoActivity(CirclePercentViewActivity.class);
	}


	public void onSimpleLineChart(View view){
		gotoActivity(SimpleLineChartActivity.class);
	}



}
