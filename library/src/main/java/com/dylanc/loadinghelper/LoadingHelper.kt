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

package com.dylanc.loadinghelper

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import java.util.*

/**
 * @author Dylan Cai
 */
class LoadingHelper @JvmOverloads constructor(
  private val contentView: View,
  contentAdapter: ContentAdapter<*>? = null
) {
  lateinit var decorView: View
    private set
  private lateinit var contentParent: ViewGroup
  private val parent: ViewGroup?
  private var currentViewHolder: ViewHolder? = null
  private var onReloadListener: OnReloadListener? = null
  private var adapters: HashMap<Any, Adapter<*>> = HashMap()
  private val viewHolders: HashMap<Any, ViewHolder> = HashMap()

  companion object {
    private var adapterPool: (AdapterPool.() -> Unit)? = null

    @JvmStatic
    fun setDefaultAdapterPool(adapterPool: AdapterPool.() -> Unit) {
      this.adapterPool = adapterPool
    }
  }

  /**
   * Constructs a LoadingHelper with a activity and a content adapter
   *
   * @param activity       the activity
   * @param contentAdapter the adapter of creating content view
   */
  @JvmOverloads
  constructor(activity: Activity, contentAdapter: ContentAdapter<*>? = null) : this(
    (activity.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0),
    contentAdapter
  )

  init {
    adapterPool?.let { AdapterPool(this).apply(it) }
    parent = contentView.parent as ViewGroup?
    register(ViewType.CONTENT, contentAdapter ?: SimpleContentAdapter())
    setDecorAdapter(LinearDecorAdapter(listOf()))
  }

  /**
   * Sets an adapter for decorating content view.
   *
   * @param decorAdapter the adapter for decorating content view.
   * @since v2.0.0
   */
  fun setDecorAdapter(decorAdapter: DecorAdapter) {
    currentViewHolder = null
    if (parent != null) {
      val index = parent.indexOfChild(contentView)
      if (index >= 0) {
        parent.removeView(contentView)
      } else {
        parent.removeView(decorView)
        (contentView.parent as ViewGroup).removeView(contentView)
      }
      decorView = decorAdapter.createDecorView()
      parent.addView(decorView, index)
    } else {
      decorView = decorAdapter.createDecorView()
    }
    contentParent = decorAdapter.getContentParent(decorView)
    showView(ViewType.CONTENT)
  }

  /**
   * Adds one or more views to decorate content in the header.
   *
   * @param viewType the view type of adapter
   * @since v2.0.0
   */
  fun setDecorHeader(vararg viewType: Any) {
    val views = mutableListOf<View>()
    for (t in viewType) {
      views.add(getViewHolder(t).rootView)
    }
    setDecorAdapter(LinearDecorAdapter(views))
  }

  /**
   * Adds child decorative view between the content and the decorative view.
   *
   * @param decorAdapter the adapter for decorating content view.
   * @since v2.1.0
   */
  fun addChildDecorAdapter(decorAdapter: DecorAdapter) {
    contentParent.removeView(currentViewHolder?.rootView)
    currentViewHolder = null
    val childDecorView = decorAdapter.createDecorView()
    contentParent.addView(childDecorView)
    contentParent = decorAdapter.getContentParent(childDecorView)
    showView(ViewType.CONTENT)
  }

  /**
   * Adds child decorative header between the content and the decorative view.
   *
   * @param viewTypes the view type of adapter
   * @since v2.1.0
   */
  fun addChildDecorHeader(vararg viewTypes: Any) {
    val views = mutableListOf<View>()
    for (viewType in viewTypes) {
      views.add(getViewHolder(viewType).rootView)
    }
    addChildDecorAdapter(LinearDecorAdapter(views))
  }

  private fun DecorAdapter.createDecorView() =
    onCreateDecorView(LayoutInflater.from(contentView.context)).also { decorView ->
      if (contentView.layoutParams != null) {
        decorView.layoutParams = contentView.layoutParams
      }
    }

  /**
   * Registers the adapter of creating view before showing view.
   *
   * @param viewType the view type of adapter
   * @param adapter  the adapter of creating view
   */
  fun register(viewType: Any, adapter: Adapter<*>) {
    adapters[viewType] = adapter
  }

  /**
   * Called if you need to handle reload event, you can get the listener of reloading data from view holder.
   *
   * @param onReloadListener the listener of reloading data
   */
  fun setOnReloadListener(onReloadListener: OnReloadListener) {
    this.onReloadListener = onReloadListener
  }

  fun setOnReloadListener(onReload: () -> Unit) =
    setOnReloadListener(object : OnReloadListener {
      override fun onReload() = onReload()
    })

  fun showLoadingView() = showView(ViewType.LOADING)

  fun showContentView() = showView(ViewType.CONTENT)

  fun showErrorView() = showView(ViewType.ERROR)

  fun showEmptyView() = showView(ViewType.EMPTY)

  /**
   * Shows the view by view type
   *
   * @param viewType the view type of adapter
   */
  fun showView(viewType: Any) {
    if (currentViewHolder == null) {
      addView(viewType)
    } else {
      if (viewType !== currentViewHolder!!.viewType && currentViewHolder!!.rootView.parent != null) {
        contentParent.removeView(currentViewHolder!!.rootView)
        addView(viewType)
      }
    }
  }

  private fun addView(viewType: Any) {
    val viewHolder = getViewHolder(viewType)
    val rootView = viewHolder.rootView
    if (rootView.parent != null) {
      (rootView.parent as ViewGroup).removeView(rootView)
    }
    contentParent.addView(rootView)
    currentViewHolder = viewHolder
  }

  private fun notifyDataSetChanged(adapter: Adapter<ViewHolder>) =
    adapter.onBindViewHolder(getViewHolder(getViewType(adapter)!!))

  private fun getViewHolder(viewType: Any): ViewHolder {
    if (viewHolders[viewType] == null) {
      addViewHolder(viewType)
    }
    return viewHolders[viewType] as ViewHolder
  }

  private fun getViewType(targetAdapter: Adapter<*>): Any? {
    for (entry in adapters.entries) {
      if (entry.value == targetAdapter) {
        return entry.key
      }
    }
    return null
  }

  @Suppress("UNCHECKED_CAST")
  fun <T : Adapter<out ViewHolder>> getAdapter(viewType: Any) = adapters[viewType] as T

  private fun addViewHolder(viewType: Any) {
    val adapter: Adapter<ViewHolder> = getAdapter(viewType)
    val viewHolder = if (adapter is ContentAdapter<*>) {
      adapter.onCreateViewHolder(contentView)
    } else {
      adapter.onCreateViewHolder(LayoutInflater.from(contentParent.context), contentParent)
    }
    viewHolder.viewType = viewType
    viewHolder.onReloadListener = onReloadListener
    viewHolders[viewType] = viewHolder
    adapter.onBindViewHolder(viewHolder)
    adapter.listener = this::notifyDataSetChanged
  }

  abstract class Adapter<VH : ViewHolder> {
    internal lateinit var listener: (adapter: Adapter<ViewHolder>) -> Unit

    abstract fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH

    abstract fun onBindViewHolder(holder: VH)

    @Suppress("UNCHECKED_CAST")
    fun notifyDataSetChanged() = listener.invoke(this as Adapter<ViewHolder>)
  }

  abstract class ContentAdapter<VH : ViewHolder> : Adapter<VH>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup) =
      onCreateViewHolder(View(parent.context))

    abstract fun onCreateViewHolder(contentView: View): VH
  }

  private class SimpleContentAdapter : LoadingHelper.ContentAdapter<ViewHolder>() {
    override fun onCreateViewHolder(contentView: View): ViewHolder = ViewHolder(contentView)

    override fun onBindViewHolder(holder: ViewHolder) = Unit
  }

  open class ViewHolder(val rootView: View) {
    internal var viewType: Any? = null
    var onReloadListener: OnReloadListener? = null
      internal set
  }

  abstract class DecorAdapter {
    abstract fun onCreateDecorView(inflater: LayoutInflater): View

    abstract fun getContentParent(decorView: View): ViewGroup
  }

  private class LinearDecorAdapter(private val views: List<View>) : DecorAdapter() {
    override fun onCreateDecorView(inflater: LayoutInflater) =
      LinearLayout(inflater.context).apply {
        orientation = LinearLayout.VERTICAL
        for (view in views) {
          addView(view)
        }
      }

    override fun getContentParent(decorView: View) = decorView as ViewGroup
  }

  class AdapterPool internal constructor(private val helper: LoadingHelper) {
    fun register(viewType: Any, adapter: Adapter<*>) {
      helper.register(viewType, adapter)
    }
  }

  interface OnReloadListener {
    fun onReload()
  }
}

enum class ViewType {
  TITLE, LOADING, CONTENT, ERROR, EMPTY
}