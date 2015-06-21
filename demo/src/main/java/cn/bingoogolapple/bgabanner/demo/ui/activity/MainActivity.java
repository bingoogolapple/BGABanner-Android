package cn.bingoogolapple.bgabanner.demo.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.demo.R;
import cn.bingoogolapple.bgabanner.BGABanner;

public class MainActivity extends FragmentActivity {
    private BGABanner mDefaultBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDefaultBanner = (BGABanner) findViewById(R.id.banner_main_default);
        mDefaultBanner.setViewPagerViews(getFiveDatas());

        ((BGABanner) findViewById(R.id.banner_main_cube)).setViewPagerViews(getFourDatas());
        ((BGABanner) findViewById(R.id.banner_main_accordion)).setViewPagerViews(getThreeDatas());
        ((BGABanner) findViewById(R.id.banner_main_flip)).setViewPagerViews(getFourDatas());
        ((BGABanner) findViewById(R.id.banner_main_rotate)).setViewPagerViews(getThreeDatas());
        ((BGABanner) findViewById(R.id.banner_main_alpha)).setViewPagerViews(getFourDatas());
        ((BGABanner) findViewById(R.id.banner_main_zoomFade)).setViewPagerViews(getThreeDatas());
        ((BGABanner) findViewById(R.id.banner_main_fade)).setViewPagerViews(getFourDatas());
        ((BGABanner) findViewById(R.id.banner_main_zoomCenter)).setViewPagerViews(getThreeDatas());
        ((BGABanner) findViewById(R.id.banner_main_zoom)).setViewPagerViews(getFiveDatas());
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

    private List<View> getFiveDatas() {
        List<View> datas = new ArrayList<>();
        datas.add(getLayoutInflater().inflate(R.layout.view_one, null));
        datas.add(getLayoutInflater().inflate(R.layout.view_two, null));
        datas.add(getLayoutInflater().inflate(R.layout.view_three, null));
        datas.add(getLayoutInflater().inflate(R.layout.view_four, null));
        datas.add(getLayoutInflater().inflate(R.layout.view_five, null));
        return datas;
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main_1:
                mDefaultBanner.setCurrentItem(0);
                break;
            case R.id.btn_main_2:
                mDefaultBanner.setCurrentItem(1);
                break;
            case R.id.btn_main_3:
                mDefaultBanner.setCurrentItem(2);
                break;
            case R.id.btn_main_4:
                mDefaultBanner.setCurrentItem(3);
                break;
            case R.id.btn_main_5:
                mDefaultBanner.setCurrentItem(4);
                break;
            default:
                break;
        }
    }
}
