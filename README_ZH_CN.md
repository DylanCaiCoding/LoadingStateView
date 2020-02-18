# LoadingHelper

[English](README.md) | 中文

[![maven](https://api.bintray.com/packages/dylancai/maven/loadinghelper/images/download.svg)](https://bintray.com/dylancai/maven/loadinghelper/_latestVersion) [![License](https://img.shields.io/badge/License-Apache--2.0-blue.svg)](https://github.com/DylanCaiCoding/LoadingHelper/blob/master/LICENSE)

`LoadingHelper` 是一个用于显示加载界面的高拓展性、低耦合的工具，只用了一个 200 行左右的 Kotlin 代码实现（不包含注释）。不仅能在请求网络数据时**显示加载中、加载成功、加载失败、无数据的视图或自定义视图**，还可以**对标题栏进行管理**。

- 无需在布局添加代码
- 可添加自定义视图
- 可用于 Activity、Fragment、列表或指定的 View
- 可管理标题栏和添加多个头部控件
- 可设置重新请求数据的事件
- 可动态更新视图样式
- 可结合绝大部分第三方控件使用
- 可解耦内容视图的初始化

## 示例

[Activity(error)](/app/src/main/java/com/dylanc/loadinghelper/sample/practise/ActErrorActivity.java)|[Fragment(empty)](app/src/main/java/com/dylanc/loadinghelper/sample/practise/FragmentEmptyActivity.java)|[View(placeholder)](app/src/main/java/com/dylanc/loadinghelper/sample/practise/ViewPlaceholderActivity.java)
:---:|:---:|:---:
![](gif/activity_error.gif)|![](gif/fragment_empty.gif)|![](gif/view_placeholder.gif)

[ViewPager(timeout)](app/src/main/java/com/dylanc/loadinghelper/sample/practise/ViewPagerActivity.java)|[RecyclerView(cool loading)](app/src/main/java/com/dylanc/loadinghelper/sample/practise/RecyclerViewActivity.java)|[CustomTitle(search)](app/src/main/java/com/dylanc/loadinghelper/sample/practise/SearchTitleActivity.java)
:---:|:---:|:---:
![](gif/viewpager_timeout.gif)|![](gif/recyclerview_cool_loading.gif)|![](gif/custom_title_search.gif)

点击或者扫描二维码下载

[![QR code](img/app_download_qr_code.png)](https://madeqr.com/loadinghelper)

## 开始使用

在 `build.gradle` 添加依赖：

```
dependencies {
  implementation 'com.dylanc:loadinghelper:1.1.0'
}
```

### 基础用法

第一步，创建一个适配器继承  `LoadingHelper.Adapter<VH extends ViewHolder>`，写法与 `RecyclerView.Adapter` 类似。如果需要实现点击重新请求数据，可以在点击事件调用 holder.getOnReloadListener.onReload() 方法。

```java
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

第二步，注册适配器，关联一个视图类型。有五个默认类型，也可以传任意类型数据进行注册。

```java
LoadingHelper loadingHelper = new LoadingHelper(this);
loadingHelper.register(ViewType.LOADING, new LoadingAdapter());
// 当需要支持点击重新请求数据时
loadingHelper.setOnReloadListener(() -> {})
```

如果想注册成全局的适配器，需要配置默认的适配器池。

```java
LoadingHelper.setDefaultAdapterPool(adapterPool -> {
  adapterPool.register(ViewType.LOADING, new LoadingAdapter());
  return Unit.INSTANCE;
});
```

第三步，显示对应类型的视图。

```java
loadingHelper.showView(viewType);
loadingHelper.showLoadingView(); // 对应视图类型 ViewType.LOADING
loadingHelper.showContentView(); // 对应视图类型 ViewType.CONTENT
loadingHelper.showErrorView(); // 对应视图类型 ViewType.ERROR
loadingHelper.showEmptyView(); // 对应视图类型 ViewType.EMPTY
```

**动态更新已显示视图**

在显示了视图之后，可以对视图进行更改刷新。用法和 `RecyclerView.Adapter` 一样，调用 `notifyDataSetChanged()` 后，会执行适配器的 `onBindViewHolder()` 方法。

```java
ErrorAdapter errorAdapter = loadingHelper.getAdapter(ViewType.ERROR);
errorAdapter.errorText = "服务器繁忙，请稍后重试";
errorAdapter.notifyDataSetChanged();
```

### 高级用法

#### 添加标题栏

先创建好标题栏的适配器，想用哪个标题栏就注册哪个适配器，能添加多个头部，会在所有头部的下方进行 loading。

```java
loadingHelper= new LoadingHelper(this);
loadingHelper.register(ViewType.TITLE, new TitleAdapter("标题名"));
loadingHelper.addTitleView();
// 假设还需要添加一个有搜索功能的头部
loadingHelper.register(VIEW_TYPE_EDIT_HEADER, new SearchHeaderAdapter());
loadingHelper.addHeaderView(VIEW_TYPE_SEARCH, 1);
```

如果想删掉某个已添加的头部。

```java
loadingHelper.removeHeaderView(VIEW_TYPE_SEARCH)
```

#### 初始化内容视图

创建一个适配器继承 `LoadingHelper.ContentAdapter<VH extends ViewHolder>`。如果想要使用 Activity 对象，可以在构造方法传入或者通过 contentView 获得。

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

在创建 `LoadingHelper` 对象时传入 `ContentAdapter` 对象，就会立即对内容视图进行处理。

```java
loadingHelper= new LoadingHelper(this, new CommonContentAdapter());
```


## 感谢

- [luckbilly/Gloading](https://github.com/luckybilly/Gloading) 站在了巨人肩膀上优化了本库，非常感谢！
- [drakeet/MultiType](https://github.com/drakeet/MultiType) 参考了注册配置多适配器的思想和用法
- [dinuscxj/LoadingDrawable](https://github.com/dinuscxj/LoadingDrawable) 示例中的自定义加载动画
- [wuhenzhizao/android-titlebar](https://github.com/wuhenzhizao/android-titlebar) 示例中的标题栏控件

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