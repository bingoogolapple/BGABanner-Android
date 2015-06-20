package cn.bingoogolapple.bgabanner.demo.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.demo.R;
import cn.bingoogolapple.bgabanner.BGABanner;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((BGABanner) findViewById(R.id.banner_main_default)).setViewPagerViews(getFourDatas());
        ((BGABanner) findViewById(R.id.banner_main_cube)).setViewPagerViews(getFourDatas());
        ((BGABanner) findViewById(R.id.banner_main_accordion)).setViewPagerViews(getThreeDatas());
        ((BGABanner) findViewById(R.id.banner_main_flip)).setViewPagerViews(getFourDatas());
        ((BGABanner) findViewById(R.id.banner_main_rotate)).setViewPagerViews(getThreeDatas());
        ((BGABanner) findViewById(R.id.banner_main_alpha)).setViewPagerViews(getFourDatas());
        ((BGABanner) findViewById(R.id.banner_main_zoomFade)).setViewPagerViews(getThreeDatas());
        ((BGABanner) findViewById(R.id.banner_main_fade)).setViewPagerViews(getFourDatas());
        ((BGABanner) findViewById(R.id.banner_main_zoomCenter)).setViewPagerViews(getThreeDatas());
        ((BGABanner) findViewById(R.id.banner_main_zoom)).setViewPagerViews(getThreeDatas());
        ((BGABanner) findViewById(R.id.banner_main_stack)).setViewPagerViews(getFourDatas());
        ((BGABanner) findViewById(R.id.banner_main_zoomStack)).setViewPagerViews(getThreeDatas());
        ((BGABanner) findViewById(R.id.banner_main_depth)).setViewPagerViews(getFourDatas());
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
