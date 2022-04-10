package com.dylanc.loadingstateview.toolbar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dylanc.loadingstateview.NavBtnType
import com.dylanc.loadingstateview.ToolbarConfig
import com.dylanc.loadingstateview.ToolbarViewDelegate
import com.dylanc.loadingstateview.toolbar.databinding.LayoutToolbarBinding

class SimpleToolbarViewDelegate : ToolbarViewDelegate() {
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
          if (config.navIcon > 0) ivLeft.setImageResource(config.navIcon)
          ivLeft.setOnClickListener(config.navClickListener)
          tvLeft.visibility = View.GONE
          ivLeft.visibility = View.VISIBLE
        }
        NavBtnType.TEXT -> {
          tvLeft.text = config.navText
          tvLeft.setOnClickListener(config.navClickListener)
          tvLeft.visibility = View.VISIBLE
          ivLeft.visibility = View.GONE
        }
        NavBtnType.ICON_TEXT -> {
          if (config.navIcon > 0) ivLeft.setImageResource(config.navIcon)
          tvLeft.text = config.navText
          ivLeft.setOnClickListener(config.navClickListener)
          tvLeft.setOnClickListener(config.navClickListener)
          tvLeft.visibility = View.VISIBLE
          ivLeft.visibility = View.VISIBLE
        }
        NavBtnType.NONE -> {
          ivLeft.visibility = View.GONE
          tvLeft.visibility = View.GONE
        }
      }
    }

    if (config.rightText != null) {
      binding.tvRight.text = config.rightText
      binding.tvRight.setOnClickListener(config.rightClickListener)
      binding.tvRight.visibility = View.VISIBLE
    } else {
      binding.tvRight.visibility = View.GONE
    }

    if (config.rightIcon > 0) {
      binding.ivRight.setImageResource(config.rightIcon)
      binding.ivRight.setOnClickListener(config.rightClickListener)
      binding.ivRight.visibility = View.VISIBLE
    } else {
      binding.ivRight.visibility = View.GONE
    }

    if (config.rightSecondIcon > 0) {
      binding.ivRightSecond.setImageResource(config.rightSecondIcon)
      binding.ivRightSecond.setOnClickListener(config.rightSecondClickListener)
      binding.ivRightSecond.visibility = View.VISIBLE
    } else {
      binding.ivRightSecond.visibility = View.GONE
    }
  }
}