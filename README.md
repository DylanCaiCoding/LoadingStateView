# LoadingHelper

English | [中文](README_ZH_CN.md)

[![](https://www.jitpack.io/v/DylanCaiCoding/LoadingHelper.svg)](https://www.jitpack.io/#DylanCaiCoding/LoadingHelper) [![](https://img.shields.io/badge/License-Apache--2.0-blue.svg)](https://github.com/DylanCaiCoding/LoadingHelper/blob/master/LICENSE)

`LoadingHelper` is a highly expandable Android library for showing loading status view on the low-coupling way, it is implemented with a Kotlin code of less than 300 lines without comment statement . it not only **shows different view like loading, content, error, empty or customized view** when loading network data, but also **manages title bar.**

## Feature

- No need to add view code to the layout.
- Support for show custom views.
- Support for use for Activity, Fragment, RecyclerView, View.
- Support for managing the title bar and add multiple headers.
- Support for set reload event.
- Support for update views anytime.
- Support for use with most third-party libraries.
- Support for preprocessing the content view.

## Demo

Click or scan QR code to download

[![QR code](img/app_download_qr_code.png)](https://www.pgyer.com/loadinghelper)

| [Activity(error)](app/src/main/java/com/dylanc/loadinghelper/sample/ui/ActErrorActivity.java) | [View(placeholder)](app/src/main/java/com/dylanc/loadinghelper/sample/ui/ViewPlaceholderActivity.java) | [ViewPager(timeout)](app/src/main/java/com/dylanc/loadinghelper/sample/ui/ViewPagerActivity.java) | [RecyclerView(cool loading)](app/src/main/java/com/dylanc/loadinghelper/sample/ui/RecyclerViewActivity.java) |
| :---: | :----: | :---: | :---: |
| ![](gif/activity_error.gif) | ![](gif/view_placeholder.gif) | ![](gif/viewpager_timeout.gif) | ![](gif/recyclerview_loading.gif) |

| [SpecialHeader(custom)](app/src/main/java/com/dylanc/loadinghelper/sample/ui/CustomHeaderActivity.java) | [MultipleHeader(search)](app/src/main/java/com/dylanc/loadinghelper/sample/ui/MultipleHeaderActivity.java) | [SpecialDecorView(scrolling)](app/src/main/java/com/dylanc/loadinghelper/sample/ui/ScrollingToolbarActivity.java) | [BottomDecorView(editor)](app/src/main/java/com/dylanc/loadinghelper/sample/ui/BottomEditorActivity.java) |
| :---: | :---: | :---: | :---: |
| ![](gif/special_header_custom.gif) | ![](gif/multiple_header_search.gif) | ![](gif/special_decor_scrolling.gif) | ![](gif/bottom_decor_editor.gif) |


## Getting started

Add it in your root `build.gradle` at the end of repositories:
```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://www.jitpack.io' }
    }
}
```

Add dependencies in your module `build.gradle` :

```
dependencies {
  implementation 'com.github.DylanCaiCoding:LoadingHelper:2.2.0'
}
```

### Usage

#### Step 1. Create a class extends `LoadingHelper.Adapter<VH extends ViewHolder>`, for example:

```java
public class LoadingAdapter extends LoadingHelper.Adapter<LoadingHelper.ViewHolder> {

  @NonNull
  @Override
  public LoadingHelper.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    return new LoadingHelper.ViewHolder(inflater.inflate(R.layout.layout_loading_view, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull LoadingHelper.ViewHolder holder) {

  }
}
```

#### Step 2. Register your adapter with a view type, for example:

```java
LoadingHelper loadingHelper = new LoadingHelper(this);
loadingHelper.register(ViewType.LOADING, new LoadingAdapter());
```

##### Or if you want to register a global adapter.

```java
LoadingHelper.setDefaultAdapterPool(adapterPool -> {
  adapterPool.register(ViewType.LOADING, new LoadingAdapter());
  return Unit.INSTANCE;
});
```

#### Step 3. Show view by view type, for example:

```java
loadingHelper.showView(viewType);
loadingHelper.showLoadingView(); // view type is ViewType.LOADING
loadingHelper.showContentView(); // view type is ViewType.CONTENT
loadingHelper.showErrorView(); // view type is ViewType.ERROR
loadingHelper.showEmptyView(); // view type is ViewType.EMPTY
```

#### When you need to reload data.

```java
loadingHelper.setOnReloadListener(new LoadingHelper.OnReloadListener() {
  @Override
  public void onReload() {
    // request data again
  }
});

//In the adapter
holder.getOnReloadListener.onReload();
```

#### When you need to change view after view showed.

```java
ErrorAdapter adapter = loadingHelper.getAdapter(ViewType.Error);
adapter.errorText = "Fail to load, please wait";
adapter.notifyDataSetChanged();
```

### Advanced usage

#### Add title view

If you want to add an ordinary title bar above the content.

Similar to the previous usage, create a class extends `LoadingHelper.Adapter<VH extends ViewHolder>`  and set header.

```java
loadingHelper.register(ViewType.TITLE, new TitleAdapter("title"));
loadingHelper.register(VIEW_TYPE_SEARCH, new SearchHeaderAdapter(onSearchListener));
loadingHelper.setDecorHeader(ViewType.TITLE, VIEW_TYPE_SEARCH);
```

If you want to add an special title bar with linkage effect.

Create a class extends `LoadingHelper.DecorAdapter` to create a decorated view and specify a loading container.

```java
public class ScrollDecorAdapter extends LoadingHelper.DecorAdapter {
  @NotNull
  @Override
  public View onCreateDecorView(@NotNull LayoutInflater inflater) {
    return inflater.inflate(R.layout.layout_scrolling, null);
  }

  @NotNull
  @Override
  public ViewGroup getContentParent(@NotNull View decorView) {
    return decorView.findViewById(R.id.content_parent);
  }
}
```

Then set it up.

```java
loadingHelper.setDecorAdapter(new ScrollDecorAdapter());
```

#### Preprocessing the content view

Create a adapter extends `LoadingHelper.ContentAdapter<VH extends ViewHolder>`.

```java
public class CommonContentAdapter extends LoadingHelper.ContentAdapter<LoadingHelper.ViewHolder> {
  @Override
  public LoadingHelper.ViewHolder onCreateViewHolder(@NonNull View contentView) {
    return new LoadingHelper.ViewHolder(contentView);
  }

  @Override
  public void onBindViewHolder(@NonNull LoadingHelper.ViewHolder holder) {
	View contentView = holder.getRootView();
  }
}
```

Create a `LoadingHelper` with  the `ContentAdapter`.

```java
LoadingHelper loadingHelper = new LoadingHelper(this, new CommonContentAdapter());
```

## Thanks

- [luckbilly/Gloading](https://github.com/luckybilly/Gloading) Optimize my library standing on the shoulders of giants.
- [drakeet/MultiType](https://github.com/drakeet/MultiType)  Referenced the usage of ​​multiple adapters.
- [dinuscxj/LoadingDrawable](https://github.com/dinuscxj/LoadingDrawable) The cool loading effect in the demo.

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
