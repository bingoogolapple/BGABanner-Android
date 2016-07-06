package cn.bingoogolapple.bgabanner.demo.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGABannerUtil;
import cn.bingoogolapple.bgabanner.demo.App;
import cn.bingoogolapple.bgabanner.demo.R;
import cn.bingoogolapple.bgabanner.demo.engine.Engine;
import cn.bingoogolapple.bgabanner.demo.model.BannerModel;
import cn.bingoogolapple.bgabanner.transformer.TransitionEffect;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {
    private BGABanner mDefaultBanner;
    private BGABanner mCubeBanner;
    private BGABanner mAccordionBanner;
    private BGABanner mFlipBanner;
    private BGABanner mRotateBanner;
    private BGABanner mAlphaBanner;
    private BGABanner mZoomFadeBanner;
    private BGABanner mFadeBanner;
    private BGABanner mZoomCenterBanner;
    private BGABanner mZoomBanner;
    private BGABanner mStackBanner;
    private BGABanner mZoomStackBanner;
    private BGABanner mDepthBanner;

    private Engine mEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEngine = App.getInstance().getEngine();

        initView();
        setListener();
        loadData();
    }

    private void initView() {
        mDefaultBanner = (BGABanner) findViewById(R.id.banner_main_default);
        mCubeBanner = (BGABanner) findViewById(R.id.banner_main_cube);
        mAccordionBanner = (BGABanner) findViewById(R.id.banner_main_accordion);
        mFlipBanner = (BGABanner) findViewById(R.id.banner_main_flip);
        mRotateBanner = (BGABanner) findViewById(R.id.banner_main_rotate);
        mAlphaBanner = (BGABanner) findViewById(R.id.banner_main_alpha);
        mZoomFadeBanner = (BGABanner) findViewById(R.id.banner_main_zoomFade);
        mFadeBanner = (BGABanner) findViewById(R.id.banner_main_fade);
        mZoomCenterBanner = (BGABanner) findViewById(R.id.banner_main_zoomCenter);
        mZoomBanner = (BGABanner) findViewById(R.id.banner_main_zoom);
        mStackBanner = (BGABanner) findViewById(R.id.banner_main_stack);
        mZoomStackBanner = (BGABanner) findViewById(R.id.banner_main_zoomStack);
        mDepthBanner = (BGABanner) findViewById(R.id.banner_main_depth);
    }

    private void setListener() {
        mDefaultBanner.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onClickBannerItem(int position) {
                Toast.makeText(App.getInstance(), "点击了第" + (position + 1) + "页", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData() {
        loadData(mCubeBanner, 2);
        loadData(mAccordionBanner, 3);
        loadData(mFlipBanner, 4);
        loadData(mRotateBanner, 5);
        loadData(mAlphaBanner, 6);
        loadData(mZoomFadeBanner, 4);
        loadData(mFadeBanner, 4);
        loadData(mZoomCenterBanner, 4);
        loadData(mZoomBanner, 4);
        loadData(mStackBanner, 4);
        loadData(mZoomStackBanner, 4);
        loadData(mDepthBanner, 4);
    }

    private void loadData(final BGABanner banner, int count) {
        mEngine.fetchItemsWithItemCount(count).enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                banner.setViewsAndTips(getViews(bannerModel.imgs.size()), bannerModel.tips);
//                banner.setViews(getViews(bannerModel.imgs.size()));
                for (int i = 0; i < banner.getItemCount(); i++) {
                    Glide.with(MainActivity.this).load(bannerModel.imgs.get(i)).placeholder(R.drawable.holder).error(R.drawable.holder).into(banner.getItemImageView(i));
                }
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
                Toast.makeText(App.getInstance(), "网络数据加载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<ImageView> getViews(int count) {
        List<ImageView> views = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            views.add(BGABannerUtil.getItemImageView(this, R.drawable.holder));
        }
        return views;
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
            case R.id.btn_main_get_item_count:
                Toast.makeText(App.getInstance(), "广告条总页数为 " + mDefaultBanner.getItemCount(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_main_get_current_item:
                Toast.makeText(App.getInstance(), "广告当前索引位置为 " + mDefaultBanner.getCurrentItem(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_main_load_three_item:
                loadData(mDefaultBanner, 3);
                break;
            case R.id.btn_main_load_five_item:
                loadData(mDefaultBanner, 5);
                break;
            case R.id.btn_main_cube:
                mDefaultBanner.setTransitionEffect(TransitionEffect.Cube);
                break;
            case R.id.btn_main_depth:
                mDefaultBanner.setTransitionEffect(TransitionEffect.Depth);
                break;
            case R.id.btn_main_flip:
                mDefaultBanner.setTransitionEffect(TransitionEffect.Flip);
                break;
            case R.id.btn_main_rotate:
                mDefaultBanner.setTransitionEffect(TransitionEffect.Rotate);
                break;
            case R.id.btn_main_alpha:
                mDefaultBanner.setTransitionEffect(TransitionEffect.Alpha);
                break;
            default:
                break;
        }
    }
}
