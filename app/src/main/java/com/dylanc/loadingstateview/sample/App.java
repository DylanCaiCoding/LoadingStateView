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

package com.dylanc.loadingstateview.sample;

import android.app.Application;

import com.dylanc.loadingstateview.LoadingStateView;
import com.dylanc.loadingstateview.ViewType;
import com.dylanc.loadingstateview.sample.delegate.EmptyViewDelegate;
import com.dylanc.loadingstateview.sample.delegate.ErrorViewDelegate;
import com.dylanc.loadingstateview.sample.delegate.LoadingViewDelegate;

import kotlin.Unit;

/**
 * @author Dylan Cai
 */
public class App extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    LoadingStateView.setViewDelegatePool(pool -> {
      pool.register(ViewType.LOADING, new LoadingViewDelegate());
      pool.register(ViewType.ERROR, new ErrorViewDelegate());
      pool.register(ViewType.EMPTY, new EmptyViewDelegate());
      return Unit.INSTANCE;
    });
  }
}
