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
    @BGAAView(R.id.banner1)
    private BGABanner mBanner1;
    @BGAAView(R.id.banner2)
    private BGABanner mBanner2;
    @BGAAView(R.id.banner3)
    private BGABanner mBanner3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BGAA.injectView2Activity(this);
        mBanner1.setViewPagerViews(getDatas());
        mBanner2.setViewPagerViews(getDatas());
        mBanner3.setViewPagerViews(getDatas());
    }

    private List<View> getDatas() {
        List<View> datas = new ArrayList<View>();
        datas.add(getLayoutInflater().inflate(R.layout.view_one, null));
        datas.add(getLayoutInflater().inflate(R.layout.view_two, null));
        datas.add(getLayoutInflater().inflate(R.layout.view_three, null));
        datas.add(getLayoutInflater().inflate(R.layout.view_four, null));
        return datas;
    }
}
