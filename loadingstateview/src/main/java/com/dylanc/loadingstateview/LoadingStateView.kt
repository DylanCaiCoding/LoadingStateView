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

@file:Suppress("unused", "MemberVisibilityCanBePrivate")

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
  private lateinit var contentParent: ViewGroup
  private val parent: ViewGroup?
  private var currentViewHolder: ViewHolder? = null
  private var onReloadListener: OnReloadListener? = null
  private var viewDelegates: HashMap<Any, ViewDelegate<*>> = HashMap()
  private val viewHolders: HashMap<Any, ViewHolder> = HashMap()

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
    register(ViewType.CONTENT, ContentViewDelegate())
    setDecorView(LinearDecorViewDelegate(listOf()))
  }

  /**
   * Sets an view delegate for decorating content view.
   *
   * @param decorViewDelegate the view delegate for decorating content view.
   */
  fun setDecorView(decorViewDelegate: DecorViewDelegate) {
    currentViewHolder = null
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
   * @param pairs the view delegates of creating view
   */
  fun setDecorHeader(vararg pairs: Pair<Any, ViewDelegate<*>>) =
    setDecorHeader(*pairs.map {
      register(it.first, it.second)
      it.first
    }.toTypedArray())

  /**
   * Adds one or more views to decorate content in the header.
   *
   * @param viewType the view type of view delegate
   */
  fun setDecorHeader(vararg viewType: Any) =
    setDecorView(LinearDecorViewDelegate(viewType.map { getViewHolder(it).rootView }))

  /**
   * Adds child decorative view between the content and the decorative view.
   *
   * @param decorViewDelegate the view delegate for decorating content view.
   */
  fun addChildDecorView(decorViewDelegate: DecorViewDelegate) {
    contentParent.removeView(currentViewHolder?.rootView)
    currentViewHolder = null
    val childDecorView = decorViewDelegate.createDecorView()
    contentParent.addView(childDecorView)
    contentParent = decorViewDelegate.getContentParent(childDecorView)
    showView(ViewType.CONTENT)
  }

  /**
   * Adds child decorative header between the content and the decorative view.
   *
   * @param pairs the view delegates of creating view
   */
  fun addChildDecorHeader(vararg pairs: Pair<Any, ViewDelegate<*>>) =
    addChildDecorHeader(*pairs.map {
      register(it.first, it.second)
      it.first
    }.toTypedArray())

  /**
   * Adds child decorative header between the content and the decorative view.
   *
   * @param viewTypes the view type of view delegate
   */
  fun addChildDecorHeader(vararg viewTypes: Any) =
    addChildDecorView(LinearDecorViewDelegate(viewTypes.map { getViewHolder(it).rootView }))

  private fun DecorViewDelegate.createDecorView() =
    onCreateDecorView(LayoutInflater.from(contentView.context)).also { decorView ->
      contentView.layoutParams?.let { decorView.layoutParams = it }
    }

  /**
   * Registers the view delegate of creating view before showing view.
   *
   * @param viewType the view type of view delegate
   * @param viewDelegate  the view delegate of creating view
   */
  fun register(viewType: Any, viewDelegate: ViewDelegate<*>) {
    viewDelegates[viewType] = viewDelegate
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
    val currentViewHolder = currentViewHolder
    if (currentViewHolder == null) {
      addView(viewType)
    } else {
      if (viewHolders[viewType] == null) {
        addView(viewType)
      }
      if (viewType != currentViewHolder.viewType) {
        getViewHolder(viewType).rootView.visibility = View.VISIBLE
        if (animation != null) {
          animation.onStartHideAnimation(currentViewHolder.rootView, currentViewHolder.viewType)
          animation.onStartShowAnimation(getViewHolder(viewType).rootView, getViewHolder(viewType).viewType)
        } else {
          currentViewHolder.rootView.visibility = View.GONE
        }
        this.currentViewHolder = getViewHolder(viewType)
      }
    }
  }

  /**
   * Gets the current view type.
   */
  val currentViewType get() = currentViewHolder!!.viewType

  /**
   * Updates the view by view type. It needs to be called after showing view.
   */
  @Suppress("UNCHECKED_CAST")
  fun <T : ViewHolder> updateView(viewType: Any, block: Callback<T>) {
    block.apply { (getViewHolder(viewType) as T).invoke() }
  }

  private fun addView(viewType: Any) {
    val viewHolder = getViewHolder(viewType)
    val rootView = viewHolder.rootView
    if (rootView.parent != null) {
      (rootView.parent as ViewGroup).removeView(rootView)
    }
    if (parent is ConstraintLayout && viewType == ViewType.CONTENT) {
      rootView.updateLayoutParams {
        if (rootView.measuredWidth == 0) width = MATCH_PARENT
        if (rootView.measuredHeight == 0) height = MATCH_PARENT
      }
    }
    contentParent.addView(rootView)
    currentViewHolder = viewHolder
  }

  private fun getViewHolder(viewType: Any): ViewHolder {
    if (viewHolders[viewType] == null) {
      addViewHolder(viewType)
    }
    return viewHolders[viewType] as ViewHolder
  }

  @Suppress("UNCHECKED_CAST")
  fun <T : ViewDelegate<*>> getViewDelegate(viewType: Any) = viewDelegates[viewType] as T

  private fun addViewHolder(viewType: Any) {
    val viewDelegate: ViewDelegate<ViewHolder> = getViewDelegate(viewType)
    val viewHolder = viewDelegate.onCreateViewHolder(LayoutInflater.from(contentParent.context), contentParent)
    viewHolder.viewType = viewType
    viewDelegate.onReloadListener = onReloadListener
    viewHolders[viewType] = viewHolder
  }

  abstract class ViewDelegate<VH : ViewHolder> {
    var onReloadListener: OnReloadListener? = null
      internal set

    abstract fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH
  }

  private inner class ContentViewDelegate : LoadingStateView.ViewDelegate<ViewHolder>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup) = ViewHolder(contentView)
  }

  open class ViewHolder(val rootView: View) {
    internal lateinit var viewType: Any
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
    fun register(viewType: Any, viewDelegate: ViewDelegate<*>) = stateView.register(viewType, viewDelegate)
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