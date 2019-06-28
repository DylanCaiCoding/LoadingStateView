# LoadingHelper

English | [中文](README_ZH_CN.md)

LoadingHelper is a highly expandable Android library for showing loading status view with the low-coupling way, it not only shows different view like **loading, content, error, empty or customized view** when loading network data or database data, but also supports for adding view to header, you can **manager title view** more easier in the activity or fragment. It supports to **change view anytime and expand features of view**, then you can do something after view showed to cope with more demand. **LoadingHelper is more flexible than other similar libraries**.

## Feature

- No need to modify the code of xml file.
- Only one Java file, 360 lines of code without comment statement.
- Support for using in Activity, Fragment, View, ViewPager, RecyclerView.
- Support for managing title view.
- Support for changing views anytime.
- Support for expanding features of view.
- Support for using with most third-party libraries.

## Demo

[Activity(error)](/app/src/main/java/com/caisl/loadinghelper/sample/practise/ActErrorActivity.java)|[Fragment(empty)](app/src/main/java/com/caisl/loadinghelper/sample/practise/FragmentEmptyActivity.java)|[View(placeholder)](app/src/main/java/com/caisl/loadinghelper/sample/practise/ViewPlaceholderActivity.java)
:---:|:---:|:---:
![](gif/activity_error.gif)|![](gif/fragment_empty.gif)|![](gif/view_placeholder.gif)

[ViewPager(timeout)](app/src/main/java/com/caisl/loadinghelper/sample/practise/ViewPagerActivity.java)|[RecyclerView(cool loading)](app/src/main/java/com/caisl/loadinghelper/sample/practise/RecyclerViewActivity.java)|[CustomTitle(search)](app/src/main/java/com/caisl/loadinghelper/sample/practise/SearchTitleActivity.java)
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
  implementation 'com.github.CaiShenglang:LoadingHelper:1.0.0-alpha'
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
loadingHelper.registerAdapter(VIEW_TYPE_LOADING, new LoadingAdapter());

// if you want to register global adapter
LoadingHelper.getDefault().registerAdapter(VIEW_TYPE_LOADING, new LoadingAdapter());
```

#### Step 3. Show view by view type, for example:

```
loadingHelper.showView(viewType);
loadingHelper.showLoadingView(); // view type is VIEW_TYPE_LOADING
loadingHelper.showContentView(); // view type is VIEW_TYPE_CONTENT
loadingHelper.showErrorView(); // view type is VIEW_TYPE_ERROR
loadingHelper.showEmptyView(); // view type is VIEW_TYPE_EMPTY
```

When you need to retry load data.

```
LoadingHelper.setOnRetryListener(new LoadingHelper.OnRetryListener() {
  @Override
  public void onRetry() {
    // request data again
  }
});

holder.onRetryListener.onRetry();
```

## Advanced usage

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
loadingHelper.registerTitleAdapter(new TitleAdapter(config));
loadingHelper.addTitleView();
```

#### Change view after show view

Usage like RecyclerView.Adapter, for example:

```
mErrorAdapter = new ErrorAdapter(mErrorConfig);
loadingHelper.registerAdapter(VIEW_TYPE_ERROR, mErrorAdapter);

mErrorConfig.setErrorText("Service is busy");
// it will execute Adapter#onBindViewHolder(holder)
mErrorAdapter.notifyDataSetChanged();
```

#### Expand Features of view

Create a class implements LoadingHelper.Method<T,VH extends ViewHolder>, for example:

```
// no return value
public class ShowTitleLoadingMethod implements LoadingHelper.Method<Void,TitleAdapter.TitleViewHolder> {
  @Override
  public Void execute(TitleAdapter.TitleViewHolder holder, Object... params) {
    boolean show = (boolean) params[0]; // get param
    // realize feature
    return null;
  }
}

// have a return value
public class IsTitleLoadingMethod implements LoadingHelper.Method<Boolean,TitleAdapter.TitleViewHolder> {
  @Override
  public Boolean execute(TitleAdapter.TitleViewHolder holder, Object... params) {
    // return the desired value
  }
}

LoadingHelper.getDefault()
  .registerMethod(VIEW_TYPE_TITLE,"showTitleLoading",new ShowTitleLoadingMethod());
  .registerMethod(VIEW_TYPE_TITLE,"isTitleLoading",new IsTitleLoadingMethod());
```

Execute a method with or without a return value.

```
// execute method
loadingHelper.executeMethod(VIEW_TYPE_LOADING,"showTitleLoading",true);

// get a return value
boolean titleLoading = holder.getMethodReturnValue("isTitleLoading");
```

### Do something for content view

```
public class SimpleContentAdapter extends LoadingHelper.ContentAdapter<LoadingHelper.ContentViewHolder> {
  @Override
  public LoadingHelper.ContentViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent,
                                                            @NonNull View contentView) {
    return new LoadingHelper.ContentViewHolder(contentView);
  }

  @Override
  public void onBindViewHolder(@NonNull LoadingHelper.ContentViewHolder holder) {
    if (holder.activity != null) {
      // 
    }
  }
}
```

## Thanks

- [luckbilly/Gloading](https://github.com/luckybilly/Gloading) 
- [drakeet/MultiType](https://github.com/drakeet/MultiType) 

## LICENSE

```
Copyright (C) 2019. caisl

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