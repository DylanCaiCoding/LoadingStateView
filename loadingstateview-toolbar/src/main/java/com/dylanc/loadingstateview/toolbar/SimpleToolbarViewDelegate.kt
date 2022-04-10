package com.dylanc.loadingstateview.toolbar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dylanc.loadingstateview.toolbar.databinding.LayoutToolbarBinding

class SimpleToolbarViewDelegate : ToolbarViewDelegate() {
  private lateinit var binding: LayoutToolbarBinding

  override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View {
    binding = LayoutToolbarBinding.inflate(inflater, parent, false)
    return binding.root
  }

  override fun onBindView(view: View, config: ToolbarConfig) {
    binding.apply {
      tvTitle.text = config.title
      when (config.navBtnType) {
        NavBtnType.ICON -> {
          ivLeft.setImageResource(config.navIcon)
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
        NavBtnType.ICON_TEXT->{
          ivLeft.setImageResource(config.navIcon)
          tvLeft.text = config.navText
          ivLeft.setOnClickListener(config.navClickListener)
          tvLeft.setOnClickListener(config.navClickListener)
          tvLeft.visibility = View.VISIBLE
          ivLeft.visibility = View.VISIBLE
        }
        NavBtnType.NONE-> {
          ivLeft.visibility = View.GONE
          tvLeft.visibility = View.GONE
        }
      }
    }
  }
}