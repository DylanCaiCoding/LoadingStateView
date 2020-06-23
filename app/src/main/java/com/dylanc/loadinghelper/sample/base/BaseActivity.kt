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

package com.dylanc.loadinghelper.sample.base

import android.view.MenuItem
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.dylanc.loadinghelper.LoadingHelper
import com.dylanc.loadinghelper.ViewType

/**
 * 这是耦合度较低的封装方式，没有任何抽象方法，可以很方便地将基类里的代码拷贝到其它项目的基类里使用。
 *
 * 使用该基类时注意以下事项：
 * 将主题设置成 NoActionBar 再使用，不然会有报错，后续会将这个问题修复。
 * 要注册一个类型为 ViewType.TITLE 的继承了 BaseToolbarAdapter 的全局标题栏适配器。
 *
 * @author Dylan Cai
 */
@Suppress("unused")
abstract class BaseActivity : AppCompatActivity() {

  lateinit var loadingHelper: LoadingHelper
    private set

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
    loadingHelper.setOnReloadListener(this::onReload)
  }

  @JvmOverloads
  fun setToolbar(
    title: String, type: NavIconType = NavIconType.NONE,
    menuId: Int = 0, listener: ((MenuItem) -> Boolean)? = null
  ) =
    setToolbar(ToolbarConfig(title, type, menuId, listener))

  private fun setToolbar(config: ToolbarConfig) {
    val toolbarAdapter: BaseToolbarAdapter<ToolbarConfig, *> =
      loadingHelper.getAdapter(ViewType.TITLE)
    toolbarAdapter.config = config
    loadingHelper.setDecorHeader(ViewType.TITLE)
  }

  fun showLoadingView() = loadingHelper.showLoadingView()

  fun showContentView() = loadingHelper.showContentView()

  fun showErrorView() = loadingHelper.showErrorView()

  fun showEmptyView() = loadingHelper.showEmptyView()

  fun showCustomView(viewType: Any) = loadingHelper.showView(viewType)

  open fun onReload() {}
}