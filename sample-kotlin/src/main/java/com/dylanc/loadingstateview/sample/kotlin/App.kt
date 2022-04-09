package com.dylanc.loadingstateview.sample.kotlin

import android.app.Application
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.ViewType
import com.dylanc.loadingstateview.toolbar.DefaultToolbarViewDelegate
import com.dylanc.loadingstateview.sample.kotlin.delegate.LoadingViewDelegate
import com.dylanc.loadingstateview.toolbar.defaultToolbarStyle

class App : Application() {

  override fun onCreate() {
    super.onCreate()
    LoadingStateView.setViewDelegatePool{
      register(ViewType.LOADING, LoadingViewDelegate())
    }
  }
}