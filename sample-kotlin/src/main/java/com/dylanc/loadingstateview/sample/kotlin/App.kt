package com.dylanc.loadingstateview.sample.kotlin

import android.app.Application
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.sample.kotlin.delegate.LoadingViewDelegate

class App : Application() {

  override fun onCreate() {
    super.onCreate()
    LoadingStateView.setViewDelegatePool{
      register(LoadingViewDelegate())
    }
  }
}