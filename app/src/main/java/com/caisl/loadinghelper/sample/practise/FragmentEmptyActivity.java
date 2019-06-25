package com.caisl.loadinghelper.sample.practise;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.caisl.loadinghelper.LoadingHelper;
import com.caisl.loadinghelper.sample.R;
import com.caisl.loadinghelper.sample.adapter.TitleAdapter;
import com.caisl.loadinghelper.sample.adapter.TitleConfig;
import com.caisl.loadinghelper.sample.practise.fragment.LoadingFragment;

import static com.caisl.loadinghelper.LoadingHelper.VIEW_TYPE_EMPTY;
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
    loadingHelper.registerAdapter(VIEW_TYPE_TITLE,new TitleAdapter("Fragment(empty)", TitleConfig.Type.BACK));
    loadingHelper.addTitleView();

    final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.add(R.id.container, LoadingFragment.newInstance(VIEW_TYPE_EMPTY));
    transaction.commit();
  }

}
