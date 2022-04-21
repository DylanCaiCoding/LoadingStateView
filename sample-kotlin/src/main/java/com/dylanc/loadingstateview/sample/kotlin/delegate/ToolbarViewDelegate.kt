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

package com.dylanc.loadingstateview.sample.kotlin.delegate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dylanc.loadingstateview.NavBtnType
import com.dylanc.loadingstateview.ToolbarConfig
import com.dylanc.loadingstateview.BaseToolbarViewDelegate
import com.dylanc.loadingstateview.sample.kotlin.databinding.LayoutToolbarBinding
import com.dylanc.loadingstateview.toolbarExtras

var ToolbarConfig.rightTextColor: Int? by toolbarExtras()

class ToolbarViewDelegate : BaseToolbarViewDelegate() {
  private lateinit var binding: LayoutToolbarBinding

  override fun onCreateToolbar(inflater: LayoutInflater, parent: ViewGroup): View {
    binding = LayoutToolbarBinding.inflate(inflater, parent, false)
    return binding.root
  }

  override fun bind(config: ToolbarConfig) {
    binding.apply {
      tvTitle.text = config.title

      when (config.navBtnType) {
        NavBtnType.ICON -> {
          config.navIcon?.let { ivLeft.setImageResource(it) }
          ivLeft.setOnClickListener(config.onNavClickListener)
          tvLeft.visibility = View.GONE
          ivLeft.visibility = View.VISIBLE
        }
        NavBtnType.TEXT -> {
          tvLeft.text = config.navText
          tvLeft.setOnClickListener(config.onNavClickListener)
          tvLeft.visibility = View.VISIBLE
          ivLeft.visibility = View.GONE
        }
        NavBtnType.ICON_TEXT -> {
          config.navIcon?.let { ivLeft.setImageResource(it) }
          tvLeft.text = config.navText
          ivLeft.setOnClickListener(config.onNavClickListener)
          tvLeft.setOnClickListener(config.onNavClickListener)
          tvLeft.visibility = View.VISIBLE
          ivLeft.visibility = View.VISIBLE
        }
        NavBtnType.NONE -> {
          ivLeft.visibility = View.GONE
          tvLeft.visibility = View.GONE
        }
      }

      if (config.rightText != null) {
        tvRight.text = config.rightText
        tvRight.setOnClickListener(config.onRightClickListener)
        tvRight.visibility = View.VISIBLE
        config.rightTextColor?.let { tvRight.setTextColor(it) }
      } else {
        tvRight.visibility = View.GONE
      }

      if (config.rightIcon != null) {
        ivRight.setImageResource(config.rightIcon!!)
        ivRight.setOnClickListener(config.onRightClickListener)
        ivRight.visibility = View.VISIBLE
      } else {
        ivRight.visibility = View.GONE
      }
    }
  }
}