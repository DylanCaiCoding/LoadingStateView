package com.caisl.loadinghelper.sample.practise;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.caisl.loadinghelper.LoadingHelper;
import com.caisl.loadinghelper.sample.R;
import com.caisl.loadinghelper.sample.lce.title.ShowTitleLoadingMethod;
import com.caisl.loadinghelper.sample.lce.title.TitleAdapter;
import com.caisl.loadinghelper.sample.lce.title.TitleConfig;
import com.caisl.loadinghelper.sample.widget.LCEActivity;

import static com.caisl.loadinghelper.LoadingHelper.VIEW_TYPE_TITLE;

/**
 * @author caisl
 * @since 2019/6/20
 */
public class RecyclerViewActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recycler_view);
    LoadingHelper loadingHelper = new LoadingHelper(this);
    loadingHelper.registerAdapter(VIEW_TYPE_TITLE, new TitleAdapter(TitleConfig.Type.BACK));
    loadingHelper.registerMethod(VIEW_TYPE_TITLE, "showTitleLoading", new ShowTitleLoadingMethod());
    loadingHelper.addTitleView();

    RecyclerView recyclerView = findViewById(R.id.recycler_view);
    recyclerView.setAdapter(new ImageAdapter());
    recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
  }


  public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    String[] mImages = {
        "https://ww1.sinaimg.cn/large/0065oQSqly1g2pquqlp0nj30n00yiq8u.jpg",
        "https://ww1.sinaimg.cn/large/0065oQSqly1g2hekfwnd7j30sg0x4djy.jpg",
        "https://ws1.sinaimg.cn/large/0065oQSqly1g0ajj4h6ndj30sg11xdmj.jpg",
        "https://ws1.sinaimg.cn/large/0065oQSqly1fytdr77urlj30sg10najf.jpg",
        "https://ws1.sinaimg.cn/large/0065oQSqly1fymj13tnjmj30r60zf79k.jpg",
        "https://ws1.sinaimg.cn/large/0065oQSqgy1fy58bi1wlgj30sg10hguu.jpg",
        "https://ws1.sinaimg.cn/large/0065oQSqgy1fxno2dvxusj30sf10nqcm.jpg",
        "https://ws1.sinaimg.cn/large/0065oQSqgy1fxd7vcz86nj30qo0ybqc1.jpg",
        "https://ws1.sinaimg.cn/large/0065oQSqgy1fwyf0wr8hhj30ie0nhq6p.jpg",
        "https://ws1.sinaimg.cn/large/0065oQSqgy1fwgzx8n1syj30sg15h7ew.jpg"
    };

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
      final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_image, parent,false);
      ImageView imageView = new ImageView(parent.getContext());
      imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 750));
      imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
      LoadingHelper loadingHelper = new LoadingHelper(imageView);
      return new ImageViewHolder(loadingHelper,imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int position) {
      holder.showImage(mImages[position]);
    }

    @Override
    public int getItemCount() {
      return mImages.length;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {

      private final LoadingHelper mLoadingHelper;
      ImageView mIvLady;

      ImageViewHolder(@NonNull LoadingHelper loadingHelper, ImageView imageView) {
        super(loadingHelper.getParent());
        mLoadingHelper = loadingHelper;
        mIvLady = imageView;
//        mIvLady = itemView.findViewById(R.id.iv_lady);
      }

      void showImage(String url){
        mLoadingHelper.showLoadingView();
        Glide.with(itemView.getContext())
            .load(url)
            .listener(new RequestListener<Drawable>() {
              @Override
              public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                mLoadingHelper.showErrorView();
                return false;
              }

              @Override
              public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                mLoadingHelper.showContentView();
                return false;
              }
            })
            .into(mIvLady);
      }
    }
  }
}
