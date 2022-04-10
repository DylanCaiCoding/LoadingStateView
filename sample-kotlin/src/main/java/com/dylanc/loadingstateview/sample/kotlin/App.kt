package com.dylanc.loadingstateview.sample.kotlin

import android.app.Application
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.sample.kotlin.delegate.LoadingViewDelegate
import com.dylanc.loadingstateview.toolbar.SimpleToolbarViewDelegate
import com.dylanc.loadingstateview.toolbarFactory

class App : Application() {

  override fun onCreate() {
    super.onCreate()
    LoadingStateView.setViewDelegatePool {
      register(LoadingViewDelegate())
    }
    toolbarFactory = { SimpleToolbarViewDelegate() }
  }
}