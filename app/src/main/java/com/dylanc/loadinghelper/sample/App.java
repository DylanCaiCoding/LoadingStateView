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

package com.dylanc.loadinghelper.sample;

import android.app.Application;

import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.ViewType;
import com.dylanc.loadinghelper.sample.adapter.EmptyAdapter;
import com.dylanc.loadinghelper.sample.adapter.ErrorAdapter;
import com.dylanc.loadinghelper.sample.adapter.LoadingAdapter;
import com.dylanc.loadinghelper.sample.adapter.ToolbarAdapter;

import kotlin.Unit;

/**
 * @author Dylan Cai
 */
public class App extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    LoadingHelper.setDefaultAdapterPool(adapterPool -> {
      adapterPool.register(ViewType.LOADING, new LoadingAdapter());
      adapterPool.register(ViewType.ERROR, new ErrorAdapter());
      adapterPool.register(ViewType.EMPTY, new EmptyAdapter());
      return Unit.INSTANCE;
    });
  }
}
