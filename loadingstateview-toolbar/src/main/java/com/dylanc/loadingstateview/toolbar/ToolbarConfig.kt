@file:Suppress("unused")

package com.dylanc.loadingstateview.toolbar

import android.app.Activity
import android.graphics.Color
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
  @DrawableRes var navIcon: Int = ToolbarUI.leftIcon,
  var navClickListener: View.OnClickListener = View.OnClickListener {
    if (it.context is Activity) (it.context as Activity).finish()
  },

  @DrawableRes var rightIcon: Int = -1,
  var rightText: String? = null,
  var rightClickListener: View.OnClickListener? = null,

  @DrawableRes var rightSecondIcon: Int = -1,
  var rightSecondText: String? = null,
  var rightSecondClickListener: View.OnClickListener? = null,

  var height: Float = ToolbarUI.height,
  var titleTextSize: Float = ToolbarUI.titleTextSize,
  @ColorInt var titleTextColor: Int = ToolbarUI.titleTextColor,
  @ColorInt var backgroundColor: Int = ToolbarUI.backgroundColor
)

fun ToolbarConfig.setNavText(text: String, listener: View.OnClickListener) {
  navText = text
  navClickListener = listener
}

fun ToolbarConfig.setNavIcon(@DrawableRes icon: Int, listener: View.OnClickListener) {
  navIcon = icon
  navClickListener = listener
}

fun ToolbarConfig.setRightIcon(@DrawableRes icon: Int, listener: View.OnClickListener) {
  rightIcon = icon
  rightClickListener = listener
}
fun ToolbarConfig.setRightText(text: String, listener: View.OnClickListener) {
  rightText = text
  rightClickListener = listener
}

fun ToolbarConfig.setRightSecondText(text: String, listener: View.OnClickListener) {
  rightSecondText = text
  rightSecondClickListener = listener
}

fun ToolbarConfig.setRightSecondIcon(@DrawableRes icon: Int, listener: View.OnClickListener) {
  rightSecondIcon = icon
  rightSecondClickListener = listener
}

object ToolbarUI {
  var height = -1f
  var titleTextSize = 18f

  @ColorInt
  var titleTextColor = Color.BLACK

  @ColorInt
  var backgroundColor = Color.WHITE

  @DrawableRes
  var leftIcon: Int = R.drawable.ic_arrow_back_ios
}
