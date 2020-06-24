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
import com.dylanc.loadinghelper.sample.adapter.NavIconType
import com.dylanc.loadinghelper.sample.adapter.ScrollingDecorAdapter
import com.dylanc.loadinghelper.sample.adapter.ToolbarAdapter

/**
 * 这是耦合度较低的封装方式，没有任何抽象方法，可以很方便地将基类里的代码拷贝到其它项目的基类里使用。
 *
 * 使用该基类时注意以下事项：
 * 将主题设置成 NoActionBar，不然会有报错，后续会将这个问题修复。
 * 显示对应视图之前需要注册适配器，可以设置全局适配器，某个页面想修改样式时再注册个新的适配器。
 * 设置标题栏的方法应该根据项目需要进行编写，下面提供了参考示例。
 *
 * @author Dylan Cai
 */
@Suppress("unused")
abstract class BaseActivity : AppCompatActivity() {

  lateinit var loadingHelper: LoadingHelper
    private set

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

  /**
   * 这是添加通用标题栏的示例方法。
   */
  @JvmOverloads
  fun setToolbar(
    title: String, type: NavIconType = NavIconType.NONE,
    menuId: Int = 0, listener: ((MenuItem) -> Boolean)? = null
  ) {
    loadingHelper.register(ViewType.TITLE, ToolbarAdapter(title, type, menuId, listener))
    loadingHelper.setDecorHeader(ViewType.TITLE)
  }

  /**
   * 这是添加有联动效果的标题栏的示例方法。
   */
  fun setScrollingToolbar(title: String) {
    loadingHelper.setDecorAdapter(ScrollingDecorAdapter(title))
  }

  fun showLoadingView() = loadingHelper.showLoadingView()

  fun showContentView() = loadingHelper.showContentView()

  fun showErrorView() = loadingHelper.showErrorView()

  fun showEmptyView() = loadingHelper.showEmptyView()

  fun showCustomView(viewType: Any) = loadingHelper.showView(viewType)

  open fun onReload() {}
}