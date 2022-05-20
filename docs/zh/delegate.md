# Kotlin 委托用法

## 准备工作

需要修改基类，只需简单的两步就可以把本库的功能集成到基类，并且不会影响到已有的代码，只是给基类扩展了新的方法。

注意添加的依赖需要是 `loadingstateview-ktx`：

```groovy
dependencies {
  implementation 'com.github.DylanCaiCoding.LoadingStateView:loadingstateview-ktx:4.0.0'
}
```

修改步骤如下：

1. 实现 `LoadingState by LoadingStateDelegate(), OnReloadListener, Decorative` 接口，其中 `LoadingState` 接口委托给了 `LoadingStateDelegate` 代理类。
2. 在 Activity 的 `setContentView()` 方法后执行 `decorateContentView(this, this)`。在 Fragment 的 `onCreateView()` 返回 `view.decorate(this, this)`。

<!-- tabs:start -->

#### **Activity**

![img.png](../img/base_activity_code.png)

#### **Fragment**

![img.png](../img/base_fragment_code.png)

<!-- tabs:end -->

这样改造基类后会得到以下的增强：

- 在不影响已有代码的情况下，增加了 [LoadingState](https://github.com/DylanCaiCoding/LoadingStateView/blob/master/loadingstateview-ktx/src/main/java/com/dylanc/loadingstateview/LoadingState.kt) 接口提供的常用方法，该接口包含了 `LoadingStateView` 所有功能。
- 如果担心对基类有什么影响，在页面重写 `override val isDecorated = false` 会把一切还原，即使调用了新增的接口方法也不会生效，请放心使用。

## 显示缺省页

先注册各类型缺省页的样式，之后才能调用对应的 `showView()` 方法。

创建类继承 `LoadingStateView.ViewDelegate`，构造函数传个视图类型参数，默认提供了 `ViewType.LOADING`、`ViewType.ERROR`、`ViewType.EMPTY`。

<!-- tabs:start -->

#### **ViewType.LOADING**

```kotlin
class LoadingViewDelegate : LoadingStateView.ViewDelegate(ViewType.LOADING) {

  override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View =
    inflater.inflate(R.layout.layout_loading, parent, false)
}
```

#### **ViewType.ERROR**

```kotlin
class ErrorViewDelegate : LoadingStateView.ViewDelegate(ViewType.ERROR) {

  override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View =
    inflater.inflate(R.layout.layout_error, parent, false).apply {
      findViewById<View>(R.id.btn_reload).setOnClickListener {
        onReloadListener?.onReload()
      }
    }
}
```

#### **ViewType.EMPTY**

```kotlin
class EmptyViewDelegate : LoadingStateView.ViewDelegate(ViewType.EMPTY) {

  override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup): View =
    inflater.inflate(R.layout.layout_empty, parent, false)
}
```

<!-- tabs:end -->

在 Application 注册全局的 `ViewDelegate`。

```kotlin
LoadingStateView.setViewDelegatePool {
  register(LoadingViewDelegate(), ErrorViewDelegate(), EmptyViewDelegate())
}
```

在实现了基类的 `Activity` 或 `Fragment` 可以调用对应的 `showView()` 方法。

```kotlin
showLoadingView()  // 显示 ViewType.LOADING 类型的视图
showErrorView()    // 显示 ViewType.ERROR 类型的视图
showEmptyView()    // 显示 ViewType.EMPTY 类型的视图
showContentView()  // 显示原来的内容视图
showView(viewType) // 显示自定义类型的视图
```

如果需要实现点击重新加载，就在基类重写 `onReload()` 方法。

如果某个页面需要显示不同的缺省页，可以在显示前调用一下 `registerView(viewDelegate)` 方法覆盖默认的样式。比如：

```kotlin
registerView(CoolLoadingViewDelegate())
showLoadingView()
```

如果需要动态更新某个样式，在 `ViewDelegate` 自行增加更新的方法，比如在 `ErrorViewDelegate` 增加了 `updateMsg(msg)` 方法修改请求失败的文字，然后就能更新了。

```kotlin
updateView<ErrorViewDelegate>(ViewType.ERROR) {
  updateMsg("服务器繁忙，请稍后重试")
}
```

## 设置标题栏

先注册标题栏样式，之后才能调用 `setToolbar()` 方法。

创建一个类继承 `BaseToolbarViewDelegate`，通常项目都有各自的标题栏封装，我们能基于已有的标题栏布局或者自定义的标题栏控件实现 `ToolbarViewDelegate`。比如：

```kotlin
class ToolbarViewDelegate : BaseToolbarViewDelegate() {
  private lateinit var tvTitle: TextView
  private lateinit var ivLeft: ImageView
  private lateinit var ivRight: ImageView

  override fun onCreateToolbar(inflater: LayoutInflater, parent: ViewGroup): View {
    val view = inflater.inflate(R.layout.layout_toolbar, parent, false)
    tvTitle = view.findViewById(R.id.tv_title)
    ivLeft = view.findViewById(R.id.iv_left)
    ivRight = view.findViewById(R.id.iv_right)
    return view
  }

  override fun onBindToolbar(config: ToolbarConfig) {
    tvTitle.text = config.title

    if (config.navBtnType == NavBtnType.NONE) {
      ivLeft.visibility = View.GONE
    } else {
      ivLeft.setOnClickListener(config.onNavClickListener)
      ivLeft.visibility = View.VISIBLE
    }

    if (config.rightIcon != null) {
      ivRight.setImageResource(config.rightIcon!!)
      ivRight.setOnClickListener(config.onRightClickListener)
      ivRight.visibility = View.VISIBLE
    }
  }
}
```

`ToolbarConfig` 提供了几个常用的属性。可以根据需要选择处理，比如上述例子只实现了有无返回键和右侧按钮的逻辑，项目中有功能相对完整的[示例代码](https://github.com/DylanCaiCoding/LoadingStateView/blob/master/sample-kotlin/src/main/java/com/dylanc/loadingstateview/sample/kotlin/delegate/ToolbarViewDelegate.kt)。

| 属性                 | 含义                  |
| -------------------- | -------------------- |
| title                | 标题                  |
| navBtnType           | 导航 (左侧) 按钮类型    |
| navIcon              | 导航 (左侧) 图标       |
| navText              | 导航 (左侧) 文字       |
| onNavClickListener   | 导航 (左侧) 按钮点击事件 |
| rightIcon            | 右侧图标               |
| rightText            | 右侧文字               |
| onRightClickListener | 右侧按钮点击事件        |

`onNavClickListener` 默认执行 `finish()` 操作。`navBtnType` 默认类型是 `NavBtnType.ICON`，还有 `NavBtnType.NONE`、`NavBtnType.TEXT`、`NavBtnType.ICON_TEXT`类型。其它的属性默认为空，为空的时候不用处理使用默认样式即可。

当然这点属性肯定不能满足所有的需求，所以本库支持给 `ToolbarConfig` 增加扩展属性。比如需要动态修改右侧文字颜色：

```kotlin
var ToolbarConfig.rightTextColor: Int? by toolbarExtras() // 增加 rightTextColor 扩展属性

class ToolbarViewDelegate : BaseToolbarViewDelegate() {
   
   // ...
   
   override fun onBindToolbar(config: ToolbarConfig) {
     // ... 
     config.rightTextColor?.let { tvRight.setTextColor(it) } // 处理扩展属性
   }
}
```

在 Application 注册全局的标题栏 `ViewDelegate`。

```kotlin
LoadingStateView.setViewDelegatePool {
  register(ToolbarViewDelegate(), // ... )
}
```

之后就能在实现了基类的 `Activity` 或 `Fragment` 设置标题栏了。

```kotlin
setToolbar() // 默认有返回键

setToolbar("title") // 有标题和返回键

setToolbar("title", NavBtnType.NONE) // 只有标题，无返回键

setToolbar("title") {                 // 以下可选
  navIcon = R.drawable.account        // 只修改返回键图标
  navIcon { ... }                     // 只修改返回键的点击事件
  navIcon(R.drawable.message) { ... } // 修改返回键的图标和点击事件
  rightIcon(R.drawable.add) { ... }   // 添加右侧图标
  rightText("Delete") { ... }         // 添加右侧文字
  rightTextColor = Color.RED          // 新增的扩展属性，修改右侧文字颜色
}
```

这样就多了一种添加标题栏的方式，新写的代码可以用上述的方式添加标题栏，老的代码保留已有的 `<include/>` 布局或者自定义标题栏控件的用法。样式都是一样的，因为是基于已有标题栏实现的。

如果某个页面的标题栏样式变动很大，不建议写太多扩展属性来配置，这样代码阅读性也差。推荐用新布局再写一个 `BaseToolbarViewDelegate` 的实现类，在设置标题栏之前注册，这会覆盖掉默认的样式。

```kotlin
registerView(SpecialToolbarViewDelegate())
setToolbar("title")
```

如果需要动态更新标题栏样式：

```kotlin
updateToolbar { title = "Loading..." }
```

## 在顶部添加控件

可添加多个控件，比如添加标题栏和搜索栏，搜索栏需要另写一个类继承 `LoadingStateView.ViewDelegate`。

```kotlin
setHeaders(
  ToolbarViewDelegate("Search") {
    rightIcon(R.drawable.more) { ... }
  },
  SearchViewDelegate(onSearchListener)
)
```

## 给内容增加装饰

可以给内容布局再套上一层装饰，实现更复杂的样式，非简单地在顶部增加控件，比如带联动效果的标题栏、DrawerLayout、底部输入框等布局。

接下来以滑动隐藏标题栏的效果为例，先写一个带联动效果的标题栏布局，其中有个 FragmentLayout 是用于填充内容和显示缺省页。

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
class ScrollingDecorViewDelegate(
  private val activity: Activity, 
  private val title: String
) : LoadingStateView.DecorViewDelegate() {

  override fun onCreateDecorView(context: Context, inflater: LayoutInflater): View {
    val view = inflater.inflate(R.layout.layout_scrolling_toolbar, null)
    val toolbar: Toolbar = view.findViewById(R.id.toolbar)
    toolbar.title = title
    toolbar.setNavigationOnClickListener { activity.finish() }
    return view
  }

  override fun getContentParent(decorView: View): ViewGroup {
    return decorView.findViewById(R.id.content_parent)
  }
}
```

与 `LoadingStateView.ViewDelegate` 不同的是，需要多实现一个 `getContentParent(decorView)` 方法来指定添加内容的容器，这里我们返回前面的 FrameLayout。

之后就可以给内容进行装饰了。

```kotlin
setDecorView(ScrollingDecorViewDelegate(this, "title"))
```