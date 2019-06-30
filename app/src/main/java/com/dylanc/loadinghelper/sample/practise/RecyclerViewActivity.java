package com.dylanc.loadinghelper.sample.practise;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.ViewType;
import com.dylanc.loadinghelper.sample.R;
import com.dylanc.loadinghelper.sample.adapter.WaterLoadingAdapter;
import com.dylanc.loadinghelper.sample.adapter.TitleAdapter;
import com.dylanc.loadinghelper.sample.adapter.TitleConfig;
import com.dylanc.loadinghelper.sample.utils.HttpUtils;


/**
 * @author Dylan Cai
 * @since 2019/6/20
 */
public class RecyclerViewActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recycler_view);
    LoadingHelper loadingHelper = new LoadingHelper(this);
    loadingHelper.register(ViewType.TITLE, new TitleAdapter("RecyclerView(cool loading)", TitleConfig.Type.BACK));
    loadingHelper.addTitleView();

    RecyclerView recyclerView = findViewById(R.id.recycler_view);
    recyclerView.setAdapter(new ImageAdapter());
    recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
  }

  public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
      final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_image, parent, false);
      return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
      holder.showImage(HttpUtils.getRandomUrl());
    }

    @Override
    public int getItemCount() {
      return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements LoadingHelper.OnReloadListener {

      private final LoadingHelper mLoadingHelper;
      ImageView mImageView;
      private String mUrl;

      ViewHolder(@NonNull View itemView) {
        super(itemView);
        mLoadingHelper = new LoadingHelper(itemView.findViewById(R.id.loading_view));
        mLoadingHelper.register(ViewType.LOADING, new WaterLoadingAdapter());
        mLoadingHelper.setOnReloadListener(this);
        mImageView = itemView.findViewById(R.id.image_view);
      }

      void showImage(String url) {
        mUrl = url;
        mLoadingHelper.showLoadingView();
        Glide.with(mImageView.getContext())
            .load(url)
            .listener(new RequestListener<Drawable>() {
              @Override
              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target,
                                          boolean isFirstResource) {
                mLoadingHelper.showErrorView();
                return false;
              }

              @Override
              public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                             DataSource dataSource, boolean isFirstResource) {
                mLoadingHelper.showContentView();
                return false;
              }
            })
            .into(mImageView);
      }

      @Override
      public void onReload() {
        showImage(mUrl);
      }
    }
  }
}
