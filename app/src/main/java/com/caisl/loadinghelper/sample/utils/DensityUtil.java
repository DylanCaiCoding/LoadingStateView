package com.caisl.loadinghelper.sample.utils;

import android.content.Context;

public class DensityUtil {

  public static float dip2px(Context context, float dpValue) {
    float scale = context.getResources().getDisplayMetrics().density;
    return dpValue * scale;
  }
}