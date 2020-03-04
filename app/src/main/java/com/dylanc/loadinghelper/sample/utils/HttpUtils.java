package com.dylanc.loadinghelper.sample.utils;

import android.os.Handler;

import java.util.Random;

/**
 * @author Dylan Cai
 * @since 2019/6/25
 */
public class HttpUtils {

  /**
   * 模拟请求，两秒后回调请求成功方法
   */
  public static void requestSuccess(final Callback callback){
    new Handler().postDelayed(() -> {
      if (callback!=null){
        callback.onSuccess();
      }
    },2000);
  }

  /**
   * 模拟请求，两秒后回调请求失败方法
   */
  public static void requestFailure(final Callback callback){
    new Handler().postDelayed(() -> {
      if (callback!=null){
        callback.onFailure();
      }
    },2000);
  }

  public static String getRandomImageUrl(){
    int position = new Random().nextInt(100);
    return "https://source.unsplash.com/collection/"+position+"/1600x900";
  }

  public interface Callback{
    void onSuccess();

    void onFailure();
  }
}
