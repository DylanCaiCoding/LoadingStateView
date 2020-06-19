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

package com.dylanc.loadinghelper.sample.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.dylanc.loadinghelper.sample.R;
import com.dylanc.loadinghelper.sample.base.NavIconType;
import com.dylanc.loadinghelper.sample.ui.fragment.LoadingFragment;
import com.dylanc.loadinghelper.sample.utils.ToolbarUtils;

import static com.dylanc.loadinghelper.sample.ui.fragment.LoadingFragment.VIEW_TYPE_EMPTY;

/**
 * @author Dylan Cai
 */
public class FragmentEmptyActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fragment);
    ToolbarUtils.setToolbar(this,"Fragment(empty)", NavIconType.BACK);
    final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.add(R.id.content_view, LoadingFragment.newInstance(VIEW_TYPE_EMPTY));
    transaction.commit();
  }

}
