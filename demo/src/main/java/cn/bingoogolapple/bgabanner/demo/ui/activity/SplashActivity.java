package cn.bingoogolapple.bgabanner.demo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGABannerUtil;
import cn.bingoogolapple.bgabanner.demo.R;
import cn.bingoogolapple.bgabanner.transformer.TransitionEffect;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        BGABanner banner = (BGABanner) findViewById(R.id.banner_splash_pager);
        // 用Java代码方式设置切换动画
        banner.setTransitionEffect(TransitionEffect.Rotate);
        // banner.setPageTransformer(new RotatePageTransformer());
        // 设置page切换时长
        banner.setPageChangeDuration(1000);

        List<View> views = new ArrayList<>();
        views.add(BGABannerUtil.getItemImageView(this, R.drawable.guide_1));
        views.add(BGABannerUtil.getItemImageView(this, R.drawable.guide_2));
        views.add(BGABannerUtil.getItemImageView(this, R.drawable.guide_3));

        View lastView = getLayoutInflater().inflate(R.layout.item_splash_last, null);
        views.add(lastView);
        lastView.findViewById(R.id.btn_item_splash_last_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        });
        banner.setViews(views);
        // banner.setCurrentItem(1);

//        banner.setAllowUserScrollable(false);
    }

}