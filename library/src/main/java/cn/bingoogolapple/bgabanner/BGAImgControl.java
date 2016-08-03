package cn.bingoogolapple.bgabanner;

import android.content.Context;

import com.facebook.common.internal.Supplier;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

/**
 * Created by yan on 2016/8/2.
 */

public class BGAImgControl {
    public void init(Context context) {
        Fresco.initialize(context);
    }

    public static BGAImgControl bgaImgControl;

    public static BGAImgControl getInstance() {
        if (bgaImgControl == null) {
            synchronized (BGAImgControl.class) {
                if (bgaImgControl == null) {
                    bgaImgControl = new BGAImgControl();
                }
            }
        }
        return bgaImgControl;
    }

    public void init(Context context, int cacheSize) {
        maxSize = cacheSize;
        Fresco.initialize(context, getConfigureCaches(context));
    }

    public static class Builder extends GenericDraweeHierarchyBuilder {
        public Builder(Context context) {
            super(context.getResources());
        }
    }

   private int maxSize = 10 * 1024 * 1024;

    /**
     * 效果不是很理想，用Fresco默认配置便可
     *
     * @param context
     * @return
     */
    public ImagePipelineConfig getConfigureCaches(Context context) {
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                maxSize,// 内存缓存中总图片的最大大小,以字节为单位。
                Integer.MAX_VALUE,// 内存缓存中图片的最大数量。
                maxSize,// 内存缓存中准备清除但尚未被删除的总图片的最大大小,以字节为单位。
                Integer.MAX_VALUE,// 内存缓存中准备清除的总图片的最大数量。
                Integer.MAX_VALUE);// 内存缓存中单个图片的最大大小。

        Supplier<MemoryCacheParams> mSupplierMemoryCacheParams = new Supplier<MemoryCacheParams>() {
            @Override
            public MemoryCacheParams get() {
                return bitmapCacheParams;
            }
        };
        ImagePipelineConfig.Builder builder = ImagePipelineConfig.newBuilder(context);
        builder.setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams);
        return builder.build();
    }

}
