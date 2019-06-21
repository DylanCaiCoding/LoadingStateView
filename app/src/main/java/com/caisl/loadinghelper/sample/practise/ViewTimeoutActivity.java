package com.caisl.loadinghelper.sample.practise;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.caisl.loadinghelper.LoadingHelper;
import com.caisl.loadinghelper.sample.R;
import com.caisl.loadinghelper.sample.lce.title.TitleAdapter;
import com.caisl.loadinghelper.sample.lce.title.TitleConfig;

import static com.caisl.loadinghelper.LoadingHelper.VIEW_TYPE_TITLE;

/**
 * @author caisl
 * @since 2019/6/20
 */
public class ViewTimeoutActivity extends AppCompatActivity {

  private LoadingHelper mLoadingHelper;
  private ImageView mIvLady;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view);
    mIvLady = findViewById(R.id.iv_lady);
    mLoadingHelper = new LoadingHelper(this);
    mLoadingHelper.registerAdapter(VIEW_TYPE_TITLE,new TitleAdapter(TitleConfig.Type.BACK));
    mLoadingHelper.addTitleView();
//    loadFailure();

    final LoadingHelper loadingHelper = new LoadingHelper(mIvLady);
    loadingHelper.showLoadingView();
    Glide.with(this)
        .load("https://ws1.sinaimg.cn/large/0065oQSqly1g0ajj4h6ndj30sg11xdmj.jpg")
        .listener(new RequestListener<Drawable>() {
          @Override
          public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            loadingHelper.showErrorView();
            return false;
          }

          @Override
          public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            loadingHelper.showContentView();
            return false;
          }
        })
        .into(mIvLady);
  }

  private void loadSuccess() {
    mLoadingHelper.showLoadingView();
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        mLoadingHelper.showContentView();
      }
    },2000);
  }

  private void loadFailure() {
    mLoadingHelper.showLoadingView();
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        mLoadingHelper.showErrorView();
      }
    },2000);
  }

}
