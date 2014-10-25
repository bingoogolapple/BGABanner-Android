package cn.bingoogolapple.bgabanner.demo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.demo.R;
import cn.bingoogolapple.bgabanner.library.BGABanner;
import cn.bingoogolapple.loon.library.Loon;
import cn.bingoogolapple.loon.library.LoonLayout;
import cn.bingoogolapple.loon.library.LoonView;

@LoonLayout(id = R.layout.activity_splash)
public class SplashActivity extends FragmentActivity {

    @LoonView(id = R.id.banner_splash_pager)
    private BGABanner mPagerBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Loon.injectView2Activity(this);

        List<View> views = new ArrayList<View>();
        views.add(getLayoutInflater().inflate(R.layout.view_one, null));
        views.add(getLayoutInflater().inflate(R.layout.view_two, null));
        views.add(getLayoutInflater().inflate(R.layout.view_three, null));
        View lastView = getLayoutInflater().inflate(R.layout.view_last,null);
        views.add(lastView);
        mPagerBanner.setViewPagerViews(views);

        lastView.findViewById(R.id.btn_last_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
