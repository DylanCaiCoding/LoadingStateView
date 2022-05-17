# Kotlin 委托用法

## 准备工作

需要修改基类，只需简单的几步就可以把本库的功能集成到基类，并且不会影响到已有的代码，只是给基类增加了新的方法。

修改步骤如下：

1. 增加 `LoadingState by LoadingStateImpl(), OnReloadListener` 代码，这是给基类实现 `LoadingState` 和 `OnReloadListener` 接口，并且把 `LoadingState` 接口委托给 `LoadingStateImpl`。
2. 在基类中添加 `open val isDecorated = true` 属性，提供一个可撤销的配置变量。
3. 在 Activity 的 `setContentView()` 方法后执行 `decorateContentView(this, isDecorated)`。在 Fragment 的 `onCreateView()` 返回值执行 `view.decorate(this, isDecorated)`。

<!-- tabs:start -->

#### **Activity**

![img.png](../img/base_activity_code.png)

#### **Fragment**

![img.png](../img/base_fragment_code.png)

<!-- tabs:end -->

这样改造基类后会得到以下的增强：

- 在不影响已有代码的情况下，增加了 [LoadingState](https://github.com/DylanCaiCoding/LoadingStateView/blob/master/loadingstateview-ktx/src/main/java/com/dylanc/loadingstateview/LoadingState.kt) 和 `OnReloadListener` 提供的常用方法。
- 如果担心万一会遇到什么兼容问题，那么在页面重写 `override val isDecorated = false` 属性就会把新增的功能禁用，即使调用了接口方法也不会生效。

### 注册默认的标题栏和缺省页样式

缺省页需要创建一个类继承 `LoadingStateView.ViewDelegate`，构造函数需要传个视图类型参数，默认提供了 `ViewType.LOADING`、`ViewType.ERROR`、`ViewType.EMPTY`。

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

标题栏的稍微有点不同，需要创建一个类继承 `BaseToolbarViewDelegate`，比 `LoadingStateView.ViewDelegate` 多重写个 `bind(config)` 方法，方便之后的更新操作。

下面用个简单标题栏为例子，只有一个返回键和右侧按钮。

```kotlin
class DefaultToolbarViewDelegate : ToolbarViewDelegate() {
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

  override fun bind(config: ToolbarConfig) {
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

通常项目都有各自的标题栏封装，比如 `<include/>` 的标题栏布局或者自定义的标题栏控件，我们能基于此实现。

在 Application 注册全局的 `ViewDelegate`。

```kotlin
LoadingStateView.setViewDelegatePool {
  register(DefaultToolbarViewDelegate(), LoadingViewDelegate(), ErrorViewDelegate(), EmptyViewDelegate())
}
```
