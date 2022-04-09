@file:Suppress("unused")

package com.dylanc.loadingstateview.toolbar

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

enum class NavIconType {
  ICON, TEXT, NONE
}

open class ToolbarConfig(
  val context: Context,
  var title: String? = null,
  var navIconType: NavIconType = NavIconType.ICON,

  var leftText: String? = null,
  @DrawableRes var leftIcon: Int = ToolbarUI.leftIcon,
  var leftBtnClickListener: View.OnClickListener? = null,

  @DrawableRes var rightIcon: Int = -1,
  var rightText: String? = null,
  var rightBtnClickListener: View.OnClickListener? = null,

  @DrawableRes var rightSecondIcon: Int = -1,
  var rightSecondText: String? = null,
  var rightSecondBtnClickListener: View.OnClickListener? = null,

  var height: Float = ToolbarUI.height,
  var titleTextSize: Float = ToolbarUI.titleTextSize,
  @ColorInt var titleTextColor: Int = ToolbarUI.titleTextColor,
  @ColorInt var backgroundColor: Int = ToolbarUI.backgroundColor
)

fun ToolbarConfig.setLeftIcon(@DrawableRes icon: Int, listener: View.OnClickListener) {
  leftIcon = icon
  leftBtnClickListener = listener
}

fun ToolbarConfig.setRightText(@StringRes resId: Int, listener: View.OnClickListener) {
  rightText = context.getString(resId)
  rightBtnClickListener = listener
}

fun ToolbarConfig.setRightText(text: String, listener: View.OnClickListener) {
  rightText = text
  rightBtnClickListener = listener
}

fun ToolbarConfig.setRightIcon(@DrawableRes icon: Int, listener: View.OnClickListener) {
  rightIcon = icon
  rightBtnClickListener = listener
}

fun ToolbarConfig.setRightSecondText(text: String, listener: View.OnClickListener) {
  rightSecondText = text
  rightSecondBtnClickListener = listener
}

fun ToolbarConfig.setRightSecondText(@StringRes resId: Int, listener: View.OnClickListener) {
  this.rightText = context.getString(resId)
  rightSecondBtnClickListener = listener
}

fun ToolbarConfig.setRightSecondIcon(@DrawableRes icon: Int, listener: View.OnClickListener) {
  rightSecondIcon = icon
  rightSecondBtnClickListener = listener
}

object ToolbarUI {
  var height = -1f
  var titleTextSize = 18f

  @ColorInt
  var titleTextColor = Color.BLACK

  @ColorInt
  var backgroundColor = Color.WHITE

  @DrawableRes
  var leftIcon: Int = -1
}
