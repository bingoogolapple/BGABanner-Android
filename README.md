:running:BGABanner-Android:running:
============

## 目录

* [功能介绍](#功能介绍)
* [效果图与示例 apk](#效果图与示例-apk)
* [常见问题](#常见问题)
* [使用](#使用)
* [自定义属性说明](#自定义属性说明)
* [关于我](#关于我)
* [打赏支持](#打赏支持)
* [License](#license)

## 功能介绍

- [x] 引导界面导航效果
- [x] 支持根据服务端返回的数据动态设置广告条的总页数
- [x] 支持大于等于1页时的无限循环自动轮播、手指按下暂停轮播、抬起手指开始轮播
- [x] 支持自定义指示器位置和广告文案位置
- [x] 支持图片指示器和数字指示器
- [x] 支持 ViewPager 各种切换动画
- [x] 支持选中特定页面
- [x] 支持监听 item 点击事件
- [x] 加载网络数据时支持占位图设置，避免出现整个广告条空白的情况
- [x] 多个 ViewPager 跟随滚动

## 效果图与示例 apk

![banner](https://cloud.githubusercontent.com/assets/8949716/17557718/dc235ec4-5f4a-11e6-92b7-144a2a1a1e3f.gif)

[点击下载 BGABannerDemo.apk](http://fir.im/BGABannerDemo) 或扫描下面的二维码安装

![BGABannerDemo apk文件二维](http://7xk9dj.com1.z0.glb.clouddn.com/banner/BGABannerDemo.png)

## 常见问题

1. 结合 Fresco 加载图片请参考 [FrescoDemoActivity](https://github.com/bingoogolapple/BGABanner-Android/blob/master/demo/src/main/java/cn/bingoogolapple/bgabanner/demo/ui/activity/FrescoDemoActivity.java)
2. 自定义 item 布局文件请参考 [FrescoDemoActivity](https://github.com/bingoogolapple/BGABanner-Android/blob/master/demo/src/main/java/cn/bingoogolapple/bgabanner/demo/ui/activity/FrescoDemoActivity.java)

## 使用

### 1.添加 Gradle 依赖
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/cn.bingoogolapple/bga-banner/badge.svg)](https://maven-badges.herokuapp.com/maven-central/cn.bingoogolapple/bga-banner) bga-banner 后面的「latestVersion」指的是左边这个 maven-central 徽章后面的「数字」，请自行替换。

```groovy
dependencies {
    compile 'com.android.support:support-v4:latestVersion'
    compile 'cn.bingoogolapple:bga-banner:latestVersion@aar'
}
```

### 2.在布局文件中添加 BGABanner

```xml
<cn.bingoogolapple.bgabanner.BGABanner
    android:id="@+id/banner_guide_content"
    style="@style/MatchMatch"
    app:banner_pageChangeDuration="1000"
    app:banner_pointAutoPlayAble="false"
    app:banner_pointContainerBackground="@android:color/transparent"
    app:banner_pointDrawable="@drawable/bga_banner_selector_point_hollow"
    app:banner_pointTopBottomMargin="15dp"
    app:banner_transitionEffect="alpha" />
```

### 3.在 Activity 或者 Fragment 中配置 BGABanner 的数据源

有多种配置数据源的方式，这里仅列出三种方式。更多初始化方式请查看 [demo](https://github.com/bingoogolapple/BGABanner-Android/tree/master/demo)，或加网页底部给的 QQ 群咨询

>配置数据源的方式1：通过传入数据模型并结合 Adapter 的方式配置数据源。这种方式主要用于加载网络图片，以及实现少于3页时的无限轮播

```java
mContentBanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
        Glide.with(MainActivity.this)
                .load(model)
                .placeholder(R.drawable.holder)
                .error(R.drawable.holder)
                .centerCrop()
                .dontAnimate()
                .into(itemView);
    }
});

mContentBanner.setData(Arrays.asList("网络图片路径1", "网络图片路径2", "网络图片路径3"), Arrays.asList("提示文字1", "提示文字2", "提示文字3"));
```

> 配置数据源的方式2：通过直接传入视图集合的方式配置数据源，主要用于自定义引导页每个页面布局的情况

```java
List<View> views = new ArrayList<>();
views.add(BGABannerUtil.getItemImageView(this, R.drawable.ic_guide_1));
views.add(BGABannerUtil.getItemImageView(this, R.drawable.ic_guide_2));
views.add(BGABannerUtil.getItemImageView(this, R.drawable.ic_guide_3));
mContentBanner.setData(views);
```

> 配置数据源的方式3：通过传入图片资源 id 的方式配置数据源，主要用于引导页每一页都是只显示图片的情况

```
mContentBanner.setData(R.drawable.uoko_guide_foreground_1, R.drawable.uoko_guide_foreground_2, R.drawable.uoko_guide_foreground_3);
```

### 4.监听广告 item 的单击事件，在 BGABanner 里已经帮开发者处理了防止重复点击事件

```java
mContentBanner.setDelegate(new BGABanner.Delegate<ImageView, String>() {
    @Override
    public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
        Toast.makeText(banner.getContext(), "点击了" + position, Toast.LENGTH_SHORT).show();
    }
});
```

### 5.设置「进入按钮」和「跳过按钮」控件资源 id 及其点击事件，如果进入按钮和跳过按钮有一个不存在的话就传 0，在 BGABanner 里已经帮开发者处理了防止重复点击事件，在 BGABanner 里已经帮开发者处理了「跳过按钮」和「进入按钮」的显示与隐藏

```java
mContentBanner.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
    @Override
    public void onClickEnterOrSkip() {
        startActivity(new Intent(GuideActivity.this, MainActivity.class));
        finish();
    }
});
```

## 自定义属性说明
```xml
<declare-styleable name="BGABanner">
    <!-- 指示点容器背景 -->
    <attr name="banner_pointContainerBackground" format="reference|color" />
    <!-- 指示点背景 -->
    <attr name="banner_pointDrawable" format="reference" />
    <!-- 指示点容器左右内间距 -->
    <attr name="banner_pointContainerLeftRightPadding" format="dimension" />
    <!-- 指示点上下外间距 -->
    <attr name="banner_pointTopBottomMargin" format="dimension" />
    <!-- 指示点左右外间距 -->
    <attr name="banner_pointLeftRightMargin" format="dimension" />
    <!-- 指示器的位置 -->
    <attr name="banner_indicatorGravity">
        <flag name="top" value="0x30" />
        <flag name="bottom" value="0x50" />
        <flag name="left" value="0x03" />
        <flag name="right" value="0x05" />
        <flag name="center_horizontal" value="0x01" />
    </attr>
    <!-- 是否开启自动轮播 -->
    <attr name="banner_pointAutoPlayAble" format="boolean" />
    <!-- 自动轮播的时间间隔 -->
    <attr name="banner_pointAutoPlayInterval" format="integer" />
    <!-- 页码切换过程的时间长度 -->
    <attr name="banner_pageChangeDuration" format="integer" />
    <!-- 页面切换的动画效果 -->
    <attr name="banner_transitionEffect" format="enum">
        <enum name="defaultEffect" value="0" />
        <enum name="alpha" value="1" />
        <enum name="rotate" value="2" />
        <enum name="cube" value="3" />
        <enum name="flip" value="4" />
        <enum name="accordion" value="5" />
        <enum name="zoomFade" value="6" />
        <enum name="fade" value="7" />
        <enum name="zoomCenter" value="8" />
        <enum name="zoomStack" value="9" />
        <enum name="stack" value="10" />
        <enum name="depth" value="11" />
        <enum name="zoom" value="12" />
    </attr>
    <!-- 提示文案的文字颜色 -->
    <attr name="banner_tipTextColor" format="reference|color" />
    <!-- 提示文案的文字大小 -->
    <attr name="banner_tipTextSize" format="dimension" />
    <!-- 加载网络数据时覆盖在 BGABanner 最上层的占位图 -->
    <attr name="banner_placeholderDrawable" format="reference" />
    <!-- 是否是数字指示器 -->
    <attr name="banner_isNumberIndicator" format="boolean" />
    <!-- 数字指示器文字颜色 -->
    <attr name="banner_numberIndicatorTextColor" format="reference|color" />
    <!-- 数字指示器文字大小 -->
    <attr name="banner_numberIndicatorTextSize" format="dimension" />
    <!-- 数字指示器背景 -->
    <attr name="banner_numberIndicatorBackground" format="reference" />
    <!-- 当只有一页数据时是否显示指示器，默认值为 false -->
    <attr name="banner_isNeedShowIndicatorOnOnlyOnePage" format="boolean" />
    <!-- 自动轮播区域距离 BGABanner 底部的距离，用于使指示器区域与自动轮播区域不重叠 -->
    <attr name="banner_contentBottomMargin" format="dimension"/>
</declare-styleable>
```

## 代码是最好的老师，更多详细用法请查看 [demo](https://github.com/bingoogolapple/BGABanner-Android/tree/master/demo):feet:

## 关于我

| 新浪微博 | 个人主页 | 邮箱 | BGA系列开源库QQ群
| ------------ | ------------- | ------------ | ------------ |
| <a href="http://weibo.com/bingoogol" target="_blank">bingoogolapple</a> | <a  href="http://www.bingoogolapple.cn" target="_blank">bingoogolapple.cn</a>  | <a href="mailto:bingoogolapple@gmail.com" target="_blank">bingoogolapple@gmail.com</a> | ![BGA_CODE_CLUB](http://7xk9dj.com1.z0.glb.clouddn.com/BGA_CODE_CLUB.png?imageView2/2/w/200) |

## 打赏支持

如果您觉得 BGA 系列开源库帮你节省了大量的开发时间，请扫描下方的二维码随意打赏，要是能打赏个 10.24 :monkey_face:就太:thumbsup:了。您的支持将鼓励我继续创作:octocat:

如果您目前正打算购买通往墙外的梯子，可以使用我的邀请码「YFQ9Q3B」购买 [Lantern](https://github.com/getlantern/forum)，双方都赠送三个月的专业版使用时间:beers:

<p align="center">
  <img src="http://7xk9dj.com1.z0.glb.clouddn.com/bga_pay.png" width="450">
</p>

## License

    Copyright 2015 bingoogolapple

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
