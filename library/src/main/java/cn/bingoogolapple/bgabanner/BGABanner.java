package cn.bingoogolapple.bgabanner;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.transformer.BGAPageTransformer;
import cn.bingoogolapple.bgabanner.transformer.TransitionEffect;

public class BGABanner extends RelativeLayout implements BGAViewPager.AutoPlayDelegate, ViewPager.OnPageChangeListener {
    private static final int RMP = RelativeLayout.LayoutParams.MATCH_PARENT;
    private static final int RWC = RelativeLayout.LayoutParams.WRAP_CONTENT;
    private static final int LWC = LinearLayout.LayoutParams.WRAP_CONTENT;
    private static final int NO_PLACEHOLDER_DRAWABLE = -1;
    private static final int VEL_THRESHOLD = 400;
    private BGAViewPager mViewPager;
    private List<View> mHackyViews;
    private List<View> mViews;
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
    private TransitionEffect mTransitionEffect;
    private ImageView mPlaceholderIv;
    private ImageView.ScaleType mPlaceholderScaleType = ImageView.ScaleType.CENTER_CROP;
    private int mPlaceholderDrawableResId = NO_PLACEHOLDER_DRAWABLE;
    private List<? extends Object> mModels;
    private Delegate mDelegate;
    private Adapter mAdapter;
    private int mOverScrollMode = OVER_SCROLL_NEVER;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private boolean mIsNumberIndicator = false;
    private TextView mNumberIndicatorTv;
    private int mNumberIndicatorTextColor = Color.WHITE;
    private int mNumberIndicatorTextSize;
    private Drawable mNumberIndicatorBackground;
    private boolean mIsNeedShowIndicatorOnOnlyOnePage;
    private boolean mAllowUserScrollable = true;
    private View mSkipView;
    private View mEnterView;
    private GuideDelegate mGuideDelegate;
    private int mContentBottomMargin;
    private boolean mIsFirstInvisible = true;

    private static final ImageView.ScaleType[] sScaleTypeArray = {
            ImageView.ScaleType.MATRIX,
            ImageView.ScaleType.FIT_XY,
            ImageView.ScaleType.FIT_START,
            ImageView.ScaleType.FIT_CENTER,
            ImageView.ScaleType.FIT_END,
            ImageView.ScaleType.CENTER,
            ImageView.ScaleType.CENTER_CROP,
            ImageView.ScaleType.CENTER_INSIDE
    };

