package cn.bingoogolapple.bgabanner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

import java.lang.ref.WeakReference;
import java.util.List;

import cn.bingoogolapple.bgabanner.transformer.BGAPageTransformer;
import cn.bingoogolapple.bgabanner.transformer.TransitionEffect;

public class BGABanner extends RelativeLayout implements BGAViewPager.AutoPlayDelegate, ViewPager.OnPageChangeListener {
    private static final int RMP = RelativeLayout.LayoutParams.MATCH_PARENT;
    private static final int RWC = RelativeLayout.LayoutParams.WRAP_CONTENT;
    private static final int LWC = LinearLayout.LayoutParams.WRAP_CONTENT;
    private static final int VEL_THRESHOLD = 400;
    private BGAViewPager mViewPager;
    private List<? extends View> mViews;
    private List<String> mTips;
    private LinearLayout mPointRealContainerLl;
    private TextView mTipTv;
    private boolean mAutoPlayAble = true;
    private int mAutoPlayInterval = 3000;
    private int mPageChangeDuration = 800;
    private int mPointGravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    private int mPointLeftRightMargin;
    private int mPointTopBottomMargin;
    private int mPointContainerLeftRightPadding;
    private int mTipTextSize;
    private int mTipTextColor = Color.WHITE;
    private int mPointDrawableResId = R.drawable.bga_banner_selector_point_solid;
    private Drawable mPointContainerBackgroundDrawable;
    private AutoPlayTask mAutoPlayTask;
    private int mPageScrollPosition;
    private float mPageScrollPositionOffset;
    private Delegate mDelegate;
    private TransitionEffect mTransitionEffect;

