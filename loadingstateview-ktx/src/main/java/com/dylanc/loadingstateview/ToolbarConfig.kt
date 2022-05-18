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
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import androidx.annotation.DrawableRes
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

enum class NavBtnType {
  ICON, TEXT, ICON_TEXT, NONE
}

class ToolbarConfig(
  var title: String? = null,
  var navBtnType: NavBtnType = NavBtnType.ICON,
  val extras: HashMap<String, Any?> = HashMap(),
) {
  @DrawableRes
  var navIcon: Int? = null
  var navText: String? = null
    private set
  var onNavClickListener = View.OnClickListener {
    var context: Context? = it.context
    while (context is ContextWrapper) {
      if (context is Activity) {
        context.finish()
        return@OnClickListener
      }
      context = context.baseContext
    }
  }
    private set

  @DrawableRes
  var rightIcon: Int? = null
    private set
  var rightText: String? = null
    private set
  var onRightClickListener: View.OnClickListener? = null
    private set

  fun navIcon(@DrawableRes icon: Int? = navIcon, listener: View.OnClickListener) {
    navIcon = icon
    onNavClickListener = listener
  }

  fun navText(text: String, listener: View.OnClickListener) {
    navText = text
    onNavClickListener = listener
  }

  fun rightIcon(@DrawableRes icon: Int, listener: View.OnClickListener) {
    rightIcon = icon
    onRightClickListener = listener
  }

  fun rightText(text: String, listener: View.OnClickListener) {
    rightText = text
    onRightClickListener = listener
  }
}

fun <T> toolbarExtras() = object : ReadWriteProperty<ToolbarConfig, T?> {
  @Suppress("UNCHECKED_CAST")
  override fun getValue(thisRef: ToolbarConfig, property: KProperty<*>): T? =
    thisRef.extras[property.name] as? T

  override fun setValue(thisRef: ToolbarConfig, property: KProperty<*>, value: T?) {
    thisRef.extras[property.name] = value
  }
}