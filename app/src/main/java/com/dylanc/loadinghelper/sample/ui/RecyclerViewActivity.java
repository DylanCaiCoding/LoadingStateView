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

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.ViewType;
import com.dylanc.loadinghelper.sample.R;
import com.dylanc.loadinghelper.sample.adapter.CoolLoadingAdapter;
import com.dylanc.loadinghelper.sample.adapter.NavIconType;
import com.dylanc.loadinghelper.sample.utils.HttpUtils;
import com.dylanc.loadinghelper.sample.utils.ToolbarUtils;


/**
 * @author Dylan Cai
 */
public class RecyclerViewActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recycler_view);
    ToolbarUtils.setToolbar(this,"RecyclerView(cool loading)", NavIconType.BACK);

    RecyclerView recyclerView = findViewById(R.id.recycler_view);
    recyclerView.setNestedScrollingEnabled(false);
    recyclerView.setAdapter(new ImageAdapter());
    recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
  }

  public static class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
      final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_image, parent, false);
      return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
      holder.showImage(HttpUtils.getRandomImageUrl());
    }

    @Override
    public int getItemCount() {
      return 10;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

      private final LoadingHelper loadingHelper;
      ImageView imageView;
      private String url;

      ViewHolder(@NonNull View itemView) {
        super(itemView);
        loadingHelper = new LoadingHelper(itemView.findViewById(R.id.loading_view));
        loadingHelper.register(ViewType.LOADING, new CoolLoadingAdapter());
        loadingHelper.setOnReloadListener(() -> showImage(url));
        imageView = itemView.findViewById(R.id.image_view);
      }

      void showImage(String url) {
        this.url = url;
        loadingHelper.showLoadingView();
        Glide.with(imageView.getContext())
            .load(url)
            .listener(new RequestListener<Drawable>() {
              @Override
              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target,
                                          boolean isFirstResource) {
                loadingHelper.showErrorView();
                return false;
              }

              @Override
              public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                             DataSource dataSource, boolean isFirstResource) {
                loadingHelper.showContentView();
                return false;
              }
            })
            .into(imageView);
      }

    }
  }
}
