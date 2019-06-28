package com.caisl.loadinghelper.sample.practise;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.caisl.loadinghelper.LoadingHelper;
import com.caisl.loadinghelper.sample.R;
import com.caisl.loadinghelper.sample.adapter.*;
import com.caisl.loadinghelper.sample.utils.HttpUtils;

import static com.caisl.loadinghelper.sample.adapter.TimeoutAdapter.VIEW_TYPE_TIMEOUT;

/**
 * @author caisl
 * @since 2019/6/20
 */
public class ViewPlaceholderActivity extends AppCompatActivity {

  private LoadingHelper mViewLoadingHelper;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view);
    LoadingHelper loadingHelper = new LoadingHelper(this);
    loadingHelper.registerTitleAdapter(new TitleAdapter("View(placeholder)", TitleConfig.Type.BACK));
    loadingHelper.addTitleView();

    mViewLoadingHelper = new LoadingHelper(findViewById(R.id.content));
    mViewLoadingHelper.registerAdapter(LoadingHelper.VIEW_TYPE_LOADING,new PlaceholderAdapter());
    loadSuccess();
  }

  private void loadSuccess() {
    mViewLoadingHelper.showLoadingView();
    HttpUtils.requestSuccess(mCallback);
  }

  private HttpUtils.Callback mCallback = new HttpUtils.Callback() {
    @Override
    public void onSuccess() {
      mViewLoadingHelper.showContentView();
    }

    @Override
    public void onFailure() {
      mViewLoadingHelper.showLoadingView();
    }
  };

}
