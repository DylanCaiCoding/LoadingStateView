package com.caisl.loadinghelper.sample

import android.app.Application
import com.caisl.loadinghelper.LoadingHelper
import com.caisl.loadinghelper.LoadingHelper.*
import com.caisl.loadinghelper.sample.lce.title.TitleAdapter
import com.caisl.loadinghelper.sample.lce.empty.EmptyAdapter
import com.caisl.loadinghelper.sample.lce.error.ErrorAdapter
import com.caisl.loadinghelper.sample.lce.loading.LoadingAdapter
import com.caisl.loadinghelper.sample.lce.time_out.TimeoutAdapter
import com.caisl.loadinghelper.sample.lce.time_out.TimeoutConfig.Companion.VIEW_TYPE_TIME_OUT

/**
 * @author caisl
 * @since 2019/6/14
 */
class App : Application() {
  override fun onCreate() {
    super.onCreate()
    LoadingHelper.getDefault()
      .registerAdapter(VIEW_TYPE_TITLE, TitleAdapter())
      .registerAdapter(VIEW_TYPE_LOADING, LoadingAdapter())
      .registerAdapter(VIEW_TYPE_ERROR, ErrorAdapter())
      .registerAdapter(VIEW_TYPE_EMPTY, EmptyAdapter())
      .registerAdapter(VIEW_TYPE_TIME_OUT, TimeoutAdapter())
  }
}