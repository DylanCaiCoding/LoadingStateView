package com.dylanc.loadingstateview.toolbar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dylanc.loadingstateview.toolbar.databinding.LayoutToolbarBinding

class DefaultToolbarViewDelegate : ToolbarViewDelegate() {
  private lateinit var binding: LayoutToolbarBinding

  override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View {
    binding = LayoutToolbarBinding.inflate(inflater, parent, false)
    return binding.root
  }

  override fun onBindView(view: View, config: ToolbarConfig) {
    binding.apply {
      tvTitle.text = config.title
    }
  }
}