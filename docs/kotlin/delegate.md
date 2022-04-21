## Kotlin 委托用法

先康康最终效果，

### 准备工作

#### 修改基类

只需简单的几步就可以把本库的功能集成到基类，并且不会影响到已有的代码，只是给基类增加了新的方法。

修改步骤如下：

1. 增加 `LoadingState by LoadingStateImpl(), OnReloadListener` 代码，这是给基类实现 `LoadingState` 和 `OnReloadListener` 接口，并且把 `LoadingState` 接口委托给 `LoadingStateImpl`。
2. 在基类中添加 `open val isDecorated = true` 属性，提供一个可重写的配置变量。
3. 在 Activity 的 `setContentView()` 方法后执行 `decorateContentView(this, isDecorated)`。在 Fragment 的 `onCreateView()` 返回值执行 `view.decorate(this, isDecorated)`。

Activity 基类的修改示例：

```kotlin
abstract class BaseActivity(private val layoutRes: Int) : AppCompatActivity(),
  LoadingState by LoadingStateImpl(), OnReloadListener {

  open val isDecorated = true

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layoutRes)
    decorateContentView(this, isDecorated)
  }
}
```

Fragment 基类的修改示例：

```kotlin
abstract class BaseFragment(private val layoutRes: Int) : Fragment(),
  LoadingState by LoadingStateImpl(), OnReloadListener {

  open val isDecorated = true

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val root = inflater.inflate(layoutRes, container, false)
    return root.decorate(this, isDecorated)
  }
}
```

这样改造基类后会得到以下的增强：

- 在不影响已有代码的情况下，增加了 [LoadingState](https://github.com/DylanCaiCoding/LoadingStateView/blob/master/loadingstateview-ktx/src/main/java/com/dylanc/loadingstateview/LoadingState.kt) 和 `OnReloadListener` 提供的常用方法。
- 如果担心万一会遇到什么兼容问题，那么在页面重写 `override val isDecorated = false` 属性就会把新增的功能禁用，即使调用了方法也不会生效。

#### 注册默认的标题栏和缺省页

通常项目都有各自的标题栏封装，比如 `<include/>` 的标题栏布局或者自定义的标题栏控件，我们能基于此实现 `ToolbarViewDelegate`。

```kotlin
LoadingStateView.setViewDelegatePool {
  register(ToolbarViewDelegate(), LoadingViewDelegate(), ErrorViewDelegate())
}
```
