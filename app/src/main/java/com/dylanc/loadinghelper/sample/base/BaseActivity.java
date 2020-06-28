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

package com.dylanc.loadinghelper.sample.base;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.ViewType;
import com.dylanc.loadinghelper.sample.adapter.NavIconType;
import com.dylanc.loadinghelper.sample.adapter.ToolbarAdapter;

/**
 * 这是耦合度较低的封装方式，没有任何抽象方法，可以很方便地将基类里的代码拷贝到其它项目的基类里使用。
 * <p>
 * 使用该基类时注意以下事项：
 * 将主题设置成 NoActionBar，不然会有报错，后续会将这个问题修复。
 * 显示对应视图之前需要注册适配器，可以设置全局适配器，某个页面想修改样式时再注册个新的适配器。
 * 设置标题栏的方法应该根据项目需要进行编写，下面提供了参考示例。
 *
 * @author Dylan Cai
 */
@SuppressWarnings("unused")
public class BaseActivity extends AppCompatActivity {

  private LoadingHelper loadingHelper;

  @Override
  public void setContentView(int layoutResID) {
    this.setContentView(layoutResID, android.R.id.content);
  }

  public void setContentView(int layoutResID, @IdRes int contentViewId) {
    this.setContentView(layoutResID, contentViewId, null);
  }

  public void setContentView(int layoutResID, @IdRes int contentViewId, LoadingHelper.ContentAdapter<?> contentAdapter) {
    super.setContentView(layoutResID);
    loadingHelper = new LoadingHelper(findViewById(contentViewId), contentAdapter);
    loadingHelper.setOnReloadListener(this::onReload);
  }

  public void setToolbar(String title, NavIconType type) {
    setToolbar(title, type, 0);
  }

  /**
   * 添加标题栏的示例方法，请根据自己的需求进行修改
   */
  public void setToolbar(String title, NavIconType type, int menuId) {
    loadingHelper.register(ViewType.TITLE, new ToolbarAdapter(title, type, menuId, this::onOptionsItemSelected));
    loadingHelper.setDecorHeader(ViewType.TITLE);
  }

  public void showLoadingView() {
    loadingHelper.showLoadingView();
  }

  public void showContentView() {
    loadingHelper.showContentView();
  }

  public void showErrorView() {
    loadingHelper.showErrorView();
  }

  public void showEmptyView() {
    loadingHelper.showEmptyView();
  }

  public void showCustomView(Object viewType) {
    loadingHelper.showView(viewType);
  }

  protected void onReload() {
  }
}