    private BGAOnNoDoubleClickListener mGuideOnNoDoubleClickListener = new BGAOnNoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View v) {
            if (mGuideDelegate != null) {
                mGuideDelegate.onClickEnterOrSkip();
            }
        }
    };

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
        mTipTextSize = BGABannerUtil.sp2px(context, 10);
        mPointContainerBackgroundDrawable = new ColorDrawable(Color.parseColor("#44aaaaaa"));
        mTransitionEffect = TransitionEffect.Default;
        mNumberIndicatorTextSize = BGABannerUtil.sp2px(context, 10);

        mContentBottomMargin = 0;
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
        } else if (attr == R.styleable.BGABanner_banner_indicatorGravity) {
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
        } else if (attr == R.styleable.BGABanner_banner_placeholderDrawable) {
            mPlaceholderDrawableResId = typedArray.getResourceId(attr, mPlaceholderDrawableResId);
        } else if (attr == R.styleable.BGABanner_banner_isNumberIndicator) {
            mIsNumberIndicator = typedArray.getBoolean(attr, mIsNumberIndicator);
        } else if (attr == R.styleable.BGABanner_banner_numberIndicatorTextColor) {
            mNumberIndicatorTextColor = typedArray.getColor(attr, mNumberIndicatorTextColor);
        } else if (attr == R.styleable.BGABanner_banner_numberIndicatorTextSize) {
            mNumberIndicatorTextSize = typedArray.getDimensionPixelSize(attr, mNumberIndicatorTextSize);
        } else if (attr == R.styleable.BGABanner_banner_numberIndicatorBackground) {
            mNumberIndicatorBackground = typedArray.getDrawable(attr);
        } else if (attr == R.styleable.BGABanner_banner_isNeedShowIndicatorOnOnlyOnePage) {
            mIsNeedShowIndicatorOnOnlyOnePage = typedArray.getBoolean(attr, mIsNeedShowIndicatorOnOnlyOnePage);
        } else if (attr == R.styleable.BGABanner_banner_contentBottomMargin) {
            mContentBottomMargin = typedArray.getDimensionPixelSize(attr, mContentBottomMargin);
        } else if (attr == R.styleable.BGABanner_android_scaleType) {
            final int index = typedArray.getInt(attr, -1);
            if (index >= 0 && index < sScaleTypeArray.length) {
                mPlaceholderScaleType = sScaleTypeArray[index];
            }
        }
    }

    private void initView(Context context) {
        RelativeLayout pointContainerRl = new RelativeLayout(context);
        if (Build.VERSION.SDK_INT >= 16) {
            pointContainerRl.setBackground(mPointContainerBackgroundDrawable);
        } else {
            pointContainerRl.setBackgroundDrawable(mPointContainerBackgroundDrawable);
        }
        pointContainerRl.setPadding(mPointContainerLeftRightPadding, mPointTopBottomMargin, mPointContainerLeftRightPadding, mPointTopBottomMargin);
        RelativeLayout.LayoutParams pointContainerLp = new RelativeLayout.LayoutParams(RMP, RWC);
        // 处理圆点在顶部还是底部
        if ((mPointGravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.TOP) {
            pointContainerLp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        } else {
            pointContainerLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        }
        addView(pointContainerRl, pointContainerLp);


        RelativeLayout.LayoutParams indicatorLp = new RelativeLayout.LayoutParams(RWC, RWC);
        indicatorLp.addRule(CENTER_VERTICAL);
        if (mIsNumberIndicator) {
            mNumberIndicatorTv = new TextView(context);
            mNumberIndicatorTv.setId(R.id.banner_indicatorId);
            mNumberIndicatorTv.setGravity(Gravity.CENTER_VERTICAL);
            mNumberIndicatorTv.setSingleLine(true);
            mNumberIndicatorTv.setEllipsize(TextUtils.TruncateAt.END);
            mNumberIndicatorTv.setTextColor(mNumberIndicatorTextColor);
            mNumberIndicatorTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mNumberIndicatorTextSize);
            mNumberIndicatorTv.setVisibility(View.INVISIBLE);
            if (mNumberIndicatorBackground != null) {
                if (Build.VERSION.SDK_INT >= 16) {
                    mNumberIndicatorTv.setBackground(mNumberIndicatorBackground);
                } else {
                    mNumberIndicatorTv.setBackgroundDrawable(mNumberIndicatorBackground);
                }
            }
            pointContainerRl.addView(mNumberIndicatorTv, indicatorLp);
        } else {
            mPointRealContainerLl = new LinearLayout(context);
            mPointRealContainerLl.setId(R.id.banner_indicatorId);
            mPointRealContainerLl.setOrientation(LinearLayout.HORIZONTAL);
            mPointRealContainerLl.setGravity(Gravity.CENTER_VERTICAL);
            pointContainerRl.addView(mPointRealContainerLl, indicatorLp);
        }

        RelativeLayout.LayoutParams tipLp = new RelativeLayout.LayoutParams(RMP, RWC);
        tipLp.addRule(CENTER_VERTICAL);
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
            indicatorLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            tipLp.addRule(RelativeLayout.RIGHT_OF, R.id.banner_indicatorId);
            mTipTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        } else if (horizontalGravity == Gravity.RIGHT) {
            indicatorLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            tipLp.addRule(RelativeLayout.LEFT_OF, R.id.banner_indicatorId);
        } else {
            indicatorLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
            tipLp.addRule(RelativeLayout.LEFT_OF, R.id.banner_indicatorId);
        }

        showPlaceholder();
    }

    public void showPlaceholder() {
        if (mPlaceholderIv == null && mPlaceholderDrawableResId != NO_PLACEHOLDER_DRAWABLE) {
            mPlaceholderIv = BGABannerUtil.getItemImageView(getContext(), mPlaceholderDrawableResId);
            mPlaceholderIv.setScaleType(mPlaceholderScaleType);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RMP, RMP);
            layoutParams.setMargins(0, 0, 0, mContentBottomMargin);
            addView(mPlaceholderIv, layoutParams);
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
     * 设置是否开启自动轮播，需要在 setData 方法之前调用，并且调了该方法后必须再调用一次 setData 方法
     * 例如根据图片当图片数量大于 1 时开启自动轮播，等于 1 时不开启自动轮播
     * mDefaultBanner.setAutoPlayAble(bannerModel.imgs.size() > 1);
     * mDefaultBanner.setData(bannerModel.imgs, bannerModel.tips);
     *
     * @param autoPlayAble
     */
    public void setAutoPlayAble(boolean autoPlayAble) {
        mAutoPlayAble = autoPlayAble;

        stopAutoPlay();

        if (mViewPager != null && mViewPager.getAdapter() != null) {
            mViewPager.getAdapter().notifyDataSetChanged();
        }
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
     * 设置每一页的控件、数据模型和文案
     *
     * @param views  每一页的控件集合
     * @param models 每一页的数据模型集合
     * @param tips   每一页的提示文案集合
     */
    public void setData(List<View> views, List<? extends Object> models, List<String> tips) {
        if (views == null || views.size() < 1) {
            mAutoPlayAble = false;
            views = new ArrayList<>();
            models = new ArrayList<>();
            tips = new ArrayList<>();
        }
        if (mAutoPlayAble && views.size() < 3 && mHackyViews == null) {
            mAutoPlayAble = false;
        }

        mModels = models;
        mViews = views;
        mTips = tips;

        initIndicator();
        initViewPager();
        removePlaceholder();
    }

    /**
     * 设置布局资源id、数据模型和文案
     *
     * @param layoutResId item布局文件资源id
     * @param models      每一页的数据模型集合
     * @param tips        每一页的提示文案集合
     */
    public void setData(@LayoutRes int layoutResId, List<? extends Object> models, List<String> tips) {
        mViews = new ArrayList<>();
        if (models == null) {
            models = new ArrayList<>();
            tips = new ArrayList<>();
        }
        for (int i = 0; i < models.size(); i++) {
            mViews.add(View.inflate(getContext(), layoutResId, null));
        }
        if (mAutoPlayAble && mViews.size() < 3) {
            mHackyViews = new ArrayList<>(mViews);
            mHackyViews.add(View.inflate(getContext(), layoutResId, null));
            if (mHackyViews.size() == 2) {
                mHackyViews.add(View.inflate(getContext(), layoutResId, null));
            }
        }
        setData(mViews, models, tips);
    }

    /**
     * 设置数据模型和文案，布局资源默认为 ImageView
     *
     * @param models 每一页的数据模型集合
     * @param tips   每一页的提示文案集合
     */
    public void setData(List<? extends Object> models, List<String> tips) {
        setData(R.layout.bga_banner_item_image, models, tips);
    }

    /**
     * 设置每一页的控件集合，主要针对引导页的情况
     *
     * @param views 每一页的控件集合
     */
    public void setData(List<View> views) {
        setData(views, null, null);
    }

    /**
     * 设置每一页图片的资源 id，主要针对引导页的情况
     *
     * @param resIds
     */
    public void setData(@DrawableRes int... resIds) {
        List<View> views = new ArrayList<>();
        for (int resId : resIds) {
            views.add(BGABannerUtil.getItemImageView(getContext(), resId));
        }
        setData(views);
    }

    /**
     * 设置是否允许用户手指滑动
     *
     * @param allowUserScrollable true表示允许跟随用户触摸滑动，false反之
     */
    public void setAllowUserScrollable(boolean allowUserScrollable) {
        mAllowUserScrollable = allowUserScrollable;
        if (mViewPager != null) {
            mViewPager.setAllowUserScrollable(mAllowUserScrollable);
        }
    }

    /**
     * 添加ViewPager滚动监听器
     *
     * @param onPageChangeListener
     */
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

    /**
     * 设置进入按钮和跳过按钮控件资源 id，需要开发者自己处理这两个按钮的点击事件
     *
     * @param enterResId 进入按钮控件
     * @param skipResId  跳过按钮控件
     */
    public void setEnterSkipViewId(int enterResId, int skipResId) {
        if (enterResId != 0) {
            mEnterView = ((Activity) getContext()).findViewById(enterResId);
        }
        if (skipResId != 0) {
            mSkipView = ((Activity) getContext()).findViewById(skipResId);
        }
    }

    /**
     * 设置进入按钮和跳过按钮控件资源 id 及其点击事件监听器
     * 如果进入按钮和跳过按钮有一个不存在的话就传 0
     * 在 BGABanner 里已经帮开发者处理了重复点击事件
     * 在 BGABanner 里已经帮开发者处理了「跳过按钮」和「进入按钮」的显示与隐藏
     *
     * @param enterResId    进入按钮控件资源 id，没有的话就传 0
     * @param skipResId     跳过按钮控件资源 id，没有的话就传 0
     * @param guideDelegate 引导页「进入」和「跳过」按钮点击事件监听器
     */
    public void setEnterSkipViewIdAndDelegate(int enterResId, int skipResId, GuideDelegate guideDelegate) {
        if (guideDelegate != null) {
            mGuideDelegate = guideDelegate;
            if (enterResId != 0) {
                mEnterView = ((Activity) getContext()).findViewById(enterResId);
                mEnterView.setOnClickListener(mGuideOnNoDoubleClickListener);
            }
            if (skipResId != 0) {
                mSkipView = ((Activity) getContext()).findViewById(skipResId);
                mSkipView.setOnClickListener(mGuideOnNoDoubleClickListener);
            }
        }
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
        mOverScrollMode = overScrollMode;
        if (mViewPager != null) {
            mViewPager.setOverScrollMode(mOverScrollMode);
        }
    }

    private void initIndicator() {
        if (mPointRealContainerLl != null) {
            mPointRealContainerLl.removeAllViews();

            if (mIsNeedShowIndicatorOnOnlyOnePage || (!mIsNeedShowIndicatorOnOnlyOnePage && mViews.size() > 1)) {
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
        }
        if (mNumberIndicatorTv != null) {
            if (mIsNeedShowIndicatorOnOnlyOnePage || (!mIsNeedShowIndicatorOnOnlyOnePage && mViews.size() > 1)) {
                mNumberIndicatorTv.setVisibility(View.VISIBLE);
            } else {
                mNumberIndicatorTv.setVisibility(View.INVISIBLE);
            }
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
        mViewPager.setOverScrollMode(mOverScrollMode);
        mViewPager.setAllowUserScrollable(mAllowUserScrollable);
        mViewPager.setPageTransformer(true, BGAPageTransformer.getPageTransformer(mTransitionEffect));
        setPageChangeDuration(mPageChangeDuration);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RMP, RMP);
        layoutParams.setMargins(0, 0, 0, mContentBottomMargin);
        addView(mViewPager, 0, layoutParams);

        if (mEnterView != null || mSkipView != null) {
            mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (position == getItemCount() - 2) {
                        if (mEnterView != null) {
                            ViewCompat.setAlpha(mEnterView, positionOffset);
                        }
                        if (mSkipView != null) {
                            ViewCompat.setAlpha(mSkipView, 1.0f - positionOffset);
                        }

                        if (positionOffset > 0.5f) {
                            if (mEnterView != null) {
                                mEnterView.setVisibility(View.VISIBLE);
                            }
                            if (mSkipView != null) {
                                mSkipView.setVisibility(View.GONE);
                            }
                        } else {
                            if (mEnterView != null) {
                                mEnterView.setVisibility(View.GONE);
                            }
                            if (mSkipView != null) {
                                mSkipView.setVisibility(View.VISIBLE);
                            }
                        }
                    } else if (position == getItemCount() - 1) {
                        if (mSkipView != null) {
                            mSkipView.setVisibility(View.GONE);
                        }
                        if (mEnterView != null) {
                            mEnterView.setVisibility(View.VISIBLE);
                            ViewCompat.setAlpha(mEnterView, 1.0f);
                        }
                    } else {
                        if (mSkipView != null) {
                            mSkipView.setVisibility(View.VISIBLE);
                            ViewCompat.setAlpha(mSkipView, 1.0f);
                        }
                        if (mEnterView != null) {
                            mEnterView.setVisibility(View.GONE);
                        }
                    }
                }
            });
        }

        if (mAutoPlayAble) {
            mViewPager.setAutoPlayDelegate(this);

            int zeroItem = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % mViews.size();
            mViewPager.setCurrentItem(zeroItem);

            startAutoPlay();
        } else {
            switchToPoint(0);
        }
    }

    public void removePlaceholder() {
        if (mPlaceholderIv != null && this.equals(mPlaceholderIv.getParent())) {
            removeView(mPlaceholderIv);
            mPlaceholderIv = null;
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

    /**
     * 设置当只有一页数据时是否显示指示器
     *
     * @param isNeedShowIndicatorOnOnlyOnePage
     */
    public void setIsNeedShowIndicatorOnOnlyOnePage(boolean isNeedShowIndicatorOnOnlyOnePage) {
        mIsNeedShowIndicatorOnOnlyOnePage = isNeedShowIndicatorOnOnlyOnePage;
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
        } else if (visibility == INVISIBLE || visibility == GONE) {
            onInvisibleToUser();
        }
    }

    private void onInvisibleToUser() {
        stopAutoPlay();

        // 处理 RecyclerView 中从对用户不可见变为可见时卡顿的问题
        if (!mIsFirstInvisible && mAutoPlayAble && mViewPager != null && getItemCount() > 0) {
            switchToNextPage();
        }
        mIsFirstInvisible = false;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onInvisibleToUser();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAutoPlay();
    }

    public void startAutoPlay() {
        stopAutoPlay();
        if (mAutoPlayAble) {
            postDelayed(mAutoPlayTask, mAutoPlayInterval);
        }
    }

    public void stopAutoPlay() {
        if (mAutoPlayTask != null) {
            removeCallbacks(mAutoPlayTask);
        }
    }

    private void switchToPoint(int newCurrentPoint) {
        if (mTipTv != null) {
            if (mTips == null || mTips.size() < 1 || newCurrentPoint >= mTips.size()) {
                mTipTv.setVisibility(View.GONE);
            } else {
                mTipTv.setVisibility(View.VISIBLE);
                mTipTv.setText(mTips.get(newCurrentPoint));
            }
        }

        if (mPointRealContainerLl != null) {
            if (mViews != null && mViews.size() > 0 && newCurrentPoint < mViews.size() && ((mIsNeedShowIndicatorOnOnlyOnePage || (!mIsNeedShowIndicatorOnOnlyOnePage && mViews.size() > 1)))) {
                mPointRealContainerLl.setVisibility(View.VISIBLE);
                for (int i = 0; i < mPointRealContainerLl.getChildCount(); i++) {
                    mPointRealContainerLl.getChildAt(i).setEnabled(i == newCurrentPoint);
                    // 处理指示器选中和未选中状态图片尺寸不相等
                    mPointRealContainerLl.getChildAt(i).requestLayout();
                }
            } else {
                mPointRealContainerLl.setVisibility(View.GONE);
            }
        }

        if (mNumberIndicatorTv != null) {
            if (mViews != null && mViews.size() > 0 && newCurrentPoint < mViews.size() && ((mIsNeedShowIndicatorOnOnlyOnePage || (!mIsNeedShowIndicatorOnOnlyOnePage && mViews.size() > 1)))) {
                mNumberIndicatorTv.setVisibility(View.VISIBLE);
                mNumberIndicatorTv.setText((newCurrentPoint + 1) + "/" + mViews.size());
            } else {
                mNumberIndicatorTv.setVisibility(View.GONE);
            }
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
            if (mHackyViews == null) {
                BGABannerUtil.resetPageTransformer(mViews);
            } else {
                BGABannerUtil.resetPageTransformer(mHackyViews);
            }
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
        if (mViewPager != null) {
            if (mPageScrollPosition < mViewPager.getCurrentItem()) {
                // 往右滑
                if (xVelocity > VEL_THRESHOLD || (mPageScrollPositionOffset < 0.7f && xVelocity > -VEL_THRESHOLD)) {
                    mViewPager.setBannerCurrentItemInternal(mPageScrollPosition, true);
                } else {
                    mViewPager.setBannerCurrentItemInternal(mPageScrollPosition + 1, true);
                }
            } else {
                // 往左滑
                if (xVelocity < -VEL_THRESHOLD || (mPageScrollPositionOffset > 0.3f && xVelocity < VEL_THRESHOLD)) {
                    mViewPager.setBannerCurrentItemInternal(mPageScrollPosition + 1, true);
                } else {
                    mViewPager.setBannerCurrentItemInternal(mPageScrollPosition, true);
                }
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        position = position % mViews.size();
        switchToPoint(position);

        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mPageScrollPosition = position;
        mPageScrollPositionOffset = positionOffset;

        if (mTipTv != null) {
            if (mTips != null && mTips.size() > 0) {
                mTipTv.setVisibility(View.VISIBLE);

                int leftPosition = position % mTips.size();
                int rightPosition = (position + 1) % mTips.size();
                if (rightPosition < mTips.size() && leftPosition < mTips.size()) {
                    if (positionOffset > 0.5) {
                        mTipTv.setText(mTips.get(rightPosition));
                        ViewCompat.setAlpha(mTipTv, positionOffset);
                    } else {
                        ViewCompat.setAlpha(mTipTv, 1 - positionOffset);
                        mTipTv.setText(mTips.get(leftPosition));
                    }
                }
            } else {
                mTipTv.setVisibility(View.GONE);
            }
        }

        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrolled(position % mViews.size(), positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    public void setDelegate(Delegate delegate) {
        mDelegate = delegate;
    }

    public void setAdapter(Adapter adapter) {
        mAdapter = adapter;
    }

    private class PageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mViews == null ? 0 : (mAutoPlayAble ? Integer.MAX_VALUE : mViews.size());
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final int finalPosition = position % mViews.size();

            View view;
            if (mHackyViews == null) {
                view = mViews.get(finalPosition);
            } else {
                view = mHackyViews.get(position % mHackyViews.size());
            }

            if (mDelegate != null) {
                view.setOnClickListener(new BGAOnNoDoubleClickListener() {
                    @Override
                    public void onNoDoubleClick(View view) {
                        int currentPosition = mViewPager.getCurrentItem() % mViews.size();
                        mDelegate.onBannerItemClick(BGABanner.this, view, mModels == null ? null : mModels.get(currentPosition), currentPosition);
                    }
                });
            }

            if (mAdapter != null) {
                mAdapter.fillBannerItem(BGABanner.this, view, mModels == null ? null : mModels.get(finalPosition), finalPosition);
            }

            ViewParent viewParent = view.getParent();
            if (viewParent != null) {
                ((ViewGroup) viewParent).removeView(view);
            }

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
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

    /**
     * item 点击事件监听器，在 BGABanner 里已经帮开发者处理了防止重复点击事件
     *
     * @param <V> item 视图类型，如果没有在 setData 方法里指定自定义的 item 布局资源文件的话，这里的 V 就是 ImageView
     * @param <M> item 数据模型
     */
    public interface Delegate<V extends View, M> {
        void onBannerItemClick(BGABanner banner, V itemView, M model, int position);
    }

    /**
     * 适配器，在 fillBannerItem 方法中填充数据，加载网络图片等
     *
     * @param <V> item 视图类型，如果没有在 setData 方法里指定自定义的 item 布局资源文件的话，这里的 V 就是 ImageView
     * @param <M> item 数据模型
     */
    public interface Adapter<V extends View, M> {
        void fillBannerItem(BGABanner banner, V itemView, M model, int position);
    }

    /**
     * 引导页「进入」和「跳过」按钮点击事件监听器，在 BGABanner 里已经帮开发者处理了防止重复点击事件
     */
    public interface GuideDelegate {
        void onClickEnterOrSkip();
    }
}