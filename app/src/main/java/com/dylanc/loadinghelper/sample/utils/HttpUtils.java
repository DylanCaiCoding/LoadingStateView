package com.dylanc.loadinghelper.sample.utils;

import android.os.Handler;

import java.util.Random;

/**
 * @author Dylan Cai
 * @since 2019/6/25
 */
public class HttpUtils {

  public static void requestSuccess(final Callback callback){
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        if (callback!=null){
          callback.onSuccess();
        }
      }
    },2000);
  }

  public static void requestFailure(final Callback callback){
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        if (callback!=null){
          callback.onFailure();
        }
      }
    },2000);
  }

  public static String getRandomUrl(){
    int position = new Random().nextInt(100);
    return "https://source.unsplash.com/collection/"+position+"/1600x900";
  }


  public interface Callback{
    void onSuccess();

    void onFailure();
  }
}
