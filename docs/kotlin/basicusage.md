## 基础用法

使用 Kotlin 开发的话推荐用委托的用法，不过建议先把基础的 `LoadingStateView` 用法看了。

### 显示加载中、加载失败等缺省页

第一步，使用 Activity 或 View 创建 `LoadingStateView`，不需要重新加载的话 `OnReloadListener` 可不传。

```kotlin
val loadingStateView = LoadingStateView(this, onReloadListener)
// val loadingStateView = LoadingStateView(view, onReloadListener)
```

第二步，创建一个类继承  `LoadingStateView.ViewDelegate`。

构造函数需要传个参数，决定视图类型，之后显示和更新会用到。默认提供了 `ViewType.LOADING`、`ViewType.ERROR`、`ViewType.EMPTY`、`ViewType.TITLE`，其它的可以用一个 String 作为类型。

```kotlin
class LoadingViewDelegate : LoadingStateView.ViewDelegate(ViewType.LOADING) {
  
  override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View =
    inflater.inflate(R.layout.layout_loading, parent, false)
}
```

如果需要实现点击重新请求数据，可以在点击事件调用 `onReloadListener?.onReload()` 方法。

第三步，注册 `ViewDelegate`。首先在 Application 注册全局的 `ViewDelegate`：

```kotlin
LoadingStateView.setViewDelegatePool {
  register(LoadingViewDelegate(), ErrorViewDelegate(), EmptyViewDelegate())
}
```

如果某个页面需要不同样式，就用该页面的 `LoadingStateView` 注册对应类型的 `ViewDelegate`，就可以把全局的替换了。

```kotlin
loadingStateView.register(CoolLoadingViewDelegate())
```

第四步，显示对应类型的视图。

```kotlin
loadingStateView.showView(viewType)
loadingStateView.showLoadingView() // 显示 ViewType.LOADING 类型的视图
loadingStateView.showContentView() // 显示 ViewType.CONTENT 类型的视图
loadingStateView.showErrorView() // 显示 ViewType.ERROR 类型的视图
loadingStateView.showEmptyView() // 显示 ViewType.EMPTY 类型的视图
```

### 更新视图样式

需要在 `ViewDelegate` 自行实行更新的方法，然后获取到对应的 `ViewDelegate` 进行更新。

比如在 `ErrorViewDelegate` 增加了 `updateMsg(msg)` 方法修改请求失败的文字：

```kotlin
loadingStateView.getViewDelegate<ErrorViewDelegate>(ViewType.ERROR).updateMsg("服务器繁忙，请稍后重试")
```

### 在内容上方添加视图

实现标题栏或搜索栏等 `ViewDelegate`，之后就可以用 `LoadingStateView` 在内容布局的上方添加视图。

```kotlin
loadingStateView.setHeaders(ToolbarViewDelegate("消息"), SearchViewDelegate())
```

### 给内容的外层增加装饰布局

可以给内容布局添加一层装饰，实现更复杂的样式，非简单地在头部增加控件，比如带联动效果的标题栏、DrawerLayout、底部输入框等布局。

我们来实现滑动隐藏标题栏的效果，写一个带联动效果的标题栏布局，其中有个 FragmentLayout 是用于填充内容和切换缺省页。

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.coordinatorlayout.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
  xmlns:app="http://schemas.android.com/apk/res-auto"
  android:layout_width="match_parent"
  android:layout_height="match_parent">

  <com.google.android.material.appbar.AppBarLayout
    android:id="@+id/app_bar"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:elevation="2dp">

    <androidx.appcompat.widget.Toolbar
      android:id="@+id/toolbar"
      android:layout_width="match_parent"
      android:layout_height="?attr/actionBarSize"
      app:layout_collapseMode="pin"
      app:layout_scrollFlags="scroll|enterAlways"
      app:navigationIcon="@drawable/ic_arrow_back_ios"
      android:background="@color/white"
      app:titleTextAppearance="@style/ToolbarTextAppearance" />

  </com.google.android.material.appbar.AppBarLayout>

  <FrameLayout
    android:id="@+id/content_parent"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:layout_behavior="@string/appbar_scrolling_view_behavior"/>

</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

然后写一个类继承 `LoadingStateView.DecorViewDelegate`。

```kotlin
class ScrollingDecorViewDelegate(private val title: String) : LoadingStateView.DecorViewDelegate() {

  override fun onCreateDecorView(context: Context, inflater: LayoutInflater): View {
    val view = inflater.inflate(R.layout.layout_scrolling_toolbar, null)
    val toolbar: Toolbar = view.findViewById(R.id.toolbar)
    toolbar.title = title
    toolbar.setNavigationOnClickListener {
      if (context is Activity) context.finish()
    }
    return view
  }

  override fun getContentParent(decorView: View): ViewGroup {
    return decorView.findViewById(R.id.content_parent)
  }
}
```

与 `ViewDelegate` 不同的是，需要多实现一个 `getContentParent(decorView)` 方法来指定添加内容的容器，这里我们返回前面的 FrameLayout。

之后就可以给内容进行装饰了。

```kotlin
loadingStateView.setDecorView(ScrollingDecorViewDelegate("Test"))
```
