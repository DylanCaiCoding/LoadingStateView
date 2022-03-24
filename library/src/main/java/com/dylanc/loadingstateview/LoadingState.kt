package com.dylanc.loadingstateview

import android.app.Activity
import android.view.View
import android.view.ViewGroup

interface LoadingState : LoadingStateView.OnReloadListener {
  val loadingStateView: LoadingStateView?
  fun View.decorateWithLoadingState(isDecorated: Boolean = true): View
  fun registerLoadingState(viewType: Any, viewDelegate: LoadingStateView.ViewDelegate<*>)
  fun <T : LoadingStateView.ViewDelegate<*>> updateView(viewType: Any, block: LoadingStateView.Callback<T>)
  fun showLoadingView()
  fun showContentView()
  fun showErrorView()
  fun showEmptyView()
  fun showCustomView(viewType: Any)

  companion object {
    context(LoadingState)
    fun Activity.decorateWithLoadingState(isDecorated: Boolean = true) {
      findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
        .decorateWithLoadingState(isDecorated)
    }
  }
}

class LoadingStateImpl : LoadingState {
  override var loadingStateView: LoadingStateView? = null
    private set

  override fun View.decorateWithLoadingState(isDecorated: Boolean): View {
    return if (isDecorated) {
      loadingStateView = LoadingStateView(this)
        .apply { setOnReloadListener(::onReload) }
      loadingStateView!!.decorView
    } else {
      this
    }
  }

  override fun registerLoadingState(viewType: Any, viewDelegate: LoadingStateView.ViewDelegate<*>) {
    loadingStateView?.register(viewType, viewDelegate)
  }

  override fun <T : LoadingStateView.ViewDelegate<*>> updateView(viewType: Any, block: LoadingStateView.Callback<T>) {
    loadingStateView?.updateView(viewType, block)
  }

  override fun showLoadingView() {
    loadingStateView?.showLoadingView()
  }

  override fun showContentView() {
    loadingStateView?.showContentView()
  }

  override fun showErrorView() {
    loadingStateView?.showErrorView()
  }

  override fun showEmptyView() {
    loadingStateView?.showEmptyView()
  }

  override fun showCustomView(viewType: Any) {
    loadingStateView?.showView(viewType)
  }

  override fun onReload() = Unit
}
