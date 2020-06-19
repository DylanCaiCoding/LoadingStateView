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

package com.dylanc.loadinghelper.sample.utils;

import android.app.Activity;
import android.view.MenuItem;

import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.ViewType;
import com.dylanc.loadinghelper.sample.adapter.ScrollDecorAdapter;
import com.dylanc.loadinghelper.sample.base.BaseToolbarAdapter;
import com.dylanc.loadinghelper.sample.base.NavIconType;
import com.dylanc.loadinghelper.sample.base.ToolbarConfig;

import kotlin.jvm.functions.Function1;

/**
 * @author Dylan Cai
 */
public class ToolbarUtils {
  public static LoadingHelper setToolbar(Activity activity, String title, NavIconType type) {
    return setToolbar(activity, title, type, 0, null);
  }

  public static LoadingHelper setToolbar(Activity activity, String title, NavIconType type, int menuId,
                                         Function1<? super MenuItem, Boolean> onMenuItemClick) {
    LoadingHelper loadingHelper = new LoadingHelper(activity);
    BaseToolbarAdapter<ToolbarConfig, ?> adapter = loadingHelper.getAdapter(ViewType.TITLE);
    ToolbarConfig config = new ToolbarConfig(title, type, menuId, onMenuItemClick);
    adapter.setConfig(config);
    loadingHelper.setDecorHeader(ViewType.TITLE);
    return loadingHelper;
  }

  public static LoadingHelper setScrollingToolbar(Activity activity, String title) {
    LoadingHelper loadingHelper = new LoadingHelper(activity);
    loadingHelper.setDecorAdapter(new ScrollDecorAdapter(title));
    return loadingHelper;
  }
}
