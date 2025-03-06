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

package com.dylanc.loadingstateview.sample.java;

import android.app.Application;

import com.dylanc.loadingstateview.LoadingStateView;
import com.dylanc.loadingstateview.sample.java.animation.FadeAnimatable;
import com.dylanc.loadingstateview.sample.java.delegate.EmptyViewDelegate;
import com.dylanc.loadingstateview.sample.java.delegate.ErrorViewDelegate;
import com.dylanc.loadingstateview.sample.java.delegate.LoadingViewDelegate;

/**
 * @author Dylan Cai
 */
public class App extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    LoadingStateView.setViewDelegatePool(pool ->
        pool.register(new LoadingViewDelegate(), new ErrorViewDelegate(), new EmptyViewDelegate()));
    LoadingStateView.setDefaultAnimatable(new FadeAnimatable());
  }
}
