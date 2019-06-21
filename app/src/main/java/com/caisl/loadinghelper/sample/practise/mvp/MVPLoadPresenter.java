package com.caisl.loadinghelper.sample.practise.mvp;

/**
 * @author caisl
 * @since 2019/6/20
 */
public class MVPLoadPresenter implements MVPLoadContract.IMVPLoadPresenter {

  private final MVPLoadContract.IMVPLoadView mView;
  private final MVPLoadContract.IMVPLoadModel mModel;

  public MVPLoadPresenter(MVPLoadContract.IMVPLoadView view) {
    mView = view;
    mModel = new MVPLoadModel();
  }

  @Override
  public void loadData() {
    mView.showLoadingView();
    mModel.requestData(new Runnable() {
      @Override
      public void run() {
        mView.showContentView();
      }
    });
  }
}
