# LoadingStateView

English | [中文](README_ZH_CN.md)

[![](https://www.jitpack.io/v/DylanCaiCoding/LoadingStateView.svg)](https://www.jitpack.io/#DylanCaiCoding/LoadingLoadingStateView) [![License](https://img.shields.io/badge/License-Apache--2.0-blue.svg)](https://github.com/DylanCaiCoding/LoadingStateView/blob/master/LICENSE)

`LoadingStateView` is a highly expandable Android library for showing loading status view on the low-coupling way, it is implemented with a Kotlin code of less than 300 lines without comment statement . it not only **shows different view like loading, content, error, empty or customized view** when loading network data, but also **manages title bar.**

## Feature

- No need to add view code to the layout.
- Support for show custom views.
- Support for use for Activity, Fragment, RecyclerView, View.
- Support for managing the title bar and add multiple headers.
- Support for set reload event.
- Support for update views anytime.
- Support for use with most third-party libraries.

## Demo

Click or scan QR code to download

[![QR code](img/app_download_qr_code.png)](https://www.pgyer.com/loadinghelper)

| [Activity(error)](app/src/main/java/com/dylanc/loadingstateview/sample/ui/ActErrorActivity.java) | [View(placeholder)](app/src/main/java/com/dylanc/loadingstateview/sample/ui/ViewPlaceholderActivity.java) | [ViewPager(timeout)](app/src/main/java/com/dylanc/loadingstateview/sample/ui/ViewPagerActivity.java) | [RecyclerView(cool loading)](app/src/main/java/com/dylanc/loadingstateview/sample/ui/RecyclerViewActivity.java) |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
|                 ![](gif/activity_error.gif)                  |                ![](gif/view_placeholder.gif)                 |                ![](gif/viewpager_timeout.gif)                |              ![](gif/recyclerview_loading.gif)               |

| [SpecialHeader(custom)](app/src/main/java/com/dylanc/loadingstateview/sample/ui/CustomHeaderActivity.java) | [MultipleHeader(search)](app/src/main/java/com/dylanc/loadingstateview/sample/ui/MultipleHeaderActivity.java) | [SpecialDecorView(scrolling)](app/src/main/java/com/dylanc/loadingstateview/sample/ui/ScrollingToolbarActivity.java) | [BottomDecorView(editor)](app/src/main/java/com/dylanc/loadingstateview/sample/ui/BottomEditorActivity.java) |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
|              ![](gif/special_header_custom.gif)              |             ![](gif/multiple_header_search.gif)              |             ![](gif/special_decor_scrolling.gif)             |               ![](gif/bottom_decor_editor.gif)               |


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

```groovy
dependencies {
  implementation 'com.github.DylanCaiCoding:LoadingStateView:3.0.0-alpha'
}
```

### Usage

#### Step 1. Create a class extends `LoadingStateView.ViewDelegate<VH extends ViewHolder>`, for example:

```java
public class LoadingViewDelegate extends LoadingStateView.ViewDelegate<LoadingStateView.ViewHolder> {

  @NonNull
  @Override
  public LoadingStateView.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    return new LoadingStateView.ViewHolder(inflater.inflate(R.layout.layout_loading_view, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull LoadingStateView.ViewHolder holder) {
  }
}
```

#### Step 2. Register `ViewDelegate` with a view type, for example:

```java
LoadingStateView loadingStateView = new LoadingStateView(this);
loadingStateView.register(ViewType.LOADING, new LoadingViewDelegate());
```

##### Or if you want to register a global `ViewDelegate`.

```java
loadingStateView.setViewDelegatePool(pool -> {
  pool.register(ViewType.LOADING, new LoadingViewDelegate());
  return null;
});
```

#### Step 3. Show view by view type, for example:

```java
loadingStateView.showView(viewType);
loadingStateView.showLoadingView(); // view type is ViewType.LOADING
loadingStateView.showContentView(); // view type is ViewType.CONTENT
loadingStateView.showErrorView(); // view type is ViewType.ERROR
loadingStateView.showEmptyView(); // view type is ViewType.EMPTY
```

#### When you need to reload data.

```java
loadingStateView.setOnReloadListener(new LoadingStateView.OnReloadListener() {
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
ErrorViewDelegate errorViewDelegate = loadingStateView.getViewDelegate(ViewType.ERROR);
errorViewDelegate.errorText = "Fail to load, please wait";
loadingStateView.notifyDataSetChanged(ViewType.ERROR);
```

### Advanced usage

#### Add title view

If you want to add an ordinary title bar above the content.

Similar to the previous usage, create a class extends `LoadingStateView.ViewDelegate<VH extends ViewHolder>`  and set header.

```java
loadingStateView.setDecorHeader(new TitleViewDelegate("title"), new SearchHeaderViewDelegate());
```

If you want to add an special title bar with linkage effect.

Create a class extends `LoadingStateView.DecorViewDelegate` to create a decorated view and specify a loading container.

```java
public class ScrollingDecorViewDelegate extends LoadingStateView.DecorViewDelegate {  @NotNull  @Override  public View onCreateDecorView(@NotNull LayoutInflater inflater) {    return inflater.inflate(R.layout.layout_scrolling, null);  }  @NotNull  @Override  public ViewGroup getContentParent(@NotNull View decorView) {    return decorView.findViewById(R.id.content_parent);  }}
```

Then set it up.

```java
loadingStateView.setDecorView(new ScrollingDecorViewDelegate());
```

## Author's other libraries

- [ViewBindingKTX](https://github.com/DylanCaiCoding/ViewBindingKTX), the most comprehensive utils of ViewBinding.
- [ActivityResultLauncher](https://github.com/DylanCaiCoding/ActivityResultLauncher), perfect replacement for `startActivityForResult()`

## Thanks

- [luckbilly/Gloading](https://github.com/luckybilly/Gloading) Optimize my library standing on the shoulders of giants.
- [drakeet/MultiType](https://github.com/drakeet/MultiType)  Referenced the usage of ​​multiple adapters.
- [dinuscxj/LoadingDrawable](https://github.com/dinuscxj/LoadingDrawable) The cool loading effect in the demo.

## License

```
Copyright (C) 2019. Dylan CaiLicensed under the Apache License, Version 2.0 (the "License");you may not use this file except in compliance with the License.You may obtain a copy of the License at   http://www.apache.org/licenses/LICENSE-2.0Unless required by applicable law or agreed to in writing, softwaredistributed under the License is distributed on an "AS IS" BASIS,WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.See the License for the specific language governing permissions andlimitations under the License.
```