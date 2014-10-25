package cn.bingoogolapple.bgabanner.demo.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.demo.R;
import cn.bingoogolapple.bgabanner.library.BGABanner;
import cn.bingoogolapple.loon.library.Loon;
import cn.bingoogolapple.loon.library.LoonLayout;
import cn.bingoogolapple.loon.library.LoonView;

@LoonLayout(id = R.layout.activity_main)
public class MainActivity extends FragmentActivity {
    @LoonView(id = R.id.banner1)
    private BGABanner mBanner1;
    @LoonView(id = R.id.banner2)
    private BGABanner mBanner2;
    @LoonView(id = R.id.banner3)
    private BGABanner mBanner3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Loon.injectView2Activity(this);
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
