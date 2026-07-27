## ----------------------------------
##      Okio 相关
## ----------------------------------
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**

## ----------------------------------
##      Retrofit 2.X 相关
## ----------------------------------
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

## ----------------------------------
##      RxJava 1.X 相关
## ----------------------------------
-dontwarn sun.misc.**

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

## ----------------------------------
##      Glide 相关
## ----------------------------------
-keep class com.bumptech.glide.Glide { *; }
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-dontwarn com.bumptech.glide.**

## ----------------------------------
##      Fresco 相关
## ----------------------------------
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip

# Do not strip any method/class that is annotated with @DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}

# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**

## ----------------------------------
##      DataBinding 相关
## ----------------------------------
-keepclasseswithmembers class * extends android.databinding.ViewDataBinding{
    <methods>;
}

# BGABaseAdapter-Android:2.0.1 发布版 AAR 漏打包了 Data Binding 生成的
# cn.bingoogolapple.baseadapter.BR 类（其 DummyBinding 的 setVariable 引用了它）。
# 本 demo 并未使用 baseadapter 的 Data Binding 适配器，运行时不会触发该引用，
# 故仅抑制 R8 的 “Missing class” 报错即可。若后续用到 baseadapter 的
# BGABindingRecyclerViewAdapter 等 Data Binding 适配器，需升级到已修复的 baseadapter 版本。
-dontwarn cn.bingoogolapple.baseadapter.BR

## ----------------------------------
##      配置不混淆 Demo 里的 Model
## ----------------------------------
-keep class cn.bingoogolapple.bgabanner.demo.model.**{*;}