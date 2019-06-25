package com.caisl.loadinghelper.sample.practise;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.caisl.loadinghelper.LoadingHelper;
import com.caisl.loadinghelper.sample.R;
import com.caisl.loadinghelper.sample.adapter.TitleAdapter;
import com.caisl.loadinghelper.sample.adapter.TitleConfig;
import com.caisl.loadinghelper.sample.utils.HttpUtils;

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
    mLoadingHelper.setRetryTask(new Runnable() {
      @Override
      public void run() {
        loadSuccess();
      }
    });
    mLoadingHelper.registerTitleAdapter(new TitleAdapter("Activity(error)", TitleConfig.Type.BACK));
    mLoadingHelper.addTitleView();
    loadFailure();
  }

  private void loadSuccess() {
    mLoadingHelper.showLoadingView();
    HttpUtils.requestSuccess(mCallback);
  }

  private void loadFailure() {
    mLoadingHelper.showLoadingView();
    HttpUtils.requestFailure(mCallback);
  }

  private HttpUtils.Callback mCallback = new HttpUtils.Callback() {
    @Override
    public void onSuccess() {
      mLoadingHelper.showContentView();
    }

    @Override
    public void onFailure() {
      mLoadingHelper.showErrorView();
    }
  };
}
