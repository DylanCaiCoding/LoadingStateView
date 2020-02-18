package com.dylanc.loadinghelper.sample;

import android.app.Application;

import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.ViewType;
import com.dylanc.loadinghelper.sample.adapter.EmptyAdapter;
import com.dylanc.loadinghelper.sample.adapter.ErrorAdapter;
import com.dylanc.loadinghelper.sample.adapter.LoadingAdapter;
import com.dylanc.loadinghelper.sample.adapter.TitleAdapter;

import kotlin.Unit;

/**
 * @author Dylan Cai
 * @since 2019/6/25
 */
public class App extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    LoadingHelper.setDefaultAdapterPool(adapterPool -> {
      adapterPool.register(ViewType.TITLE,new TitleAdapter());
      adapterPool.register(ViewType.LOADING, new LoadingAdapter());
      adapterPool.register(ViewType.ERROR, new ErrorAdapter());
      adapterPool.register(ViewType.EMPTY, new EmptyAdapter());
      return Unit.INSTANCE;
    });
  }
}
