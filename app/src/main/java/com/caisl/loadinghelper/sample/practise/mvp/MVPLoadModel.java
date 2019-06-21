package com.caisl.loadinghelper.sample.practise.mvp;

import android.os.Handler;

/**
 * @author caisl
 * @since 2019/6/20
 */
public class MVPLoadModel implements MVPLoadContract.IMVPLoadModel {
  @Override
  public void requestData(Runnable runnable) {
    new Handler().postDelayed(runnable,2000);
  }
}
