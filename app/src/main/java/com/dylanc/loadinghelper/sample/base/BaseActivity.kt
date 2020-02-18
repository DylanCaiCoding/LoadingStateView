package com.dylanc.loadinghelper.sample.base

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.dylanc.loadinghelper.LoadingHelper
import com.dylanc.loadinghelper.ViewType

/**
 * @author Dylan Cai
 * @since 2019/11/16
 */
abstract class BaseActivity : AppCompatActivity() {

  lateinit var loadingHelper: LoadingHelper
    private set

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    initContentView()
    initData(savedInstanceState)
    initViews()
    loadData()
  }

  abstract fun initContentView()

  abstract fun initData(savedInstanceState: Bundle?)

  abstract fun initViews()

  open fun loadData() {}

  // 如果有默认的 ContentAdapter，参数中的 null 改成创建该对象，比如把第三个参数改成：
  // contentAdapter: LoadingHelper.ContentAdapter<*>? = DefContentAdapter()
  @JvmOverloads
  fun setContentView(
    @LayoutRes layoutResID: Int,
    @IdRes contentViewId: Int = android.R.id.content,
    contentAdapter: LoadingHelper.ContentAdapter<*>? = null
  ) {
    super.setContentView(layoutResID)
    loadingHelper = LoadingHelper(findViewById<View>(contentViewId), contentAdapter)
    loadingHelper.setOnReloadListener(this::loadData)
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

  fun addTitleView(
    title: String, type: TitleConfig.Type = TitleConfig.Type.NO_BACK,
    rightIcon: Int, rightBtnClickListener: () -> Unit
  ) =
    addTitleView(title, type, rightIcon, View.OnClickListener { rightBtnClickListener() })

  fun addTitleView(
    title: String, type: TitleConfig.Type = TitleConfig.Type.NO_BACK,
    rightText: String, rightBtnClickListener: () -> Unit
  ) =
    addTitleView(title, type, rightText, View.OnClickListener { rightBtnClickListener() })

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
}