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

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.dylanc.loadingstateview.LoadingStateView
import com.dylanc.loadingstateview.sample.kotlin.R

/**
 * @author Dylan Cai
 */
class ScrollingDecorViewDelegate(private val title: String) : LoadingStateView.DecorViewDelegate() {

  override fun onCreateDecorView(context: Context, inflater: LayoutInflater): View {
    val view = inflater.inflate(R.layout.layout_scrolling_toolbar, null)
    val toolbar: Toolbar = view.findViewById(R.id.toolbar)
    toolbar.title = title
    toolbar.setNavigationOnClickListener {
      if (context is Activity) context.finish()
    }
    return view
  }

  override fun getContentParent(decorView: View): ViewGroup {
    return decorView.findViewById(R.id.content_parent)
  }
}