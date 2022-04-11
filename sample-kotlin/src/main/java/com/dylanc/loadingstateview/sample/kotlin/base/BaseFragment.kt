package com.dylanc.loadingstateview.sample.kotlin.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dylanc.loadingstateview.LoadingState
import com.dylanc.loadingstateview.LoadingStateImpl
import com.dylanc.loadingstateview.OnReloadListener


abstract class BaseFragment : Fragment(), LoadingState by LoadingStateImpl(), OnReloadListener {

  abstract val layoutRes: Int

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val root = inflater.inflate(layoutRes, container, false)
    return root.decorate(this, isDecorated)
  }

  open val isDecorated = true
}