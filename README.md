:running:BGABanner-Android:running:
============

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/cn.bingoogolapple/bga-banner/badge.svg)](https://maven-badges.herokuapp.com/maven-central/cn.bingoogolapple/bga-banner)

## 主要功能：
- [x] 引导界面导航效果
- [x] 支持根据服务端返回的数据动态设置广告条的总页数
- [x] 支持大于等于1页时的无限循环自动轮播、手指按下暂停轮播、抬起手指开始轮播
- [x] 支持自定义指示器位置和广告文案位置
- [x] 支持 ViewPager 各种切换动画
- [x] 支持选中特定页面
- [x] 支持监听 item 点击事件
- [x] 加载网络数据时支持暂未图设置，避免出现空白
- [ ] 多个 ViewPager 跟随滚动

## 效果图与示例 apk
![引导界面](http://7xk9dj.com1.z0.glb.clouddn.com/banner/206banner1.gif?imageView2/2/w/250)
![自动轮播1](http://7xk9dj.com1.z0.glb.clouddn.com/banner/206banner2.gif?imageView2/2/w/250)
![自动轮播1](http://7xk9dj.com1.z0.glb.clouddn.com/banner/206banner3.gif?imageView2/2/w/250)

[点击下载 BGABannerDemo.apk](http://fir.im/BGABannerDemo) 或扫描下面的二维码安装

![BGABannerDemo apk文件二维](http://7xk9dj.com1.z0.glb.clouddn.com/banner/BGABannerDemo.png)

## 基本使用

#### 1.添加Gradle依赖 [![Maven Central](https://maven-badges.herokuapp.com/maven-central/cn.bingoogolapple/bga-banner/badge.svg)](https://maven-badges.herokuapp.com/maven-central/cn.bingoogolapple/bga-banner)

```groovy
dependencies {
    compile 'com.nineoldandroids:library:2.4.0'
    compile 'com.android.support:support-v4:latestVersion'
    compile 'cn.bingoogolapple:bga-banner:latestVersion@aar'
}
```

#### 2.在布局文件中添加BGABanner

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

#### 3.在Activity或者Fragment中配置BGABanner

有多种初始化方式，这里仅列出两种方式。更多初始化方式请查看 [demo](https://github.com/bingoogolapple/BGABanner-Android/tree/master/demo)，或加网页底部给的 QQ 群咨询

>初始化方式1：通过传入数据模型并结合Adapter的方式初始化

```java
mContentBanner.setAdapter(new BGABanner.Adapter() {
    @Override
    public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
        ((ImageView)view).setImageResource((int)model);
    }
});
mContentBanner.setData(Arrays.asList(R.drawable.ic_guide_1, R.drawable.ic_guide_2, R.drawable.ic_guide_3), null);
```

> 初始化方式2：通过直接传入视图集合的方式初始化

```java
List<View> views = new ArrayList<>();
views.add(BGABannerUtil.getItemImageView(this, R.drawable.ic_guide_1));
views.add(BGABannerUtil.getItemImageView(this, R.drawable.ic_guide_2));
views.add(BGABannerUtil.getItemImageView(this, R.drawable.ic_guide_3));
mContentBanner.setData(views);
```

#### 4.监听广告 item 的单击事件

```java
mContentBanner.setOnItemClickListener(new BGABanner.OnItemClickListener() {
    @Override
    public void onBannerItemClick(BGABanner banner, View view, Object model, int position) {
        Log.i(TAG, "点击了第" + (position + 1) + "页");
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
    <!-- 指示点的位置 -->
    <attr name="banner_pointGravity">
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
    <!-- 加载网络数据时覆盖在BGABanner最上层的占位图 -->
    <attr name="banner_placeholderDrawable" format="reference" />
</declare-styleable>
```

## 代码是最好的老师，更多详细用法请查看[demo](https://github.com/bingoogolapple/BGABanner-Android/tree/master/demo):feet:

## 关于我

| 新浪微博 | 个人主页 | 邮箱 | BGA系列开源库QQ群
| ------------ | ------------- | ------------ | ------------ |
| <a href="http://weibo.com/bingoogol" target="_blank">bingoogolapple</a> | <a  href="http://www.bingoogolapple.cn" target="_blank">bingoogolapple.cn</a>  | <a href="mailto:bingoogolapple@gmail.com" target="_blank">bingoogolapple@gmail.com</a> | ![BGA_CODE_CLUB](http://7xk9dj.com1.z0.glb.clouddn.com/BGA_CODE_CLUB.png?imageView2/2/w/200) |

## 打赏支持

如果觉得 BGA 系列开源库对您有用，请随意打赏。

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
