# 结合 ViewBinding 使用

## 基础用法

如果要同时使用 `LoadingStateView` 和 `ViewBinding`，需要先得到对应的 `ViewBinding` 实例，再用其根视图去创建 `LoadingStateView`。

<!-- tabs:start -->

#### **Kotlin**

```kotlin
val loadingStateView = LoadingStateView(binding.root, onReloadListener)
```

#### **Java**

```java
LoadingStateView loadingStateView = new LoadingStateView(binding.getRoot(), onReloadListener); 
```

<!-- tabs:end -->

## Kotlin 委托用法

委托用法再结合上 ViewBinding 才是个人理想中的用法。会使用到个人的另一个库 [ViewBindingKTX](https://github.com/DylanCaiCoding/ViewBindingKTX)，可以快速集成 ViewBinding 到基类中。

添加依赖：

```groovy
implementation 'com.github.DylanCaiCoding.ViewBindingKTX:viewbinding-base:2.1.0'
```

根据[文档](https://dylancaicoding.github.io/ViewBindingKTX/#/zh/baseclass)集成 ViewBinding，再对 ViewBinding 的根视图进行装饰。以下是在委托用法基础上修改的代码：

<!-- tabs:start -->

#### **Activity**

![img.png](../img/base_binding_activity_code.png)

<details>
  <summary>查看代码</summary>

```kotlin
abstract class BaseBindingActivity<VB : ViewBinding> : AppCompatActivity(),
  LoadingState by LoadingStateDelegate(), ActivityBinding<VB> by ActivityBindingDelegate() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentViewWithBinding()
    binding.root.decorate(this)
  }
}
```

</details>

#### **Fragment**

![img.png](../img/base_binding_fragment_code.png)

<details>
  <summary>查看代码</summary>

```kotlin
abstract class BaseBindingFragment<VB : ViewBinding> : Fragment(),
  LoadingState by LoadingStateDelegate(), FragmentBinding<VB> by FragmentBindingDelegate() {

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View? {
    return createViewWithBinding(inflater, container).decorate(this)
  }
}
```

</details>

<!-- tabs:end -->

这样封装后不仅能在 Activity 或 Fragment 获取 `binding` 属性，还能很方便地指定显示缺省页的区域。

比如我们在已有的项目迭代开发，一些页面的布局已经写了标题栏。如果直接调用 `showLoadingView()` 函数，缺省页会把标题栏给覆盖了，通常要在标题栏下方显示缺省页，此时就可以重写 `contentView` 属性，声明在哪个控件显示缺省页，比如：

```kotlin
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    showLoadingView()
    // ...
  }
  
  override val contentView get() = binding.container
}
```

### 已有的基类如何修改

由于要给基类增加 ViewBinding 泛型，肯定不可能直接修改基类，这会影响到已有的代码，建议继承原基类再扩展出一个支持 ViewBinding 的基类。

假设已有的基类是这种常见的封装，通过 `getLayoutId()` 函数去设置布局。

```java
public abstract class BaseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutId());
    initData();
    initViews();
  }

  public abstract int getLayoutId();
  public abstract void initData();
  public abstract void initViews();
}
```

目前直接继承是实现不了的，因为需要重写 `setContentView()` 的代码，所以要先将 `setContentView()` 抽到一个函数中。

```java
@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  initContentView();
  initData();
  initViews();
}

protected void initContentView() {
  setContentView(getLayoutId());
}
```

之后就可以继承基类重写该函数替换掉原来的 `setContentView()` 工作。

```kotlin
abstract class BaseBindingActivity<VB : ViewBinding> : BaseActivity(),
  LoadingState by LoadingStateDelegate(), OnReloadListener, Decorative,
  ActivityBinding<VB> by ActivityBindingDelegate() {

  override fun initContentView() {
    setContentViewWithBinding()
    binding.root.decorate(this, this)
  }

  override fun getLayoutId() = -1 // 使用 ViewBinding 后，就不需要布局 id 了
}
```

Fragment 的修改也同理。