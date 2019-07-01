# LoadingHelper

English | [中文](README_ZH_CN.md)

LoadingHelper is a highly expandable Android library for showing loading status view with the low-coupling way, it not only shows different view like **loading, content, error, empty or customized view** when loading network data or database data, but also supports for adding view to header, you can **manager title view** more easier in the activity or fragment. It supports to **change view anytime**, then you can do something after view showed to cope with more demand. **LoadingHelper is more flexible than other similar libraries**.

## Feature

- No need to modify the code of xml file.
- Less than 300 lines of code without comment statement.
- Support for using in Activity, Fragment, View, ViewPager, RecyclerView.
- Support for managing title view.
- Support for changing views anytime.
- Support for using with most third-party libraries.
- Support for decoupling the code of Activity's base class.

## Demo

[Activity(error)](/app/src/main/java/com/dylanc/loadinghelper/sample/practise/ActErrorActivity.java)|[Fragment(empty)](app/src/main/java/com/dylanc/loadinghelper/sample/practise/FragmentEmptyActivity.java)|[View(placeholder)](app/src/main/java/com/dylanc/loadinghelper/sample/practise/ViewPlaceholderActivity.java)
:---:|:---:|:---:
![](gif/activity_error.gif)|![](gif/fragment_empty.gif)|![](gif/view_placeholder.gif)

[ViewPager(timeout)](app/src/main/java/com/dylanc/loadinghelper/sample/practise/ViewPagerActivity.java)|[RecyclerView(cool loading)](app/src/main/java/com/dylanc/loadinghelper/sample/practise/RecyclerViewActivity.java)|[CustomTitle(search)](app/src/main/java/com/dylanc/loadinghelper/sample/practise/SearchTitleActivity.java)
:---:|:---:|:---:
![](gif/viewpager_timeout.gif)|![](gif/recyclerview_cool_loading.gif)|![](gif/custom_title_search.gif)

[Click here](https://madeqr.com/loadinghelper) or scan QR code to download

![QR code](img/app_download_qr_code.png)

## Getting started

In your project's build.gradle:

```
allprojects {
  repositories {
    ...
    maven { url 'https://www.jitpack.io' }
  }
}
```

In your module's build.gradle:

```
dependencies {
  implementation 'com.github.DylanCaiCoding:LoadingHelper:1.0.0-alpha'
}
```

### Usage

#### Step 1. Create a class extends LoadingHelper.Adapter&lt;VH extends ViewHolder&gt;, usage is similar to RecyclerView.Adapter, for example:

```
public class LoadingAdapter extends LoadingHelper.Adapter<LoadingHelper.ViewHolder> {
  
  @NonNull
  @Override
  public LoadingHelper.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    return new LoadingHelper.ViewHolder(inflater.inflate(R.layout.lce_layout_loading_view, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull LoadingHelper.ViewHolder holder) {

  }
}
```

#### Step 2. Register your adapter with view type, for example:

```
LoadingHelper loadingHelper = new LoadingHelper(this);
loadingHelper.register(ViewType.LOADING, new LoadingAdapter());

// if you want to register global adapter
LoadingHelper.getDefault().register(ViewType.LOADING, new LoadingAdapter());
```

#### Step 3. Show view by view type, for example:

```
loadingHelper.showView(viewType);
loadingHelper.showLoadingView(); // view type is ViewType.LOADING
loadingHelper.showContentView(); // view type is ViewType.CONTENT
loadingHelper.showErrorView(); // view type is ViewType.ERROR
loadingHelper.showEmptyView(); // view type is ViewType.EMPTY
```

When you need to reload data.

```
LoadingHelper.setOnReloadListener(new LoadingHelper.OnReloadListener() {
  @Override
  public void onReload() {
    // request data again
  }
});

holder.getOnReloadListener.onReload();
```

### Advanced usage

#### Add title view

Create the adapter of title view，suggest incoming data for configuration.

```
public class TitleConfig {
  private String mTitleText;
  private Type mType;
  // omit get set method
  public enum Type {
    BACK, NO_BACK
  }
}

public class TitleAdapter extends LoadingHelper.Adapter<TitleViewHolder> {
  private TitleConfig mConfig;

  public TitleAdapter(TitleConfig config) {
    mConfig = config;
  }

  @Override
  public TitleViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    return new TitleViewHolder(new Toolbar(parent.getContext()));
  }

  @Override
  public void onBindViewHolder(@NonNull final TitleViewHolder holder) {
    if (mConfig != null) {
      // change view according to configuration
    }
  }
}
```

Register title adapter and add title view.

```
final TitleConfig config = new TitleConfig();
config.setTitleText("title");
config.setType(TitleConfig.Type.BACK);
loadingHelper.register(ViewType.TITLE, new TitleAdapter(config));
loadingHelper.addTitleView();
```

#### Change view after view showed

Usage is similar to the RecyclerView.Adapter's, for example:

```
mTitleAdapter = new TitleAdapter(mTitleConfig);
loadingHelper.register(ViewType.TITLE, mTitleAdapter);

mTitleConfig.setTitleText("other title");
mTitleAdapter.notifyDataSetChanged();
```

#### Decoupling the code of Activity's base class


```
public class SimpleContentAdapter extends LoadingHelper.ContentAdapter<SimpleContentAdapter.ViewHolder> {
  @Override
  public ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent,
                                       @NonNull View contentView) {
    return new ViewHolder(contentView);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder) {

  }

  class ViewHolder extends LoadingHelper.ContentViewHolder {

    ViewHolder(@NonNull View rootView) {
      super(rootView);
    }

    @Override
    public void onCreate(@Nullable Activity activity) {
      super.onCreate(activity);
      if (activity != null) {
        // do what you want
      }
    }
  }
}
```

Creates a `LoadingHelper` with the Activity and the `ContentAdapter`.

```
LoadingHelper loadingHelper = new LoadingHelper(this, new SimpleContentAdapter());
```

## Thanks

- [luckbilly/Gloading](https://github.com/luckybilly/Gloading) Standing on the shoulders of giants to realize my idea.
- [dinuscxj/LoadingDrawable](https://github.com/dinuscxj/LoadingDrawable) The cool loading effect in the demo.
- [wuhenzhizao/android-titlebar](https://github.com/wuhenzhizao/android-titlebar) All title bars in the demo.

## License

```
Copyright (C) 2019. Dylan Cai

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```