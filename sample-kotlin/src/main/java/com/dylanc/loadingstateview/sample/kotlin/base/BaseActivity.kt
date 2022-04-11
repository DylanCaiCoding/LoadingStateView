package com.dylanc.loadingstateview.sample.kotlin.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dylanc.loadingstateview.LoadingState
import com.dylanc.loadingstateview.LoadingStateImpl
import com.dylanc.loadingstateview.OnReloadListener

abstract class BaseActivity : AppCompatActivity(), LoadingState by LoadingStateImpl(), OnReloadListener {

  abstract val layoutRes: Int

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layoutRes)
    decorateContentView(this, isDecorated)
  }

  open val isDecorated = true

  override fun onReload() = Unit
}