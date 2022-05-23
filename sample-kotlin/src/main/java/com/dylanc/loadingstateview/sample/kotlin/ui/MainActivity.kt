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

package com.dylanc.loadingstateview.sample.kotlin.ui

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import com.dylanc.loadingstateview.NavBtnType
import com.dylanc.loadingstateview.sample.kotlin.R
import com.dylanc.loadingstateview.sample.kotlin.base.BaseBindingActivity
import com.dylanc.loadingstateview.sample.kotlin.databinding.ActivityMainBinding
import com.dylanc.loadingstateview.sample.kotlin.delegate.LoadingViewDelegate
import com.dylanc.loadingstateview.sample.kotlin.delegate.rightTextColor

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setToolbar(R.string.app_name, NavBtnType.NONE) {
      rightTextColor = Color.RED
      rightText("Delete") {
        Toast.makeText(this@MainActivity, "Delete", Toast.LENGTH_SHORT).show()
      }
    }
    showErrorView()
  }

  override fun onReload() {
    showLoadingView()
    Handler(Looper.getMainLooper()).postDelayed({
      showContentView()
    }, 2000)
  }
}
