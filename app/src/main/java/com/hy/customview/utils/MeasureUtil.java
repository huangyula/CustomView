package com.hy.customview.utils;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by and on 2018-09-08.
 */

public class MeasureUtil {

        public static int[]getScreenSize(Activity activity)
        {
            int[]a=new int[2];
            WindowManager wm=activity.getWindowManager();
            DisplayMetrics displayMetrics=new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(displayMetrics);
            a[0]=displayMetrics.widthPixels;
            a[1]=displayMetrics.heightPixels;
            return a;
        }


}
