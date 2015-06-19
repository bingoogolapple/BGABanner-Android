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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BGAA.injectView2Activity(this);
        mCubeBanner.setViewPagerViews(getThreeDatas());
        mAccordionBanner.setViewPagerViews(getFourDatas());
        mFlipBanner.setViewPagerViews(getThreeDatas());
        mRotateBanner.setViewPagerViews(getFourDatas());
        mAlphaBanner.setViewPagerViews(getThreeDatas());
    }

    private List<View> getThreeDatas() {
        List<View> datas = new ArrayList<>();
        datas.add(getLayoutInflater().inflate(R.layout.view_one, null));
        datas.add(getLayoutInflater().inflate(R.layout.view_two, null));
        datas.add(getLayoutInflater().inflate(R.layout.view_three, null));
        return datas;
    }

    private List<View> getFourDatas() {
        List<View> datas = new ArrayList<>();
        datas.add(getLayoutInflater().inflate(R.layout.view_one, null));
        datas.add(getLayoutInflater().inflate(R.layout.view_two, null));
        datas.add(getLayoutInflater().inflate(R.layout.view_three, null));
        datas.add(getLayoutInflater().inflate(R.layout.view_four, null));
        return datas;
    }
}
