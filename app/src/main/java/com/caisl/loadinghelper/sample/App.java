package com.caisl.loadinghelper.sample;

import android.app.Application;
import com.caisl.loadinghelper.LoadingHelper;
import com.caisl.loadinghelper.sample.adapter.EmptyAdapter;
import com.caisl.loadinghelper.sample.adapter.ErrorAdapter;
import com.caisl.loadinghelper.sample.adapter.LoadingAdapter;
import com.caisl.loadinghelper.sample.adapter.SimpleContentAdapter;

import static com.caisl.loadinghelper.LoadingHelper.*;

/**
 * @author caisl
 * @since 2019/6/25
 */
public class App extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    LoadingHelper.getDefault()
        .registerAdapter(VIEW_TYPE_LOADING, new LoadingAdapter())
        .registerAdapter(VIEW_TYPE_ERROR, new ErrorAdapter())
        .registerAdapter(VIEW_TYPE_EMPTY, new EmptyAdapter());
  }
}
