package cn.bingoogolapple.bgabanner.demo.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgaannotation.BGAA;
import cn.bingoogolapple.bgaannotation.BGAALayout;
import cn.bingoogolapple.bgaannotation.BGAAView;
import cn.bingoogolapple.bgabanner.demo.R;
import cn.bingoogolapple.bgabanner.BGABanner;

@BGAALayout(R.layout.activity_main)
public class MainActivity extends FragmentActivity {
    @BGAAView(R.id.banner_main_cube)
    private BGABanner mCubeBanner;
    @BGAAView(R.id.banner_main_accordion)
    private BGABanner mAccordionBanner;
    @BGAAView(R.id.banner_main_flip)
    private BGABanner mFlipBanner;
    @BGAAView(R.id.banner_main_rotate)
    private BGABanner mRotateBanner;
    @BGAAView(R.id.banner_main_alpha)
    private BGABanner mAlphaBanner;
    @BGAAView(R.id.banner_main_depth)
    private BGABanner mDepthBanner;
    @BGAAView(R.id.banner_main_zoom)
    private BGABanner mZoomBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BGAA.injectView2Activity(this);
        mCubeBanner.setViewPagerViews(getDatas());
        mAccordionBanner.setViewPagerViews(getDatas());
        mFlipBanner.setViewPagerViews(getDatas());
        mRotateBanner.setViewPagerViews(getDatas());
        mAlphaBanner.setViewPagerViews(getDatas());
        mDepthBanner.setViewPagerViews(getDatas());
        mZoomBanner.setViewPagerViews(getDatas());
    }

    private List<View> getDatas() {
        List<View> datas = new ArrayList<>();
        datas.add(getLayoutInflater().inflate(R.layout.view_one, null));
        datas.add(getLayoutInflater().inflate(R.layout.view_two, null));
        datas.add(getLayoutInflater().inflate(R.layout.view_three, null));
        datas.add(getLayoutInflater().inflate(R.layout.view_four, null));
        return datas;
    }
}
