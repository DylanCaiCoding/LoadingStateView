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
@Suppress("unused")
class LoadingHelper @JvmOverloads constructor(
  private val contentView: View,
  contentAdapter: ContentAdapter<*>? = null
) {
  val decorView: LinearLayout
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
    contentAdapter?.let { register(ViewType.CONTENT, contentAdapter) }
    decorView = LinearLayout(contentView.context)
    decorView.orientation = LinearLayout.VERTICAL
    decorView.layoutParams = contentView.layoutParams
    val parent = contentView.parent as ViewGroup?
    if (parent != null) {
      val index = parent.indexOfChild(contentView)
      parent.removeView(contentView)
      parent.addView(decorView, index)
    }
    showView(ViewType.CONTENT)
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

  fun setOnReloadListener(onReloadListener: () -> Unit) =
    setOnReloadListener(object : OnReloadListener {
      override fun onReload() {
        onReloadListener.invoke()
      }
    })

  /**
   * Adds title view to the header of decorated view
   */
  fun addTitleView() {
    addHeaderView(ViewType.TITLE)
  }

  /**
   * Adds view to the header of decorated view
   *
   * @param viewType the view type of adapter
   */
  @JvmOverloads
  fun addHeaderView(viewType: Any, index: Int = 0) {
    decorView.addView(getViewHolder(viewType).rootView, index)
  }

  /**
   * Removes view in the header
   *
   * @param viewType the view type of adapter
   */
  fun removeHeaderView(viewType: Any) {
    decorView.removeView(getViewHolder(viewType).rootView)
  }

  /**
   * Shows the loading view
   */
  fun showLoadingView()  = showView(ViewType.LOADING)


  /**
   * Shows the content view
   */
  fun showContentView()  = showView(ViewType.CONTENT)

  /**
   * Shows the error view
   */
  fun showErrorView() = showView(ViewType.ERROR)

  /**
   * Shows the empty view
   */
  fun showEmptyView()= showView(ViewType.EMPTY)

  /**
   * Shows the view by view type
   *
   * @param viewType the view type of adapter
   */
  fun showView(viewType: Any) {
    if (currentViewHolder == null) {
      addView(viewType)
    } else {
      if (viewType !== currentViewHolder!!.viewType) {
        decorView.removeView(currentViewHolder!!.rootView)
        addView(viewType)
      }
    }
  }

  private fun addView(viewType: Any) {
    val viewHolder = getViewHolder(viewType)
    decorView.addView(viewHolder.rootView)
    currentViewHolder = viewHolder
  }

  private fun notifyDataSetChanged(adapter: Adapter<ViewHolder>) {
    adapter.onBindViewHolder(getViewHolder(getViewType(adapter)!!))
  }

  @Suppress("UNCHECKED_CAST")
  private fun getViewHolder(viewType: Any): ViewHolder {
    if (viewHolders[viewType] == null) {
      addViewHolder(viewType)
    }
    return viewHolders[viewType] as ViewHolder
  }

  private fun getViewType(targetAdapter: Adapter<*>): Any? {
    for (entry in adapters.entries) {
      if (entry.value == targetAdapter) return entry.key
    }
    return null
  }

  @Suppress("UNCHECKED_CAST")
  fun <T : Adapter<out ViewHolder>> getAdapter(viewType: Any): T {
    var adapter: Adapter<*>? = adapters[viewType]
    if (adapter == null && viewType === ViewType.CONTENT) {
      adapter = object : LoadingHelper.ContentAdapter<ViewHolder>() {
        override fun onCreateViewHolder(contentView: View): ViewHolder = ViewHolder(contentView)

        override fun onBindViewHolder(holder: ViewHolder) {}
      }
      register(ViewType.CONTENT, adapter)
    }
    return adapter as T
  }

  private fun addViewHolder(viewType: Any) {
    val adapter: Adapter<ViewHolder> = getAdapter(viewType)
    val viewHolder = if (adapter is ContentAdapter<*>) {
      adapter.onCreateViewHolder(contentView)
    } else {
      adapter.onCreateViewHolder(LayoutInflater.from(decorView.context), decorView)
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
    fun notifyDataSetChanged() {
      listener.invoke(this as Adapter<ViewHolder>)
    }
  }

  abstract class ContentAdapter<VH : ViewHolder> : Adapter<VH>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup) =
      onCreateViewHolder(View(parent.context))

    abstract fun onCreateViewHolder(contentView: View): VH
  }

  open class ViewHolder(val rootView: View) {

    internal var viewType: Any? = null
    /**
     * Gets the listener of reloading data from view holder.
     */
    var onReloadListener: OnReloadListener? = null
      internal set
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
