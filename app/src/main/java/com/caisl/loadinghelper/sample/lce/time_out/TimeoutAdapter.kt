package com.caisl.loadinghelper.sample.lce.time_out

import android.view.LayoutInflater
import android.view.ViewGroup
import com.caisl.loadinghelper.LoadingHelper
import com.caisl.loadinghelper.sample.R

/**
 * @author caisl
 * @since 2019/6/14
 */
class TimeoutAdapter : LoadingHelper.Adapter<LoadingHelper.ViewHolder>() {
  override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): LoadingHelper.ViewHolder {
    return LoadingHelper.ViewHolder(inflater.inflate(R.layout.lce_layout_time_out, parent, false))
  }

  override fun onBindViewHolder(holder: LoadingHelper.ViewHolder) {

  }
}