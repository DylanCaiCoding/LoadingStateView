# 结合 ViewBinding 使用

## 基础用法

如果要同时使用 `LoadingStateView` 和 `ViewBinding`，需要先得到对应的 `ViewBinding` 实例，再用根视图去创建 `LoadingStateView`。

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

结合了 ViewBinding 才是个人理想中的用法。