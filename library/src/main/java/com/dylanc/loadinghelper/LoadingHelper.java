/*
 * Copyright (c) 2019. Dylan Cai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dylanc.loadinghelper;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.HashMap;

import static java.util.Objects.requireNonNull;

/**
 * @author Dylan Cai
 */
@SuppressWarnings("WeakerAccess")
public final class LoadingHelper {

  private static volatile Default sDefault;
  private LinearLayout mDecorView;
  private View mContentView;
  private ViewHolder mCurrentViewHolder;
  private Activity mActivity;
  private OnReloadListener mOnReloadListener;
  private HashMap<Object, ViewHolder> mViewHolders;
  @NonNull
  private Adapters mAdapters;

  public static Default getDefault() {
    if (sDefault == null) {
      synchronized (Default.class) {
        sDefault = new Default();
      }
    }
    return sDefault;
  }

  /**
   * Constructs a LoadingHelper with a activity.
   *
   * @param activity Activity
   */
  public LoadingHelper(@NonNull Activity activity) {
    this(activity, null);
  }

  /**
   * Constructs a LoadingHelper with a activity and a content adapter, you can get activity's instance from
   * content view holder what content adapter created.
   *
   * @param activity       the activity
   * @param contentAdapter the adapter of creating content view
   */
  public LoadingHelper(@NonNull Activity activity, ContentAdapter contentAdapter) {
    this(activity, ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0), contentAdapter);
  }

  /**
   * Constructs a LoadingHelper with a content view.
   *
   * @param contentView the content view
   */
  public LoadingHelper(@NonNull View contentView) {
    this(contentView, null);
  }

  /**
   * Constructs a LoadingHelper with a content view and a content adapter.
   *
   * @param contentView    the content view
   * @param contentAdapter the adapter of creating content view
   */
  public LoadingHelper(@NonNull View contentView, ContentAdapter contentAdapter) {
    this(null, contentView, contentAdapter);
  }

  private LoadingHelper(Activity activity, @NonNull View contentView, ContentAdapter contentAdapter) {
    mActivity = activity;
    mContentView = requireNonNull(contentView);
    mViewHolders = new HashMap<>();
    mAdapters = getDefault().mAdapters.clone();
    if (contentAdapter != null) {
      register(ViewType.CONTENT, contentAdapter);
    }

    mDecorView = new LinearLayout(contentView.getContext());
    mDecorView.setOrientation(LinearLayout.VERTICAL);
    mDecorView.setLayoutParams(contentView.getLayoutParams());
    ViewGroup parent = (ViewGroup) contentView.getParent();
    if (parent != null) {
      int index = parent.indexOfChild(contentView);
      parent.removeView(contentView);
      parent.addView(mDecorView, index);
    }
    showView(ViewType.CONTENT);
  }

  /**
   * Registers the adapter of creating view before showing view.
   *
   * @param viewType the view type of adapter
   * @param adapter  the adapter of creating view
   */
  public void register(Object viewType, @NonNull Adapter<?> adapter) {
    mAdapters.register(viewType, adapter);
  }

  /**
   * Adds title view to the header of decorated view
   */
  public void addTitleView() {
    addHeaderView(ViewType.TITLE);
  }

  /**
   * Adds view to the header of decorated view
   *
   * @param viewType the view type of adapter
   */
  public void addHeaderView(Object viewType) {
    mDecorView.addView(getViewHolder(viewType).rootView, 0);
  }

  /**
   * Shows the loading view
   */
  public void showLoadingView() {
    showView(ViewType.LOADING);
  }

  /**
   * Shows the content view
   */
  public void showContentView() {
    showView(ViewType.CONTENT);
  }

  /**
   * Shows the error view
   */
  public void showErrorView() {
    showView(ViewType.ERROR);
  }

  /**
   * Shows the empty view
   */
  public void showEmptyView() {
    showView(ViewType.EMPTY);
  }

  /**
   * Shows the view by view type
   *
   * @param viewType the view type of adapter
   */
  public void showView(Object viewType) {
    if (mCurrentViewHolder == null) {
      addView(viewType);
    } else {
      if (viewType != mCurrentViewHolder.viewType) {
        mDecorView.removeView(mCurrentViewHolder.rootView);
        addView(viewType);
      }
    }
  }

  private void addView(Object viewType) {
    ViewHolder viewHolder = getViewHolder(viewType);
    mDecorView.addView(viewHolder.rootView);
    mCurrentViewHolder = viewHolder;
  }

  /**
   * Called if you need to handle reload event, you can get the listener of reloading data from view holder.
   *
   * @param onReloadListener the listener of reloading data
   */
  public void setOnReloadListener(OnReloadListener onReloadListener) {
    mOnReloadListener = onReloadListener;
  }

  /**
   * Called when
   *
   * @return the decorated view of root view
   */
  public LinearLayout getDecorView() {
    return mDecorView;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  private <T extends ViewHolder> T getViewHolder(Object viewType) {
    if (mViewHolders.get(viewType) == null) {
      addViewHolder(viewType);
    }
    return (T) requireNonNull(mViewHolders.get(viewType));
  }

  @SuppressWarnings("unchecked")
  private void addViewHolder(Object viewType) {
    final Adapter adapter = mAdapters.getAdapter(viewType);
    ViewHolder viewHolder;
    if (adapter instanceof ContentAdapter) {
      final ContentAdapter contentAdapter = (ContentAdapter) adapter;
      contentAdapter.onCreate(mContentView);
    }
    viewHolder = adapter.onCreateViewHolder(LayoutInflater.from(mDecorView.getContext()), mDecorView);
    viewHolder.viewType = viewType;
    viewHolder.mOnReloadListener = mOnReloadListener;
    if (viewHolder instanceof ContentViewHolder) {
      ((ContentViewHolder) viewHolder).onCreate(mActivity);
    }
    mViewHolders.put(viewType, viewHolder);
    adapter.viewHolder = viewHolder;
    adapter.onBindViewHolder(viewHolder);
  }

  public static class Default {
    Adapters mAdapters = new Adapters();

    /**
     * Registers the global adapter of creating view
     *
     * @param viewType the view type of adapter
     * @param adapter  the adapter of creating view
     * @return
     */
    public Default register(Object viewType, @NonNull Adapter<?> adapter) {
      mAdapters.register(viewType, adapter);
      return this;
    }
  }

  /**
   * @param <VH> the holder of view
   */
  public static abstract class Adapter<VH extends LoadingHelper.ViewHolder> {
    VH viewHolder;

    @NonNull
    public abstract VH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent);

    public abstract void onBindViewHolder(@NonNull VH holder);

    public void notifyDataSetChanged() {
      onBindViewHolder(requireNonNull(viewHolder));
    }
  }

  /**
   * @param <VH> the holder of content view
   */
  public static abstract class ContentAdapter<VH extends LoadingHelper.ContentViewHolder> extends Adapter<VH> {
    View mContentView;

    void onCreate(View contentView) {
      mContentView = contentView;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
      return onCreateViewHolder(requireNonNull(inflater), requireNonNull(parent), requireNonNull(mContentView));
    }

    public abstract VH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent,
                                          @NonNull View contentView);
  }

  /**
   *
   */
  public static class ViewHolder {
    @NonNull
    public final View rootView;
    Object viewType;
    OnReloadListener mOnReloadListener;

    public ViewHolder(@NonNull View rootView) {
      this.rootView = requireNonNull(rootView);
    }

    /**
     * Gets the listener of reloading data from view holder.
     */
    public OnReloadListener getOnReloadListener() {
      return mOnReloadListener;
    }
  }

  /**
   *
   */
  public static class ContentViewHolder extends ViewHolder {
    public ContentViewHolder(@NonNull View rootView) {
      super(rootView);
    }

    /**
     * @param activity the activity
     */
    public void onCreate(@Nullable Activity activity) {
    }
  }

  public interface OnReloadListener {
    void onReload();
  }

  static class Adapters implements Cloneable {
    private HashMap<Object, Adapter> mAdapters;

    public Adapters() {
      mAdapters = new HashMap<>();
    }

    public void register(Object viewType, @NonNull Adapter<?> adapter) {
      mAdapters.put(viewType, requireNonNull(adapter));
    }

    @NonNull
    public Adapter<?> getAdapter(Object viewType) {
      Adapter adapter = mAdapters.get(viewType);
      if (adapter == null && viewType == ViewType.CONTENT) {
        adapter = new LoadingHelper.ContentAdapter() {
          @Override
          public ContentViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent,
                                                      @NonNull View contentView) {
            return new ContentViewHolder(contentView);
          }

          @Override
          public void onBindViewHolder(@NonNull LoadingHelper.ViewHolder holder) {
          }
        };
        register(ViewType.CONTENT, adapter);
      }
      return requireNonNull(adapter, "Adapter is unregistered");
    }

    @SuppressWarnings("unchecked")
    public Adapters clone() {
      Adapters clone = null;
      try {
        clone = (Adapters) super.clone();
        clone.mAdapters = (HashMap<Object, Adapter>) this.mAdapters.clone();
      } catch (CloneNotSupportedException e) {
        e.printStackTrace();
      }
      return clone;
    }
  }
}