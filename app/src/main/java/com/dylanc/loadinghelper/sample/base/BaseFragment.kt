package com.dylanc.loadinghelper.sample.base

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dylanc.loadinghelper.LoadingHelper
import com.dylanc.loadinghelper.ViewType

/**
 * @author Dylan Cai
 * @since 2019/11/16
 */
abstract class BaseFragment : Fragment() ,LoadingHelper.OnReloadListener {

  lateinit var loadingHelper: LoadingHelper
    private set
  private var layoutResID: Int = 0
  private var contentViewId: Int = 0
  private var contentAdapter: LoadingHelper.ContentAdapter<*>? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    initContentView()
    val rootView = inflater.inflate(layoutResID, container, false)
    return if (contentViewId > 0) {
      val contentView = rootView.findViewById<View>(contentViewId)
      loadingHelper = LoadingHelper(contentView, contentAdapter)
      loadingHelper.setOnReloadListener(this)
      rootView
    } else {
      loadingHelper = LoadingHelper(rootView, contentAdapter)
      loadingHelper.setOnReloadListener(this)
      loadingHelper.decorView
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initData(savedInstanceState)
    initViews()
  }

  abstract fun initContentView()

  abstract fun initData(savedInstanceState: Bundle?)

  abstract fun initViews()

  // 如果有默认的 ContentAdapter，参数中的 null 改成创建该对象，比如把第三个参数改成：
  // contentAdapter: LoadingHelper.ContentAdapter<*>? = DefContentAdapter()
  @JvmOverloads
  fun setContentView(
    @LayoutRes layoutResID: Int,
    @IdRes contentViewId: Int = android.R.id.content,
    contentAdapter: LoadingHelper.ContentAdapter<*>?= null
  ) {
    this.layoutResID = layoutResID
    this.contentViewId = contentViewId
    this.contentAdapter = contentAdapter
  }

  @JvmOverloads
  fun addTitleView(title: String, type: TitleConfig.Type = TitleConfig.Type.NO_BACK) =
    addTitleView(TitleConfig(title, type))

  @JvmOverloads
  fun addTitleView(
    title: String, type: TitleConfig.Type = TitleConfig.Type.NO_BACK,
    rightIcon: Int, rightBtnClickListener: View.OnClickListener
  ) =
    addTitleView(TitleConfig(title, type).apply { setRightBtn(rightIcon, rightBtnClickListener) })

  @JvmOverloads
  fun addTitleView(
    title: String, type: TitleConfig.Type = TitleConfig.Type.NO_BACK,
    rightText: String, rightBtnClickListener: View.OnClickListener
  ) =
    addTitleView(TitleConfig(title, type).apply { setRightBtn(rightText, rightBtnClickListener) })

  private fun addTitleView(config: TitleConfig) {
    val titleAdapter: BaseTitleAdapter<TitleConfig, *> = loadingHelper.getAdapter(ViewType.TITLE)
    titleAdapter.config = config
    loadingHelper.addTitleView()
  }

  fun showLoadingView() = loadingHelper.showLoadingView()

  fun showContentView() = loadingHelper.showContentView()

  fun showErrorView() = loadingHelper.showErrorView()

  fun showEmptyView() = loadingHelper.showEmptyView()

  fun showCustomView(viewType: Any) = loadingHelper.showView(viewType)

  override fun onReload() {}
}