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

package com.dylanc.loadingstateview

import android.app.Activity
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes

enum class NavBtnType {
  ICON, TEXT, ICON_TEXT, NONE
}

open class ToolbarConfig(
  var title: String? = null,
  var navBtnType: NavBtnType = NavBtnType.ICON,
  var navText: String? = null,
  @DrawableRes var navIcon: Int = ToolbarUI.navIcon,
  var navClickListener: View.OnClickListener = View.OnClickListener {
    if (it.context is Activity) (it.context as Activity).finish()
  },
  @DrawableRes var rightIcon: Int = -1,
  var rightText: String? = null,
  var rightClickListener: View.OnClickListener? = null,
  @DrawableRes var rightSecondIcon: Int = -1,
  var rightSecondClickListener: View.OnClickListener? = null,
  var height: Float = ToolbarUI.height,
  var titleTextSize: Float = ToolbarUI.titleTextSize,
  var rightTextSize: Float = ToolbarUI.rightTextSize,
  var rightSecondTextSize: Float = ToolbarUI.rightSecondTextSize,
  @ColorInt var titleTextColor: Int = ToolbarUI.titleTextColor,
  @ColorInt var navTextColor: Int = ToolbarUI.navTextColor,
  @ColorInt var rightTextColor: Int = ToolbarUI.rightTextColor,
  @ColorInt var backgroundColor: Int = ToolbarUI.backgroundColor,
  var navIconSize: Float = ToolbarUI.navIconSize,
  var rightIconSize: Float = ToolbarUI.rightIconSize,
  var rightSecondIconSize: Float = ToolbarUI.rightSecondIconSize,
)

fun ToolbarConfig.navText(text: String, listener: View.OnClickListener) {
  navText = text
  navClickListener = listener
}

fun ToolbarConfig.navIcon(@DrawableRes icon: Int, listener: View.OnClickListener) {
  navIcon = icon
  navClickListener = listener
}

fun ToolbarConfig.rightIcon(@DrawableRes icon: Int, listener: View.OnClickListener) {
  rightIcon = icon
  rightClickListener = listener
}

fun ToolbarConfig.rightText(text: String, listener: View.OnClickListener) {
  rightText = text
  rightClickListener = listener
}

fun ToolbarConfig.rightSecondIcon(@DrawableRes icon: Int, listener: View.OnClickListener) {
  rightSecondIcon = icon
  rightSecondClickListener = listener
}

object ToolbarUI {
  var height = -1f
  var titleTextSize = -1f
  var rightTextSize = -1f
  var rightSecondTextSize = -1f

  @ColorInt
  var titleTextColor = -1

  @ColorInt
  var navTextColor = -1

  @ColorInt
  var rightTextColor = -1

  @ColorInt
  var backgroundColor = -1

  @DrawableRes
  var navIcon: Int = -1
  var navIconSize = -1f
  var rightIconSize = -1f
  var rightSecondIconSize = -1f
}
