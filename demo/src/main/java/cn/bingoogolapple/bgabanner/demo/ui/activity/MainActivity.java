package cn.bingoogolapple.bgabanner.demo.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.demo.R;

public class MainActivity extends FragmentActivity {
    private BGABanner mDefaultBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDefaultBanner = (BGABanner) findViewById(R.id.banner_main_default);
        mDefaultBanner.setViewsAndTips(getFiveViews(), getFiveTips());

        ((BGABanner) findViewById(R.id.banner_main_cube)).setViewsAndTips(getFourViews(), getFourTips());
        ((BGABanner) findViewById(R.id.banner_main_accordion)).setViews(getThreeViews());
        ((BGABanner) findViewById(R.id.banner_main_flip)).setViewsAndTips(getFourViews(), getFourTips());
        ((BGABanner) findViewById(R.id.banner_main_rotate)).setViews(getThreeViews());
        ((BGABanner) findViewById(R.id.banner_main_alpha)).setViewsAndTips(getFourViews(), getFourTips());
        ((BGABanner) findViewById(R.id.banner_main_zoomFade)).setViews(getThreeViews());
        ((BGABanner) findViewById(R.id.banner_main_fade)).setViewsAndTips(getFourViews(), getFourTips());
        ((BGABanner) findViewById(R.id.banner_main_zoomCenter)).setViews(getThreeViews());
        ((BGABanner) findViewById(R.id.banner_main_zoom)).setViews(getFiveViews());
        ((BGABanner) findViewById(R.id.banner_main_stack)).setViewsAndTips(getFourViews(), getFourTips());
        ((BGABanner) findViewById(R.id.banner_main_zoomStack)).setViews(getThreeViews());
        ((BGABanner) findViewById(R.id.banner_main_depth)).setViews(getFourViews());
    }

    private List<View> getThreeViews() {
        List<View> datas = new ArrayList<>();
        datas.add(getLayoutInflater().inflate(R.layout.view_one, null));
        datas.add(getLayoutInflater().inflate(R.layout.view_two, null));
        datas.add(getLayoutInflater().inflate(R.layout.view_three, null));
        return datas;
    }

    private List<View> getFourViews() {
        List<View> datas = new ArrayList<>();
        datas.add(getLayoutInflater().inflate(R.layout.view_one, null));
        datas.add(getLayoutInflater().inflate(R.layout.view_two, null));
        datas.add(getLayoutInflater().inflate(R.layout.view_three, null));
        datas.add(getLayoutInflater().inflate(R.layout.view_four, null));
        return datas;
    }

    private List<View> getFiveViews() {
        List<View> datas = new ArrayList<>();
        datas.add(getLayoutInflater().inflate(R.layout.view_one, null));
        datas.add(getLayoutInflater().inflate(R.layout.view_two, null));
        datas.add(getLayoutInflater().inflate(R.layout.view_three, null));
        datas.add(getLayoutInflater().inflate(R.layout.view_four, null));
        datas.add(getLayoutInflater().inflate(R.layout.view_five, null));
        return datas;
    }

    private List<String> getFourTips() {
        List<String> tips = new ArrayList<>();
        tips.add("第一页提示文案");
        tips.add("第二页提示文案");
        tips.add("第三页提示文案");
        tips.add("第四页提示文案");
        return tips;
    }

    private List<String> getFiveTips() {
        List<String> tips = new ArrayList<>();
        tips.add("第一页提示文案");
        tips.add("第二页提示文案");
        tips.add("第三页提示文案");
        tips.add("第四页提示文案");
        tips.add("第五页提示文案");
        return tips;
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
