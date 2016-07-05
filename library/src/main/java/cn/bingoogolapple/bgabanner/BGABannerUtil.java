package cn.bingoogolapple.bgabanner;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/7/5 上午11:34
 * 描述:
 */
public class BGABannerUtil {
    private static final String TAG = BGABanner.class.getSimpleName();

    private BGABannerUtil() {
    }

    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }

    private static void debug(String msg) {
        Log.i(TAG, msg);
    }

}