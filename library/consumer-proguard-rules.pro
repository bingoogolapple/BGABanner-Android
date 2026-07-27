-keep class cn.bingoogolapple.bgabanner.BGAViewPager { *; }

# 保护对 AndroidX ViewPager 私有成员的反射访问（自定义翻页时长、自动轮播、fling 速度）。
# 注意：setCurrentItemInternal 在 AndroidX 源码里是包级私有（无修饰符），keep 规则里不能写 private，否则匹配不到。
-keepclassmembers class androidx.viewpager.widget.ViewPager {
    private android.widget.Scroller mScroller;
    private int mActivePointerId;
    private android.view.VelocityTracker mVelocityTracker;
    private int mMaximumVelocity;
    void setCurrentItemInternal(int, boolean, boolean);
}