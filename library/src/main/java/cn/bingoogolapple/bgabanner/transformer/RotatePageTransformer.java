package cn.bingoogolapple.bgabanner.transformer;

import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/6/19 上午8:41
 * 描述:
 */
public class RotatePageTransformer extends BGAPageTransformer {
    private float mMaxRotation = 15.0f;

    public RotatePageTransformer() {
    }

    public RotatePageTransformer(float maxRotation) {
        setMaxRotation(maxRotation);
    }

    @Override
    public void handleInvisiblePage(View view, float position) {
        ViewCompat.setPivotX(view, view.getMeasuredWidth() * 0.5f);
        ViewCompat.setPivotY(view, view.getMeasuredHeight());
        ViewCompat.setRotation(view, 0);
    }

    @Override
    public void handleLeftPage(View view, float position) {
        float rotation = (mMaxRotation * position);
        ViewCompat.setPivotX(view, view.getMeasuredWidth() * 0.5f);
        ViewCompat.setPivotY(view, view.getMeasuredHeight());
        ViewCompat.setRotation(view, rotation);
    }

    @Override
    public void handleRightPage(View view, float position) {
        handleLeftPage(view, position);
    }

    public void setMaxRotation(float maxRotation) {
        if (maxRotation >= 0.0f && maxRotation <= 40.0f) {
            mMaxRotation = maxRotation;
        }
    }
}