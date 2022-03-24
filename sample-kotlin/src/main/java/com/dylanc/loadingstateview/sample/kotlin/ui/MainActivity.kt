package com.dylanc.loadingstateview.sample.kotlin.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.dylanc.loadingstateview.sample.kotlin.R
import com.dylanc.loadingstateview.sample.kotlin.base.BaseActivity

class MainActivity : BaseActivity() {
  override val layoutRes: Int = R.layout.activity_main

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    findViewById<View>(R.id.button).setOnClickListener {
      showLoadingView()
      Handler(Looper.getMainLooper()).postDelayed({
        showContentView()
      }, 2000)
    }
  }

  override val isDecorated = false
}