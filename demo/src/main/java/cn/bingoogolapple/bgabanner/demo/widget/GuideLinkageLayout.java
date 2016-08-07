package cn.bingoogolapple.bgabanner.demo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/8/7 上午11:39
 * 描述:引导界面联动布局，将每一个触摸事件分发给所有的子控件。
 */
public class GuideLinkageLayout extends FrameLayout {
    public GuideLinkageLayout(Context context) {
        super(context);
    }

    public GuideLinkageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GuideLinkageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            try {
                child.dispatchTouchEvent(ev);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
