/*
 * Copyright (c) 2019. Dylan Cai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dylanc.loadingstateview.sample.utils;

import android.os.Handler;

import java.util.Random;

/**
 * @author Dylan Cai
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
