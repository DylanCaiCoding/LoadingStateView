package com.dylanc.loadingstateview.sample.kotlin.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dylanc.loadingstateview.LoadingState
import com.dylanc.loadingstateview.LoadingState.Companion.decorateWithLoadingState
import com.dylanc.loadingstateview.LoadingStateImpl

abstract class BaseActivity : AppCompatActivity(), LoadingState by LoadingStateImpl() {

  abstract val layoutRes: Int

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layoutRes)
    decorateWithLoadingState(isDecorated)
  }

  open val isDecorated = true
}