    public BGABanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BGABanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initDefaultAttrs(context);
        initCustomAttrs(context, attrs);
        initView(context);
    }

    private void initDefaultAttrs(Context context) {
        mAutoPlayTask = new AutoPlayTask(this);

        mPointLeftRightMargin = BGABannerUtil.dp2px(context, 3);
        mPointTopBottomMargin = BGABannerUtil.dp2px(context, 6);
        mPointContainerLeftRightPadding = BGABannerUtil.dp2px(context, 10);
        mTipTextSize = BGABannerUtil.sp2px(context, 8);
        mPointContainerBackgroundDrawable = new ColorDrawable(Color.parseColor("#44aaaaaa"));
        mTransitionEffect = TransitionEffect.Default;
    }

    private void initCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BGABanner);
        final int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            initCustomAttr(typedArray.getIndex(i), typedArray);
        }
        typedArray.recycle();
    }

    private void initCustomAttr(int attr, TypedArray typedArray) {
        if (attr == R.styleable.BGABanner_banner_pointDrawable) {
            mPointDrawableResId = typedArray.getResourceId(attr, R.drawable.bga_banner_selector_point_solid);
        } else if (attr == R.styleable.BGABanner_banner_pointContainerBackground) {
            mPointContainerBackgroundDrawable = typedArray.getDrawable(attr);
        } else if (attr == R.styleable.BGABanner_banner_pointLeftRightMargin) {
            mPointLeftRightMargin = typedArray.getDimensionPixelSize(attr, mPointLeftRightMargin);
        } else if (attr == R.styleable.BGABanner_banner_pointContainerLeftRightPadding) {
            mPointContainerLeftRightPadding = typedArray.getDimensionPixelSize(attr, mPointContainerLeftRightPadding);
        } else if (attr == R.styleable.BGABanner_banner_pointTopBottomMargin) {
            mPointTopBottomMargin = typedArray.getDimensionPixelSize(attr, mPointTopBottomMargin);
        } else if (attr == R.styleable.BGABanner_banner_pointGravity) {
            mPointGravity = typedArray.getInt(attr, mPointGravity);
        } else if (attr == R.styleable.BGABanner_banner_pointAutoPlayAble) {
            mAutoPlayAble = typedArray.getBoolean(attr, mAutoPlayAble);
        } else if (attr == R.styleable.BGABanner_banner_pointAutoPlayInterval) {
            mAutoPlayInterval = typedArray.getInteger(attr, mAutoPlayInterval);
        } else if (attr == R.styleable.BGABanner_banner_pageChangeDuration) {
            mPageChangeDuration = typedArray.getInteger(attr, mPageChangeDuration);
        } else if (attr == R.styleable.BGABanner_banner_transitionEffect) {
            int ordinal = typedArray.getInt(attr, TransitionEffect.Accordion.ordinal());
            mTransitionEffect = TransitionEffect.values()[ordinal];
        } else if (attr == R.styleable.BGABanner_banner_tipTextColor) {
            mTipTextColor = typedArray.getColor(attr, mTipTextColor);
        } else if (attr == R.styleable.BGABanner_banner_tipTextSize) {
            mTipTextSize = typedArray.getDimensionPixelSize(attr, mTipTextSize);
        }
    }

    private void initView(Context context) {
        RelativeLayout pointContainerRl = new RelativeLayout(context);
        if (Build.VERSION.SDK_INT >= 16) {
            pointContainerRl.setBackground(mPointContainerBackgroundDrawable);
        } else {
            pointContainerRl.setBackgroundDrawable(mPointContainerBackgroundDrawable);
        }
        pointContainerRl.setPadding(mPointContainerLeftRightPadding, 0, mPointContainerLeftRightPadding, 0);
        RelativeLayout.LayoutParams pointContainerLp = new RelativeLayout.LayoutParams(RMP, RWC);
        // 处理圆点在顶部还是底部
        if ((mPointGravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.TOP) {
            pointContainerLp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        } else {
            pointContainerLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        }
        addView(pointContainerRl, pointContainerLp);

        mPointRealContainerLl = new LinearLayout(context);
        mPointRealContainerLl.setId(R.id.banner_pointContainerId);
        mPointRealContainerLl.setOrientation(LinearLayout.HORIZONTAL);
        RelativeLayout.LayoutParams pointRealContainerLp = new RelativeLayout.LayoutParams(RWC, RWC);
        pointContainerRl.addView(mPointRealContainerLl, pointRealContainerLp);

        RelativeLayout.LayoutParams tipLp = new RelativeLayout.LayoutParams(RMP, getResources().getDrawable(mPointDrawableResId).getIntrinsicHeight() + 2 * mPointTopBottomMargin);
        mTipTv = new TextView(context);
        mTipTv.setGravity(Gravity.CENTER_VERTICAL);
        mTipTv.setSingleLine(true);
        mTipTv.setEllipsize(TextUtils.TruncateAt.END);
        mTipTv.setTextColor(mTipTextColor);
        mTipTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTipTextSize);
        pointContainerRl.addView(mTipTv, tipLp);

        int horizontalGravity = mPointGravity & Gravity.HORIZONTAL_GRAVITY_MASK;
        // 处理圆点在左边、右边还是水平居中
        if (horizontalGravity == Gravity.LEFT) {
            pointRealContainerLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            tipLp.addRule(RelativeLayout.RIGHT_OF, R.id.banner_pointContainerId);
            mTipTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        } else if (horizontalGravity == Gravity.RIGHT) {
            pointRealContainerLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            tipLp.addRule(RelativeLayout.LEFT_OF, R.id.banner_pointContainerId);
        } else {
            pointRealContainerLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
            tipLp.addRule(RelativeLayout.LEFT_OF, R.id.banner_pointContainerId);
        }
    }

    /**
     * 设置页码切换过程的时间长度
     *
     * @param duration 页码切换过程的时间长度
     */
    public void setPageChangeDuration(int duration) {
        if (duration >= 0 && duration <= 2000) {
            mPageChangeDuration = duration;
            if (mViewPager != null) {
                mViewPager.setPageChangeDuration(duration);
            }
        }
    }

    /**
     * 设置是否开启自动轮播
     *
     * @param autoPlayAble
     */
    public void setAutoPlayAble(boolean autoPlayAble) {
        mAutoPlayAble = autoPlayAble;
    }

    /**
     * 设置自动轮播的时间间隔
     *
     * @param autoPlayInterval
     */
    public void setAutoPlayInterval(int autoPlayInterval) {
        mAutoPlayInterval = autoPlayInterval;
    }

    /**
     * 设置每一页的控件和文案
     *
     * @param views 每一页的控件集合
     * @param tips  每一页的提示文案集合
     */
    public void setViewsAndTips(List<? extends View> views, List<String> tips) {
        if (mAutoPlayAble && views.size() < 2) {
            mAutoPlayAble = false;
        }

        if (tips != null && tips.size() != views.size()) {
            throw new IllegalArgumentException("提示文案数必须等于页面数量");
        }
        mViews = views;
        mTips = tips;

        initPoints();
        initViewPager();
    }

    /**
     * 设置每一页的控件
     *
     * @param views 每一页的控件集合
     */
    public void setViews(List<? extends View> views) {
        setViewsAndTips(views, null);
    }

    /**
     * 设置每一页的提示文案
     *
     * @param tips 提示文案集合
     */
    public void setTips(List<String> tips) {
        if (tips != null && mViews != null && tips.size() != mViews.size()) {
            throw new IllegalArgumentException("提示文案数必须等于页面数量");
        }
        mTips = tips;
    }

    /**
     * 设置是否允许用户手指滑动
     *
     * @param scrollable true表示允许跟随用户触摸滑动，false反之
     */
    public void setAllowUserScrollable(boolean scrollable) {
        mViewPager.setAllowUserScrollable(scrollable);
    }

    /**
     * 添加ViewPager滚动监听器
     *
     * @param listener
     */
    public void addOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mViewPager.addOnPageChangeListener(listener);
    }

    /**
     * 获取当前选中界面索引
     *
     * @return
     */
    public int getCurrentItem() {
        if (mViewPager == null || mViews == null) {
            return 0;
        } else {
            return mViewPager.getCurrentItem() % mViews.size();
        }
    }

    /**
     * 获取广告页面总个数
     *
     * @return
     */
    public int getItemCount() {
        return mViews == null ? 0 : mViews.size();
    }

    public List<? extends View> getViews() {
        return mViews;
    }

    public <VT extends View> VT getItemView(int position) {
        return mViews == null ? null : (VT) mViews.get(position);
    }

    public ImageView getItemImageView(int position) {
        return getItemView(position);
    }

    public List<String> getTips() {
        return mTips;
    }

    public BGAViewPager getViewPager() {
        return mViewPager;
    }

    public void setOverScrollMode(int overScrollMode) {
        if (mViewPager != null) {
            mViewPager.setOverScrollMode(overScrollMode);
        }
    }

    private void initPoints() {
        mPointRealContainerLl.removeAllViews();

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LWC, LWC);
        lp.setMargins(mPointLeftRightMargin, mPointTopBottomMargin, mPointLeftRightMargin, mPointTopBottomMargin);
        ImageView imageView;
        for (int i = 0; i < mViews.size(); i++) {
            imageView = new ImageView(getContext());
            imageView.setLayoutParams(lp);
            imageView.setImageResource(mPointDrawableResId);
            mPointRealContainerLl.addView(imageView);
        }
    }

    private void initViewPager() {
        if (mViewPager != null && this.equals(mViewPager.getParent())) {
            this.removeView(mViewPager);
            mViewPager = null;
        }

        mViewPager = new BGAViewPager(getContext());
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setAdapter(new PageAdapter());
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setPageTransformer(true, BGAPageTransformer.getPageTransformer(mTransitionEffect));

        addView(mViewPager, 0, new RelativeLayout.LayoutParams(RMP, RMP));
        setPageChangeDuration(mPageChangeDuration);

        if (mAutoPlayAble) {
            mViewPager.setAutoPlayDelegate(this);

            int zeroItem = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % mViews.size();
            mViewPager.setCurrentItem(zeroItem);

            startAutoPlay();
        } else {
            switchToPoint(0);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mAutoPlayAble) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    stopAutoPlay();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    startAutoPlay();
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setCurrentItem(int item) {
        if (mViewPager == null || mViews == null || item > getItemCount() - 1) {
            return;
        }

        if (mAutoPlayAble) {
            int realCurrentItem = mViewPager.getCurrentItem();
            int currentItem = realCurrentItem % mViews.size();
            int offset = item - currentItem;

            // 这里要使用循环递增或递减设置，否则会ANR
            if (offset < 0) {
                for (int i = -1; i >= offset; i--) {
                    mViewPager.setCurrentItem(realCurrentItem + i, false);
                }
            } else if (offset > 0) {
                for (int i = 1; i <= offset; i++) {
                    mViewPager.setCurrentItem(realCurrentItem + i, false);
                }
            }

            startAutoPlay();
        } else {
            mViewPager.setCurrentItem(item, false);
        }
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            startAutoPlay();
        } else if (visibility == INVISIBLE) {
            stopAutoPlay();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAutoPlay();
    }

    public void startAutoPlay() {
        stopAutoPlay();
        if (mAutoPlayAble) {
            postDelayed(mAutoPlayTask, mAutoPlayInterval);
        }
    }

    public void stopAutoPlay() {
        if (mAutoPlayAble) {
            removeCallbacks(mAutoPlayTask);
        }
    }

    private void switchToPoint(int newCurrentPoint) {
        for (int i = 0; i < mPointRealContainerLl.getChildCount(); i++) {
            mPointRealContainerLl.getChildAt(i).setEnabled(false);
        }
        mPointRealContainerLl.getChildAt(newCurrentPoint).setEnabled(true);

        if (mTipTv != null && mTips != null) {
            mTipTv.setText(mTips.get(newCurrentPoint));
        }
    }

    /**
     * 设置页面切换换动画
     *
     * @param effect
     */
    public void setTransitionEffect(TransitionEffect effect) {
        mTransitionEffect = effect;
        if (mViewPager != null) {
            initViewPager();
            BGABannerUtil.resetPageTransformer(mViews);
        }
    }

    /**
     * 设置自定义页面切换动画
     *
     * @param transformer
     */
    public void setPageTransformer(ViewPager.PageTransformer transformer) {
        if (transformer != null && mViewPager != null) {
            mViewPager.setPageTransformer(true, transformer);
        }
    }

    /**
     * 切换到下一页
     */
    private void switchToNextPage() {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
        }
    }

    @Override
    public void handleAutoPlayActionUpOrCancel(float xVelocity) {
        if (mViewPager != null && mPageScrollPosition < mViewPager.getCurrentItem()) {
            // 往右滑
            if (xVelocity > VEL_THRESHOLD || (mPageScrollPositionOffset < 0.7f && xVelocity > -VEL_THRESHOLD)) {
                mViewPager.setBannerCurrentItemInternal(mPageScrollPosition);
            } else {
                mViewPager.setBannerCurrentItemInternal(mPageScrollPosition + 1);
            }
        } else {
            // 往左滑
            if (xVelocity < -VEL_THRESHOLD || (mPageScrollPositionOffset > 0.3f && xVelocity < VEL_THRESHOLD)) {
                mViewPager.setBannerCurrentItemInternal(mPageScrollPosition + 1);
            } else {
                mViewPager.setBannerCurrentItemInternal(mPageScrollPosition);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        switchToPoint(position % mViews.size());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mPageScrollPosition = position;
        mPageScrollPositionOffset = positionOffset;
        if (mTipTv != null && mTips != null) {
            if (positionOffset > 0.5) {
                mTipTv.setText(mTips.get((position + 1) % mTips.size()));
                ViewHelper.setAlpha(mTipTv, positionOffset);
            } else {
                ViewHelper.setAlpha(mTipTv, 1 - positionOffset);
                mTipTv.setText(mTips.get(position % mTips.size()));
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    public void setDelegate(Delegate delegate) {
        mDelegate = delegate;
    }

    private class PageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mViews == null ? 0 : (mAutoPlayAble ? Integer.MAX_VALUE : mViews.size());
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final int finalPosition = position % mViews.size();
            View view = mViews.get(finalPosition);

            // 在destroyItem方法中销毁的话，当只有3页时会有问题
            if (mAutoPlayAble && container.equals(view.getParent())) {
                container.removeView(view);
            }

            if (mDelegate != null) {
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDelegate.onClickBannerItem(finalPosition);
                    }
                });
            }

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (mAutoPlayAble) {
                return;
            }

            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    private static class AutoPlayTask implements Runnable {
        private final WeakReference<BGABanner> mBanner;

        private AutoPlayTask(BGABanner banner) {
            mBanner = new WeakReference<>(banner);
        }

        @Override
        public void run() {
            BGABanner banner = mBanner.get();
            if (banner != null) {
                banner.switchToNextPage();
                banner.startAutoPlay();
            }
        }
    }

    public interface Delegate {
        void onClickBannerItem(int position);
    }
}