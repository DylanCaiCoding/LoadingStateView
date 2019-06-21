package com.caisl.loadinghelper.sample.practise.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.caisl.loadinghelper.sample.R;
import com.caisl.loadinghelper.sample.lce.loading.LoadingAdapter;
import com.caisl.loadinghelper.sample.lce.title.TitleAdapter;
import com.caisl.loadinghelper.sample.lce.title.TitleConfig;
import com.caisl.loadinghelper.sample.widget.LCEActivity;

import static com.caisl.loadinghelper.LoadingHelper.VIEW_TYPE_LOADING;
import static com.caisl.loadinghelper.LoadingHelper.VIEW_TYPE_TITLE;

/**
 * @author caisl
 * @since 2019/6/20
 */
public class MVPLoadActivity extends LCEActivity implements MVPLoadContract.IMVPLoadView {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_content);

    getLoadingHelper().registerAdapter(VIEW_TYPE_LOADING,new LoadingAdapter());
    getLoadingHelper().registerAdapter(VIEW_TYPE_TITLE, new TitleAdapter(TitleConfig.Type.BACK));
    getLoadingHelper().addTitleView();

    final MVPLoadPresenter presenter = new MVPLoadPresenter(this);
    presenter.loadData();
  }
}
