package cn.bingoogolapple.bgabanner.transformer;

import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/6/19 上午8:41
 * 描述:
 */
public class ZoomFadePageTransformer extends BGAPageTransformer {

    @Override
    public void handleInvisiblePage(View view, float position) {
    }

    @Override
    public void handleLeftPage(View view, float position) {
        ViewHelper.setTranslationX(view, -view.getWidth() * position);

        ViewHelper.setPivotX(view,view.getWidth() * 0.5f);
        ViewHelper.setPivotY(view, view.getHeight() * 0.5f);
        ViewHelper.setScaleX(view, 1 + position);
        ViewHelper.setScaleY(view, 1 + position);

        ViewHelper.setAlpha(view, 1 + position);
    }

    @Override
    public void handleRightPage(View view, float position) {
        ViewHelper.setTranslationX(view, -view.getWidth() * position);

        ViewHelper.setPivotX(view,view.getWidth() * 0.5f);
        ViewHelper.setPivotY(view, view.getHeight() * 0.5f);
        ViewHelper.setScaleX(view, 1 - position);
        ViewHelper.setScaleY(view, 1 - position);
        ViewHelper.setAlpha(view, 1 - position);
    }

}