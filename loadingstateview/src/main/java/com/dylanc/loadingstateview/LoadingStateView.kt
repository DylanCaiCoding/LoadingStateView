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

@file:Suppress("unused")

package com.dylanc.loadingstateview

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import java.util.*

/**
 * @author Dylan Cai
 */
class LoadingStateView(private val contentView: View) {
  lateinit var decorView: View private set
  lateinit var currentViewType: Any private set
  private lateinit var contentParent: ViewGroup
  private val parent: ViewGroup?
  private var currentView: View? = null
  private var onReloadListener: OnReloadListener? = null
  private var viewDelegates: HashMap<Any, ViewDelegate> = HashMap()
  private val viewCashes: HashMap<Any, View> = HashMap()

  /**
   * Constructs a LoadingStateView with a activity and a content view delegate
   *
   * @param activity the activity
   */
  constructor(activity: Activity) :
      this(activity.findViewById<ViewGroup>(android.R.id.content).getChildAt(0))

  init {
    viewDelegatePool?.apply { ViewDelegatePool(this@LoadingStateView).invoke() }
    parent = contentView.parent as ViewGroup?
    register(ContentViewDelegate())
    setDecorView(LinearDecorViewDelegate(emptyList()))
  }

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
   * Adds one or more views to decorate content in the header.
   *
   * @param delegates the view delegates of creating view
   */
  fun setDecorHeader(vararg delegates: ViewDelegate) =
    setDecorView(LinearDecorViewDelegate(delegates.map {
      register(it)
      getView(it.viewType)
    }))

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

  /**
   * Adds child decorative header between the content and the decorative view.
   *
   * @param delegates the view delegates of creating view
   */
  fun addChildDecorHeader(vararg delegates: ViewDelegate) =
    addChildDecorView(LinearDecorViewDelegate(delegates.map {
      register(it)
      getView(it.viewType)
    }))

  private fun DecorViewDelegate.createDecorView() =
    onCreateDecorView(LayoutInflater.from(contentView.context)).also { decorView ->
      contentView.layoutParams?.let { decorView.layoutParams = it }
    }

  /**
   * Registers the view delegate of creating view before showing view.
   *
   * @param viewDelegate  the view delegate of creating view
   */
  fun register(vararg delegates: ViewDelegate) {
    delegates.forEach { viewDelegates[it.viewType] = it }
  }

  /**
   * Called if you need to handle reload event, you can get the listener of reloading data from view holder.
   *
   * @param onReloadListener the listener of reloading data
   */
  fun setOnReloadListener(onReloadListener: OnReloadListener) {
    this.onReloadListener = onReloadListener
  }

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
      viewCashes[viewType]?.let { addView(viewType) }
      if (viewType != currentViewType) {
        getView(viewType).visibility = View.VISIBLE
        if (animation != null) {
          animation.onStartHideAnimation(currentView, currentViewType)
          animation.onStartShowAnimation(getView(viewType), getViewDelegate<ViewDelegate>(viewType).viewType)
        } else {
          currentView.visibility = View.GONE
        }
        this.currentView = getView(viewType)
      }
    }
    currentViewType = viewType
  }

  private fun addView(viewType: Any) {
    val view = getView(viewType)
    if (view.parent != null) {
      (view.parent as ViewGroup).removeView(view)
    }
    if (parent is ConstraintLayout && viewType == ViewType.CONTENT) {
      view.updateLayoutParams {
        if (view.measuredWidth == 0) width = MATCH_PARENT
        if (view.measuredHeight == 0) height = MATCH_PARENT
      }
    }
    contentParent.addView(view)
    currentView = view
  }

  private fun getView(viewType: Any): View {
    if (viewCashes[viewType] == null) {
      addViewHolder(viewType)
    }
    return viewCashes[viewType]!!
  }

  @Suppress("UNCHECKED_CAST")
  fun <T : ViewDelegate> getViewDelegate(viewType: Any) = viewDelegates[viewType] as T

  private fun addViewHolder(viewType: Any) {
    val viewDelegate: ViewDelegate = getViewDelegate(viewType)
    val view = viewDelegate.onCreateView(LayoutInflater.from(contentParent.context), contentParent)
    viewDelegate.onReloadListener = onReloadListener
    viewCashes[viewType] = view
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
    abstract fun onCreateDecorView(inflater: LayoutInflater): View

    abstract fun getContentParent(decorView: View): ViewGroup
  }

  private class LinearDecorViewDelegate(private val views: List<View>) : DecorViewDelegate() {
    private lateinit var contentParent: FrameLayout

    override fun onCreateDecorView(inflater: LayoutInflater) =
      LinearLayout(inflater.context).apply {
        orientation = LinearLayout.VERTICAL
        contentParent = FrameLayout(context)
        contentParent.layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        views.forEach { addView(it) }
        addView(contentParent)
      }

    override fun getContentParent(decorView: View) = contentParent
  }

  class ViewDelegatePool internal constructor(private val stateView: LoadingStateView) {
    fun register(vararg delegates: ViewDelegate) = stateView.register(*delegates)
  }

  fun interface OnReloadListener {
    fun onReload()
  }

  fun interface Callback<in T> {
    fun T.invoke()
  }

  interface Animation {
    fun onStartShowAnimation(view: View, viewType: Any)

    fun onStartHideAnimation(view: View, viewType: Any)
  }

  companion object {
    private var viewDelegatePool: Callback<ViewDelegatePool>? = null

    @JvmStatic
    fun setViewDelegatePool(viewDelegatePool: Callback<ViewDelegatePool>) {
      this.viewDelegatePool = viewDelegatePool
    }
  }
}

enum class ViewType {
  TITLE, LOADING, CONTENT, ERROR, EMPTY
}