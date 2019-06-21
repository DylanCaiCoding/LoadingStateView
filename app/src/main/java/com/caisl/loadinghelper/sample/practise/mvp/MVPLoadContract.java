package com.caisl.loadinghelper.sample.practise.mvp;

import com.caisl.loadinghelper.sample.widget.ILCEView;

/**
 * @author caisl
 * @since 2019/6/20
 */
public class MVPLoadContract {
  public interface IMVPLoadView extends ILCEView {

  }

  public interface IMVPLoadPresenter{
    void loadData();
  }

  public interface IMVPLoadModel{
    void requestData(Runnable runnable);
  }
}
