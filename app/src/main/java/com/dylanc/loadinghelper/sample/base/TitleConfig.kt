package com.dylanc.loadinghelper.sample.base


import android.view.View

/**
 * @author Dylan Cai
 * @since 2019/4/9
 */
open class TitleConfig(
  var titleText: String,
  var type: Type
) {
  var rightIcon: Int = 0
    private set
  var rightText: String? = null
    private set
  var onRightBtnClickListener: View.OnClickListener? = null
    private set

  fun setRightBtn(rightIcon: Int, onRightBtnClickListener: View.OnClickListener?) {
    this.rightIcon = rightIcon
    this.onRightBtnClickListener = onRightBtnClickListener
  }

  fun setRightBtn(rightText: String, onRightBtnClickListener: View.OnClickListener?) {
    this.rightText = rightText
    this.onRightBtnClickListener = onRightBtnClickListener
  }

  enum class Type {
    BACK, NO_BACK
  }
}
