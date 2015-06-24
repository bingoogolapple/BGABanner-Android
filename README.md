:running:BGABanner-Android v2.0.0:running:
============

[![Join the chat at https://gitter.im/bingoogolapple/BGABanner-Android](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/bingoogolapple/BGABanner-Android?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
>关于我<br/>
>微博：<a href="http://weibo.com/bingoogol" target="_blank">bingoogolapple</a>&nbsp;&nbsp;&nbsp;&nbsp;主页：<a  href="http://www.bingoogolapple.cn" target="_blank">bingoogolapple.cn</a>&nbsp;&nbsp;&nbsp;&nbsp;邮箱：<a href="mailto:bingoogolapple@gmail.com" target="_blank">bingoogolapple@gmail.com</a>

demo中演示了引导页、以及通过fresco、android-async-http、gson实现广告条的自动轮播效果

**【感谢慕课网的 [千变万化的ViewPager切换动画](http://www.imooc.com/learn/226) 视频教程】**

### 主要功能：
1. 引导界面导航效果
2. 广告条循环自动轮播、手指按下暂停自动轮播、自定义指示器位置
3. ViewPager各种切换动画
4. 支持选中特定页面、支持只有3页时的轮播功能

### 效果图
![引导界面](https://raw.githubusercontent.com/bingoogolapple/BGABanner-Android/server/screenshots/banner1.gif)
![自动轮播1](https://raw.githubusercontent.com/bingoogolapple/BGABanner-Android/server/screenshots/banner2.gif)
![自动轮播1](https://raw.githubusercontent.com/bingoogolapple/BGABanner-Android/server/screenshots/banner3.gif)

### 基本使用

#### 1.添加Gradle依赖

```groovy
dependencies {
    // 记得添加nineoldandroids
    compile 'com.nineoldandroids:library:2.4.0'
    compile 'cn.bingoogolapple:bga-banner:2.0.0@aar'
}
```

#### 2.在布局文件中添加BGABanner

```xml
<cn.bingoogolapple.bgabanner.BGABanner xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/banner_splash_pager"
    style="@style/MatchMatch"
    app:banner_pointAutoPlayAble="false"
    app:banner_pointTopBottomMargin="15dp"/>
```

#### 3.在Activity或者Fragment中配置BGABanner

```java
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        BGABanner banner = (BGABanner)findViewById(R.id.banner_splash_pager);
        // 用Java代码方式设置切换动画
        banner.setTransitionEffect(BGABanner.TransitionEffect.Rotate);
        // banner.setPageTransformer(new RotatePageTransformer());
        // 设置page切换时长
        banner.setPageChangeDuration(1000);
        List<View> views = new ArrayList<>();
        views.add(getPageView(R.drawable.guide_1));
        views.add(getPageView(R.drawable.guide_2));
        views.add(getPageView(R.drawable.guide_3));

        View lastView = getLayoutInflater().inflate(R.layout.view_last, null);
        views.add(lastView);
        lastView.findViewById(R.id.btn_last_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        });
        banner.setViews(views);
        // banner.setCurrentItem(1);
    }

    private View getPageView(@DrawableRes int resid) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(resid);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }
}
```

### 自定义属性说明
```xml
<declare-styleable name="BGABanner">
    <!-- 指示点容器背景 -->
    <attr name="banner_pointContainerBackground" format="reference|color" />
    <!-- 指示点选中时的背景 -->
    <attr name="banner_pointFocusedImg" format="reference" />
    <!-- 指示点未选中时的背景 -->
    <attr name="banner_pointUnfocusedImg" format="reference" />
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
</declare-styleable>
```

### 代码是最好的老师，更多详细用法请查看[demo](https://github.com/bingoogolapple/BGABanner-Android/tree/master/demo):feet:

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
