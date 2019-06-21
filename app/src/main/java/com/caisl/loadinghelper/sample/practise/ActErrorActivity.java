package com.caisl.loadinghelper.sample.practise;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.caisl.loadinghelper.LoadingHelper;
import com.caisl.loadinghelper.sample.R;
import com.caisl.loadinghelper.sample.lce.title.TitleAdapter;
import com.caisl.loadinghelper.sample.lce.title.TitleConfig;

import static com.caisl.loadinghelper.LoadingHelper.VIEW_TYPE_TITLE;

/**
 * @author caisl
 * @since 2019/6/20
 */
public class ActErrorActivity extends AppCompatActivity {

  private LoadingHelper mLoadingHelper;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_content);
    mLoadingHelper = new LoadingHelper(this);
    mLoadingHelper.registerAdapter(VIEW_TYPE_TITLE,new TitleAdapter(TitleConfig.Type.BACK));
    mLoadingHelper.addTitleView();
    mLoadingHelper.setRetryTask(new Runnable() {
      @Override
      public void run() {
        loadSuccess();
      }
    });
    loadFailure();
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
