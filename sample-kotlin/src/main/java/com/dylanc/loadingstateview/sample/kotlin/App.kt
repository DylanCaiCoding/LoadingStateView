package com.dylanc.loadingstateview.sample.kotlin

import android.app.Application
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.sample.kotlin.delegate.ErrorViewDelegate
import com.dylanc.loadingstateview.sample.kotlin.delegate.LoadingViewDelegate
import com.dylanc.loadingstateview.sample.kotlin.delegate.ToolbarViewDelegate
import com.dylanc.loadingstateview.sample.kotlin.delegate.EmptyViewDelegate

class App : Application() {

  override fun onCreate() {
    super.onCreate()
    LoadingStateView.setViewDelegatePool {
      register(ToolbarViewDelegate(), LoadingViewDelegate(), ErrorViewDelegate(), EmptyViewDelegate())
    }
  }
}