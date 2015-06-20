package cn.bingoogolapple.bgabanner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.transformer.AccordionPageTransformer;
import cn.bingoogolapple.bgabanner.transformer.AlphaPageTransformer;
import cn.bingoogolapple.bgabanner.transformer.CubePageTransformer;
import cn.bingoogolapple.bgabanner.transformer.DefaultPageTransformer;
import cn.bingoogolapple.bgabanner.transformer.DepthPageTransformer;
import cn.bingoogolapple.bgabanner.transformer.FadePageTransformer;
import cn.bingoogolapple.bgabanner.transformer.FlipPageTransformer;
import cn.bingoogolapple.bgabanner.transformer.RotatePageTransformer;
import cn.bingoogolapple.bgabanner.transformer.StackPageTransformer;
import cn.bingoogolapple.bgabanner.transformer.ZoomCenterPageTransformer;
import cn.bingoogolapple.bgabanner.transformer.ZoomFadePageTransformer;
import cn.bingoogolapple.bgabanner.transformer.ZoomPageTransformer;
import cn.bingoogolapple.bgabanner.transformer.ZoomStackPageTransformer;

public class BGABanner extends RelativeLayout {
    private static final int RMP = RelativeLayout.LayoutParams.MATCH_PARENT;
    private static final int RWC = RelativeLayout.LayoutParams.WRAP_CONTENT;
    private static final int LWC = LinearLayout.LayoutParams.WRAP_CONTENT;
    private BGAViewPager mViewPager;
    private List<View> mViews;
    private LinearLayout mPointContainer;
    private List<ImageView> mPoints;
    private boolean mPointVisibility = true;
    private boolean mAutoPlayAble = true;
    private boolean mIsAutoPlaying = false;
    private int mAutoPlayInterval = 2000;
    private int mPageChangeDuration = 2000;
    private int mPointGravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    private int mPointSpacing;
    private int mPointContainerLeftRightPadding;
    private int mPointContainerTopBottomPadding;
    private int mCurrentPoint = 0;
    private Drawable mPointFocusedDrawable;
    private Drawable mPointUnfocusedDrawable;
    private Drawable mPointContainerBackgroundDrawable;
    private Handler mPagerHandler;

