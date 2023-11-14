package com.rockstreamer.iscreensdk.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class ScreenUtil {

    public static int convertDIPToPixels(Context context, int dip) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, displayMetrics);
    }
}
