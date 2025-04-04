# LoadingStateView

English | [中文](README_ZH.md)

[![](https://www.jitpack.io/v/DylanCaiCoding/LoadingStateView.svg)](https://www.jitpack.io/#DylanCaiCoding/LoadingLoadingStateView) [![License](https://img.shields.io/badge/License-Apache--2.0-blue.svg)](https://github.com/DylanCaiCoding/LoadingStateView/blob/master/LICENSE)

`LoadingStateView` is a highly expandable Android library for showing loading status view on the low-coupling way, the core function is implemented with a [Kotlin file](https://github.com/DylanCaiCoding/LoadingStateView/blob/master/loadingstateview/src/main/java/com/dylanc/loadingstateview/LoadingStateView.kt) of over 200 lines. it not only shows different view like loading, content, error, empty and customized view when loading network data, but also manages title bar.

**Major update: With the Kotlin feature, you can quickly add all functionality to the base class without affecting existing code. The overall usage is further simplified with removing the `ViewHolder`. It is recommended to upgrade!**

## Feature

- No need to add view code to the layout.
- Quickly add all functionality to the base class without affecting existing code. (Kotlin)
- Support for use for Activity, Fragment, RecyclerView, View.
- Support for show custom views.
- Support for managing the title bar and add multiple headers.
- Support for set reload event.
- Support for update views anytime.
- Support for use with most third-party libraries.

## Demo

Click or scan QR code to download

[![QR code](docs/img/app_download_qr_code.png)](https://www.pgyer.com/loadinghelper)

| [Activity(error)](https://github.com/DylanCaiCoding/LoadingStateView/blob/master/sample-java/src/main/java/com/dylanc/loadingstateview/sample/java/ui/ActErrorActivity.java) | [View(placeholder)](https://github.com/DylanCaiCoding/LoadingStateView/blob/master/sample-java/src/main/java/com/dylanc/loadingstateview/sample/java/ui/ViewPlaceholderActivity.java) | [ViewPager(timeout)](https://github.com/DylanCaiCoding/LoadingStateView/blob/master/sample-java/src/main/java/com/dylanc/loadingstateview/sample/java/ui/ViewPagerActivity.java) | [RecyclerView(cool loading)](https://github.com/DylanCaiCoding/LoadingStateView/blob/master/sample-java/src/main/java/com/dylanc/loadingstateview/sample/java/ui/RecyclerViewActivity.java) |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
|                 ![](docs/gif/activity_error.gif)                  |                ![](docs/gif/view_placeholder.gif)                 |                ![](docs/gif/viewpager_timeout.gif)                |              ![](docs/gif/recyclerview_loading.gif)               |

| [SpecialHeader(custom)](https://github.com/DylanCaiCoding/LoadingStateView/blob/master/sample-java/src/main/java/com/dylanc/loadingstateview/sample/java/ui/CustomHeaderActivity.java) | [MultipleHeader(search)](https://github.com/DylanCaiCoding/LoadingStateView/blob/master/sample-java/src/main/java/com/dylanc/loadingstateview/sample/java/ui/MultipleHeaderActivity.java) | [SpecialDecorView(scrolling)](https://github.com/DylanCaiCoding/LoadingStateView/blob/master/sample-java/src/main/java/com/dylanc/loadingstateview/sample/java/ui/ScrollingToolbarActivity.java) | [BottomDecorView(editor)](https://github.com/DylanCaiCoding/LoadingStateView/blob/master/sample-java/src/main/java/com/dylanc/loadingstateview/sample/java/ui/BottomEditorActivity.java) |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
|              ![](docs/gif/special_header_custom.gif)              |             ![](docs/gif/multiple_header_search.gif)              |             ![](docs/gif/special_decor_scrolling.gif)             |               ![](docs/gif/bottom_decor_editor.gif)               |

## Usage

:pencil: **[>> Usage documentation <<](https://dylancaicoding.github.io/LoadingStateView)**

## Gradle

Add it in your root `build.gradle` at the end of repositories:

```groovy
allprojects {
    repositories {
        // ...
        maven { url 'https://www.jitpack.io' }
    }
}
```

Add dependencies in your module `build.gradle` :

```groovy
dependencies {
    // java
    implementation 'com.github.DylanCaiCoding.LoadingStateView:loadingstateview:5.0.0'

    // kotlin
    implementation 'com.github.DylanCaiCoding.LoadingStateView:loadingstateview-ktx:5.0.0'
}
```


## Change log

[Releases](https://github.com/DylanCaiCoding/LoadingStateView/releases)

## Author's other libraries

| Library                                                      | Description                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| [Longan](https://github.com/DylanCaiCoding/Longan)           | Probably the best Kotlin utils library for Android.  |
| [ViewBindingKTX](https://github.com/DylanCaiCoding/ViewBindingKTX) | The most comprehensive utils of ViewBinding.                 |
| [MMKV-KTX](https://github.com/DylanCaiCoding/MMKV-KTX)       | Use MMKV with property delegates.                                      |
| [MultiBaseUrls](https://github.com/DylanCaiCoding/MultiBaseUrls) | Use annotation to allow Retrofit to support multiple baseUrl and dynamically change baseUrl |
| [Tracker](https://github.com/DylanCaiCoding/Tracker)       | A lightweight tracking framework based on the tracking idea of Buzzvideo.|

## Thanks

- [luckbilly/Gloading](https://github.com/luckybilly/Gloading) Optimize my library standing on the shoulders of giants.
- [drakeet/MultiType](https://github.com/drakeet/MultiType)  Referenced the usage of multiple adapters.
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
