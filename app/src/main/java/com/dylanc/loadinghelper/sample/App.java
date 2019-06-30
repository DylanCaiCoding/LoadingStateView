package com.dylanc.loadinghelper.sample;

import android.app.Application;
import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.ViewType;
import com.dylanc.loadinghelper.sample.adapter.EmptyAdapter;
import com.dylanc.loadinghelper.sample.adapter.ErrorAdapter;
import com.dylanc.loadinghelper.sample.adapter.LoadingAdapter;

/**
 * @author Dylan Cai
 * @since 2019/6/25
 */
public class App extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    LoadingHelper.getDefault()
        .register(ViewType.LOADING, new LoadingAdapter())
        .register(ViewType.ERROR, new ErrorAdapter())
        .register(ViewType.EMPTY, new EmptyAdapter());
  }
}
