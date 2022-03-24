### 基础用法

第一步，创建一个类继承  `LoadingStateView.ViewDelegate<VH extends ViewHolder>`，写法与 `RecyclerView.Adapter` 类似。如果需要实现点击重新请求数据，可以在点击事件调用 holder.getOnReloadListener.onReload() 方法。

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

第二步，注册 `ViewDelegate`，关联一个视图类型。有五个默认类型，也可以传任意类型数据进行注册。

```java
LoadingStateView loadingStateView = new LoadingStateView(this); // 可传 Activity 或 View
loadingStateView.register(ViewType.LOADING, new LoadingViewDelegate());
// 当需要支持点击重新请求数据时
loadingStateView.setOnReloadListener(() -> {})
```

如果需要注册成全局的 `ViewDelegate`。

```java
loadingStateView.setViewDelegatePool(pool -> {
  pool.register(ViewType.LOADING, new LoadingViewDelegate());
});
```

第三步，显示对应类型的视图。

```java
loadingStateView.showView(viewType);
loadingStateView.showLoadingView(); // 对应视图类型 ViewType.LOADING
loadingStateView.showContentView(); // 对应视图类型 ViewType.CONTENT
loadingStateView.showErrorView(); // 对应视图类型 ViewType.ERROR
loadingStateView.showEmptyView(); // 对应视图类型 ViewType.EMPTY
```

**动态更新已显示视图**

在显示了视图之后，可以对视图进行更改刷新。和 `RecyclerView.Adapter` 类似，会执行适配器的 `onBindViewHolder()` 方法。

```java
loadingStateView.updateView(ViewType.ERROR, (ErrorViewDelegate delegate) -> {
   delegate.msg = "服务器繁忙，请稍后重试";
});
```