    private Runnable mAutoPlayTask = new Runnable() {
        @Override
        public void run() {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            mPagerHandler.postDelayed(mAutoPlayTask, mAutoPlayInterval);
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
        mViewPager = new BGAViewPager(context);
        mPagerHandler = new Handler();
        mPointSpacing = dp2px(context, 5);
        mPointContainerLeftRightPadding = dp2px(context, 15);
        mPointContainerTopBottomPadding = dp2px(context, 5);
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
        if (attr == R.styleable.BGABanner_banner_pointFocusedImg) {
            mPointFocusedDrawable = typedArray.getDrawable(attr);
        } else if (attr == R.styleable.BGABanner_banner_pointUnfocusedImg) {
            mPointUnfocusedDrawable = typedArray.getDrawable(attr);
        } else if (attr == R.styleable.BGABanner_banner_pointContainerBackground) {
            mPointContainerBackgroundDrawable = typedArray.getDrawable(attr);
        } else if (attr == R.styleable.BGABanner_banner_pointSpacing) {
            /**
             * getDimension和getDimensionPixelOffset的功能差不多,都是获取某个dimen的值,如果是dp或sp的单位,将其乘以density,如果是px,则不乘;两个函数的区别是一个返回float,一个返回int. getDimensionPixelSize则不管写的是dp还是sp还是px,都会乘以denstiy.
             */
            mPointSpacing = typedArray.getDimensionPixelOffset(attr, mPointSpacing);
        } else if (attr == R.styleable.BGABanner_banner_pointContainerLeftRightPadding) {
            mPointContainerLeftRightPadding = typedArray.getDimensionPixelOffset(attr, mPointContainerLeftRightPadding);
        } else if (attr == R.styleable.BGABanner_banner_pointContainerTopBottomPadding) {
            mPointContainerTopBottomPadding = typedArray.getDimensionPixelOffset(attr, mPointContainerTopBottomPadding);
        } else if (attr == R.styleable.BGABanner_banner_pointGravity) {
            mPointGravity = typedArray.getInt(attr, mPointGravity);
        } else if (attr == R.styleable.BGABanner_banner_pointVisibility) {
            mPointVisibility = typedArray.getBoolean(attr, mPointVisibility);
        } else if (attr == R.styleable.BGABanner_banner_pointAutoPlayAble) {
            mAutoPlayAble = typedArray.getBoolean(attr, mAutoPlayAble);
        } else if (attr == R.styleable.BGABanner_banner_pointAutoPlayInterval) {
            mAutoPlayInterval = typedArray.getInteger(attr, mAutoPlayInterval);
        } else if (attr == R.styleable.BGABanner_banner_pageChangeDuration) {
            mPageChangeDuration = typedArray.getInteger(attr, mPageChangeDuration);
        } else if (attr == R.styleable.BGABanner_banner_transitionEffect) {
            int ordinal = typedArray.getInt(attr, TransitionEffect.Accordion.ordinal());
            setTransitionEffect(TransitionEffect.values()[ordinal]);
        }
    }

    private void initView(Context context) {
        addView(mViewPager, new RelativeLayout.LayoutParams(RMP, RMP));
        setPageChangeDuration(mPageChangeDuration);

        if (mPointVisibility) {
            if (mPointFocusedDrawable == null) {
                throw new RuntimeException("设置显示指示点时pointFocusedImg不能为空");
            } else if (mPointUnfocusedDrawable == null) {
                throw new RuntimeException("设置显示指示点时pointUnfocusedImg不能为空");
            }

            mPointContainer = new LinearLayout(context);
            mPointContainer.setOrientation(LinearLayout.HORIZONTAL);
            mPointContainer.setPadding(mPointContainerLeftRightPadding, mPointContainerTopBottomPadding, mPointContainerLeftRightPadding, mPointContainerTopBottomPadding);
            if (Build.VERSION.SDK_INT >= 16) {
                mPointContainer.setBackground(mPointContainerBackgroundDrawable);
            } else {
                mPointContainer.setBackgroundDrawable(mPointContainerBackgroundDrawable);
            }
            RelativeLayout.LayoutParams pointContainerLp = new RelativeLayout.LayoutParams(RMP, RWC);
            // 处理圆点在顶部还是底部
            if ((mPointGravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.TOP) {
                pointContainerLp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            } else {
                pointContainerLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            }
            int horizontalGravity = mPointGravity & Gravity.HORIZONTAL_GRAVITY_MASK;
            // 处理圆点在左边、右边还是水平居中
            if (horizontalGravity == Gravity.LEFT) {
                mPointContainer.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            } else if (horizontalGravity == Gravity.RIGHT) {
                mPointContainer.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            } else {
                mPointContainer.setGravity(Gravity.CENTER);
            }

            addView(mPointContainer, pointContainerLp);
        }
    }

    /**
     * 设置页码切换过程的时间长度
     *
     * @param duration 页码切换过程的时间长度
     */
    public void setPageChangeDuration(int duration) {
        if (duration > 0 && duration < 5000) {
            mViewPager.setPageChangeDuration(duration);
        }
    }

    /**
     * 设置每一页的控件
     *
     * @param views 每一页的控件
     */
    public void setViewPagerViews(List<View> views) {
        if (mAutoPlayAble && views.size() < 3) {
            throw new RuntimeException("开启指定轮播时至少有三个页面");
        }
        mViews = views;
        mViewPager.setAdapter(new PageAdapter());
        mViewPager.addOnPageChangeListener(new ChangePointListener());

        initPoints();
        processAutoPlay();
    }

    private void initPoints() {
        if (!mPointVisibility) {
            return;
        }

        mPointContainer.removeAllViews();
        mViewPager.removeAllViews();
        if (mPoints != null) {
            mPoints.clear();
        } else {
            mPoints = new ArrayList<>();
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LWC, LWC);
        int margin = mPointSpacing / 2;
        lp.setMargins(margin, 0, margin, 0);
        ImageView imageView;
        for (int i = 0; i < mViews.size(); i++) {
            imageView = new ImageView(getContext());
            imageView.setLayoutParams(lp);
            imageView.setImageDrawable(mPointUnfocusedDrawable);
            mPoints.add(imageView);
            mPointContainer.addView(imageView);
        }
    }

    private void processAutoPlay() {
        if (mAutoPlayAble) {
            mViewPager.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            stopAutoPlay();
                            break;
                        case MotionEvent.ACTION_UP:
                            startAutoPlay();
                            break;
                    }
                    return false;
                }
            });
            mViewPager.setCurrentItem(Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % mViews.size());
        } else {
            switchToPoint(mCurrentPoint);
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
        if (mPagerHandler != null) {
            mPagerHandler.removeCallbacks(mAutoPlayTask);
        }
    }

    private void startAutoPlay() {
        if (mAutoPlayAble && !mIsAutoPlaying) {
            mIsAutoPlaying = true;
            mPagerHandler.postDelayed(mAutoPlayTask, mAutoPlayInterval);
        }
    }

    private void stopAutoPlay() {
        if (mAutoPlayAble && mIsAutoPlaying) {
            mIsAutoPlaying = false;
            mPagerHandler.removeCallbacks(mAutoPlayTask);
        }
    }

    private void switchToPoint(int newCurrentPoint) {
        if (!mPointVisibility) {
            return;
        }

        mPoints.get(mCurrentPoint).setImageDrawable(mPointUnfocusedDrawable);
        mPoints.get(newCurrentPoint).setImageDrawable(mPointFocusedDrawable);
        mCurrentPoint = newCurrentPoint;
    }

    /**
     * 设置页面也换动画
     *
     * @param effect
     */
    public void setTransitionEffect(TransitionEffect effect) {
        switch (effect) {
            case Default:
                mViewPager.setPageTransformer(true, new DefaultPageTransformer());
                break;
            case Alpha:
                mViewPager.setPageTransformer(true, new AlphaPageTransformer());
                break;
            case Rotate:
                mViewPager.setPageTransformer(true, new RotatePageTransformer());
                break;
            case Cube:
                mViewPager.setPageTransformer(true, new CubePageTransformer());
                break;
            case Flip:
                mViewPager.setPageTransformer(true, new FlipPageTransformer());
                break;
            case Accordion:
                mViewPager.setPageTransformer(true, new AccordionPageTransformer());
                break;
            case ZoomFade:
                mViewPager.setPageTransformer(true, new ZoomFadePageTransformer());
                break;
            case Fade:
                mViewPager.setPageTransformer(true, new FadePageTransformer());
                break;
            case ZoomCenter:
                mViewPager.setPageTransformer(true, new ZoomCenterPageTransformer());
                break;
            case ZoomStack:
                mViewPager.setPageTransformer(true, new ZoomStackPageTransformer());
                break;
            case Stack:
                mViewPager.setPageTransformer(true, new StackPageTransformer());
                break;
            case Depth:
                mViewPager.setPageTransformer(true, new DepthPageTransformer());
                break;
            case Zoom:
                mViewPager.setPageTransformer(true, new ZoomPageTransformer());
                break;
            default:
                break;
        }
    }

    /**
     * 设置自定义页面切换动画
     *
     * @param transformer
     */
    public void setPageTransformer(ViewPager.PageTransformer transformer) {
        if (transformer != null) {
            mViewPager.setPageTransformer(true, transformer);
        }
    }

    private final class PageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            // 获取ViewPager的个数，这个方法是必须实现的
            return mAutoPlayAble ? Integer.MAX_VALUE : mViews.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view;
            if (mAutoPlayAble) {
                view = mViews.get(position % mViews.size());
            } else {
                view = mViews.get(position);
            }

            // 在destroyItem方法中销毁的话，当只有3页时会有问题
            if (container.equals(view.getParent())) {
                container.removeView(view);
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
    }

    private final class ChangePointListener extends BGAViewPager.SimpleOnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            if (mPointVisibility) {
                if (mAutoPlayAble) {
                    switchToPoint(position % mViews.size());
                } else {
                    switchToPoint(position);
                }
            }
        }
    }

    public enum TransitionEffect {
        Default,
        Alpha,
        Rotate,
        Cube,
        Flip,
        Accordion,
        ZoomFade,
        Fade,
        ZoomCenter,
        ZoomStack,
        Stack,
        Depth,
        Zoom
    }

    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

}