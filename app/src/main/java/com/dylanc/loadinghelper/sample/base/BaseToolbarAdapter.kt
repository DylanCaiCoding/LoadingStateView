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
import com.dylanc.loadinghelper.LoadingHelper

/**
 * @author Dylan Cai
 */
abstract class BaseToolbarAdapter<T : ToolbarConfig, VH : LoadingHelper.ViewHolder> :
  LoadingHelper.Adapter<VH>() {

  lateinit var config: T
}

open class ToolbarConfig @JvmOverloads constructor(
  var titleText: String,
  var type: NavIconType,
  var menuId: Int = 0,
  var onMenuItemClick: ((MenuItem) -> Boolean)? = null
)

enum class NavIconType {
  BACK, NONE
}
