/*
 * Copyright (c) 2019. Dylan Cai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dylanc.loadingstateview

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * @author Dylan Cai
 */
class LoadingStateView @JvmOverloads constructor(
  private val contentView: View,
  var onReloadListener: OnReloadListener? = null
) {
  lateinit var decorView: View private set
  lateinit var currentViewType: Any private set
  private lateinit var contentParent: ViewGroup
  private val parent: ViewGroup?
  private var currentView: View? = null
  private var viewDelegates: HashMap<Any, ViewDelegate> = HashMap()
  private val viewCashes: HashMap<Any, View> = HashMap()

  /**
   * Constructs a LoadingStateView with an activity and listener.
   */
  @JvmOverloads
  constructor(activity: Activity, listener: OnReloadListener? = null) :
      this(activity.findViewById<ViewGroup>(android.R.id.content).getChildAt(0), listener)

  init {
    poolInitializer?.apply { PoolInitializer(this@LoadingStateView).invoke() }
    parent = contentView.parent as ViewGroup?
    register(ContentViewDelegate())
    setDecorView(LinearDecorViewDelegate(emptyList()))
  }

  /**
   * Adds one or more views to decorate content in the header.
   *
   * @param delegates the view delegates of creating view
   */
  fun setHeaders(vararg delegates: ViewDelegate) = setDecorView(LinearDecorViewDelegate(delegates))

  /**
   * Sets an view delegate for decorating content view.
   *
   * @param decorViewDelegate the view delegate for decorating content view.
   */
  fun setDecorView(decorViewDelegate: DecorViewDelegate) {
    currentView = null
    if (parent != null) {
      val index = parent.indexOfChild(contentView)
      if (index >= 0) {
        parent.removeView(contentView)
      } else {
        parent.removeView(decorView)
        (contentView.parent as ViewGroup).removeView(contentView)
      }
      decorView = decorViewDelegate.createDecorView()
      parent.addView(decorView, index)
    } else {
      decorView = decorViewDelegate.createDecorView()
    }
    contentParent = decorViewDelegate.getContentParent(decorView)
    showView(ViewType.CONTENT)
  }

  /**
   * Adds child decorative header between the content and the decorative view.
   *
   * @param delegates the view delegates of creating view
   */
  fun addChildHeaders(vararg delegates: ViewDelegate) = addChildDecorView(LinearDecorViewDelegate(delegates))

  /**
   * Adds child decorative view between the content and the decorative view.
   *
   * @param decorViewDelegate the view delegate for decorating content view.
   */
  fun addChildDecorView(decorViewDelegate: DecorViewDelegate) {
    contentParent.removeView(currentView)
    currentView = null
    val childDecorView = decorViewDelegate.createDecorView()
    contentParent.addView(childDecorView)
    contentParent = decorViewDelegate.getContentParent(childDecorView)
    showView(ViewType.CONTENT)
  }

  private fun DecorViewDelegate.createDecorView() =
    onCreateDecorView(contentView.context, LayoutInflater.from(contentView.context)).also { decorView ->
      contentView.layoutParams?.let {
        decorView.layoutParams = if (it is ConstraintLayout.LayoutParams) ConstraintLayout.LayoutParams(it) else it
      }
    }

  /**
   * Registers the view delegate of creating view before showing view.
   *
   * @param delegates the view delegate of creating view
   */
  fun register(vararg delegates: ViewDelegate) = delegates.forEach { viewDelegates[it.viewType] = it }

  @JvmOverloads
  fun showLoadingView(animation: Animation? = null) = showView(ViewType.LOADING, animation)

  @JvmOverloads
  fun showContentView(animation: Animation? = null) = showView(ViewType.CONTENT, animation)

  @JvmOverloads
  fun showErrorView(animation: Animation? = null) = showView(ViewType.ERROR, animation)

  @JvmOverloads
  fun showEmptyView(animation: Animation? = null) = showView(ViewType.EMPTY, animation)

  /**
   * Shows the view by view type
   *
   * @param viewType the view type of view delegate
   */
  @JvmOverloads
  fun showView(viewType: Any, animation: Animation? = null) {
    val currentView = currentView
    if (currentView == null) {
      addView(viewType)
    } else {
      if (viewCashes[viewType] == null) addView(viewType)
      if (viewType != currentViewType) {
        val view = getView(viewType)
        view.visibility = View.VISIBLE
        if (animation != null) {
          animation.onStartHideAnimation(currentView, currentViewType)
          animation.onStartShowAnimation(view, getViewDelegate<ViewDelegate>(viewType)!!.viewType)
        } else {
          currentView.visibility = View.GONE
        }
        this.currentView = view
      }
    }
    currentViewType = viewType
  }

  fun <T : ViewDelegate> updateViewDelegate(viewType: Any, callback: Callback<T>) =
    callback.apply { getViewDelegate<T>(viewType)?.invoke() }

  @Suppress("UNCHECKED_CAST")
  fun <T : ViewDelegate> getViewDelegate(viewType: Any) = viewDelegates[viewType] as? T

  private fun addView(viewType: Any) {
    val view = getView(viewType)
    (view.parent as? ViewGroup)?.removeView(view)
    if (parent is ConstraintLayout && viewType == ViewType.CONTENT) {
      val params = view.layoutParams
      if (view.measuredWidth == 0) params.width = MATCH_PARENT
      if (view.measuredHeight == 0) params.width = MATCH_PARENT
      view.layoutParams = params
    }
    contentParent.addView(view)
    currentView = view
  }

  private fun getView(viewType: Any): View {
    if (viewCashes[viewType] == null) {
      val viewDelegate = requireNotNull(getViewDelegate(viewType)) { "Please register view delegate for $viewType type." }
      val view = viewDelegate.onCreateView(LayoutInflater.from(contentParent.context), contentParent)
      viewDelegate.onReloadListener = onReloadListener
      viewCashes[viewType] = view
    }
    return viewCashes[viewType]!!
  }

  abstract class ViewDelegate(val viewType: Any) {
    var onReloadListener: OnReloadListener? = null
      internal set

    abstract fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View
  }

  private inner class ContentViewDelegate : LoadingStateView.ViewDelegate(ViewType.CONTENT) {
    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup) = contentView
  }

  abstract class DecorViewDelegate {
    abstract fun onCreateDecorView(context: Context, inflater: LayoutInflater): View
    abstract fun getContentParent(decorView: View): ViewGroup
  }

  private inner class LinearDecorViewDelegate(private val views: List<View>) : DecorViewDelegate() {
    private lateinit var contentParent: FrameLayout

    constructor(delegates: Array<out ViewDelegate>) : this(delegates.map {
      register(it)
      getView(it.viewType)
    })

    override fun onCreateDecorView(context: Context, inflater: LayoutInflater) =
      LinearLayout(inflater.context).apply {
        orientation = LinearLayout.VERTICAL
        contentParent = FrameLayout(context)
        contentParent.layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        views.forEach { addView(it) }
        addView(contentParent)
      }

    override fun getContentParent(decorView: View) = contentParent
  }

  class PoolInitializer internal constructor(private val stateView: LoadingStateView) {
    fun register(vararg delegates: ViewDelegate) = stateView.register(*delegates)
  }

  fun interface Callback<in T> {
    fun T.invoke()
  }

  interface Animation {
    fun onStartShowAnimation(view: View, viewType: Any)
    fun onStartHideAnimation(view: View, viewType: Any)
  }

  companion object {
    private var poolInitializer: Callback<PoolInitializer>? = null

    @JvmStatic
    fun setViewDelegatePool(poolInitializer: Callback<PoolInitializer>) {
      this.poolInitializer = poolInitializer
    }
  }
}

interface OnReloadListener {
  fun onReload() = Unit
}

enum class ViewType {
  TITLE, LOADING, CONTENT, ERROR, EMPTY
}