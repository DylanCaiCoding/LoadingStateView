package com.dylanc.loadingstateview.sample.kotlin.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import com.dylanc.loadingstateview.NavBtnType
import com.dylanc.loadingstateview.rightIcon
import com.dylanc.loadingstateview.sample.kotlin.R
import com.dylanc.loadingstateview.sample.kotlin.base.BaseActivity
import com.dylanc.loadingstateview.toolbar.*

class MainActivity : BaseActivity() {

  override val layoutRes: Int = R.layout.activity_main

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setToolbar(R.string.app_name) {
      rightIcon(R.drawable.ic_add) {
        Toast.makeText(this@MainActivity, "Add", Toast.LENGTH_SHORT).show()
      }
    }
    findViewById<View>(R.id.button).setOnClickListener {
      showLoadingView()
      Handler(Looper.getMainLooper()).postDelayed({
        showContentView()
      }, 2000)
    }
  }
}
