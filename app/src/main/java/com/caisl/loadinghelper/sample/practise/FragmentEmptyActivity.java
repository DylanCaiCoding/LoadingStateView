package com.caisl.loadinghelper.sample.practise;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
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
public class FragmentEmptyActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fragment);
    LoadingHelper loadingHelper = new LoadingHelper(this);
    loadingHelper.registerAdapter(VIEW_TYPE_TITLE,new TitleAdapter(TitleConfig.Type.BACK));
    loadingHelper.addTitleView();

    final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.add(android.R.id.content, new EmptyFragment());
    transaction.commit();
  }

}
