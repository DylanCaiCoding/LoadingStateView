package com.dylanc.loadinghelper.sample.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.dylanc.loadinghelper.LoadingHelper;
import com.dylanc.loadinghelper.ViewType;
import com.dylanc.loadinghelper.sample.R;
import com.dylanc.loadinghelper.sample.adapter.TitleAdapter;
import com.dylanc.loadinghelper.sample.base.TitleConfig;
import com.dylanc.loadinghelper.sample.ui.fragment.LoadingFragment;

import static com.dylanc.loadinghelper.sample.ui.fragment.LoadingFragment.VIEW_TYPE_EMPTY;

/**
 * @author Dylan Cai
 * @since 2019/6/20
 */
public class FragmentEmptyActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fragment);
    LoadingHelper loadingHelper = new LoadingHelper(this);
    loadingHelper.register(ViewType.TITLE, new TitleAdapter("Fragment(empty)", TitleConfig.Type.BACK));
    loadingHelper.setDecorHeader(ViewType.TITLE);

    final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.add(R.id.content_view, LoadingFragment.newInstance(VIEW_TYPE_EMPTY));
    transaction.commit();
  }

}
