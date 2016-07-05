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
import cn.bingoogolapple.bgabanner.demo.App;
import cn.bingoogolapple.bgabanner.demo.R;
import cn.bingoogolapple.bgabanner.demo.engine.Engine;
import cn.bingoogolapple.bgabanner.demo.model.BannerModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {
    private BGABanner mDefaultBanner;
    private List<ImageView> mDefaultViews;

    private BGABanner mCubeBanner;
    private List<ImageView> mCubeViews;

    private BGABanner mAccordionBanner;
    private List<ImageView> mAccordionViews;

    private BGABanner mFlipBanner;
    private List<ImageView> mFlipViews;

    private BGABanner mRotateBanner;
    private List<ImageView> mRotateViews;

    private BGABanner mAlphaBanner;
    private List<ImageView> mAlphaViews;

    private BGABanner mZoomFadeBanner;
    private List<ImageView> mZoomFadeViews;

    private BGABanner mFadeBanner;
    private List<ImageView> mFadeViews;

    private BGABanner mZoomCenterBanner;
    private List<ImageView> mZoomCenterViews;

    private BGABanner mZoomBanner;
    private List<ImageView> mZoomViews;

    private BGABanner mStackBanner;
    private List<ImageView> mStackViews;

    private BGABanner mZoomStackBanner;
    private List<ImageView> mZoomStackViews;

    private BGABanner mDepthBanner;
    private List<ImageView> mDepthViews;

    private Engine mEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEngine = App.getInstance().getEngine();

        initDefault();
        initCube();
        initAccordion();
        initFlip();
        initRotate();
        initAlpha();
        initZoomFade();
        initFade();
        initZoomCenter();
        initZoom();
        initStack();
        initZoomStack();
        initDepth();
    }

    private void initDefault() {
        mDefaultBanner = (BGABanner) findViewById(R.id.banner_main_default);
        mDefaultBanner.setDelegate(new BGABanner.Delegate() {
            @Override
            public void onClickBannerItem(int position) {
                Toast.makeText(App.getInstance(), "点击了第" + (position + 1) + "页", Toast.LENGTH_SHORT).show();
            }
        });
        mDefaultViews = getViews(5);
        mDefaultBanner.setViews(mDefaultViews);

        mEngine.fiveItem().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                ImageView imageView;
                for (int i = 0; i < mDefaultViews.size(); i++) {
                    imageView = mDefaultViews.get(i);
                    Glide.with(MainActivity.this).load(bannerModel.imgs.get(i)).placeholder(R.drawable.holder).error(R.drawable.holder).into(imageView);
                }
                mDefaultBanner.setTips(bannerModel.tips);
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
            }
        });
    }

    private void initCube() {
        mCubeBanner = (BGABanner) findViewById(R.id.banner_main_cube);
        mCubeViews = getViews(6);
        mCubeBanner.setViews(mCubeViews);

        mEngine.sixItem().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                for (int i = 0; i < mCubeViews.size(); i++) {
                    Glide.with(MainActivity.this).load(bannerModel.imgs.get(i)).placeholder(R.drawable.holder).error(R.drawable.holder).into(mCubeViews.get(i));
                }
                // 也可以不设置tips
//                mCubeBanner.setTips(bannerModel.tips);
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
            }
        });
    }

    private void initAccordion() {
        mAccordionBanner = (BGABanner) findViewById(R.id.banner_main_accordion);
        mAccordionViews = getViews(4);
        mAccordionBanner.setViews(mAccordionViews);

        mEngine.fourItem().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                for (int i = 0; i < mAccordionViews.size(); i++) {
                    Glide.with(MainActivity.this).load(bannerModel.imgs.get(i)).placeholder(R.drawable.holder).error(R.drawable.holder).into(mAccordionViews.get(i));
                }
                mAccordionBanner.setTips(bannerModel.tips);
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
            }
        });
    }

    private void initFlip() {
        mFlipBanner = (BGABanner) findViewById(R.id.banner_main_flip);
        mFlipViews = getViews(3);
        mFlipBanner.setViews(mFlipViews);

        mEngine.threeItem().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                for (int i = 0; i < mFlipViews.size(); i++) {
                    Glide.with(MainActivity.this).load(bannerModel.imgs.get(i)).placeholder(R.drawable.holder).error(R.drawable.holder).into(mFlipViews.get(i));
                }
                mFlipBanner.setTips(bannerModel.tips);
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
            }
        });
    }

    private void initRotate() {
        mRotateBanner = (BGABanner) findViewById(R.id.banner_main_rotate);
        mRotateViews = getViews(6);
        mRotateBanner.setViews(mRotateViews);

        mEngine.sixItem().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                for (int i = 0; i < mRotateViews.size(); i++) {
                    Glide.with(MainActivity.this).load(bannerModel.imgs.get(i)).placeholder(R.drawable.holder).error(R.drawable.holder).into(mRotateViews.get(i));
                }
                mRotateBanner.setTips(bannerModel.tips);
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
            }
        });
    }

    private void initAlpha() {
        mAlphaBanner = (BGABanner) findViewById(R.id.banner_main_alpha);
        mAlphaViews = getViews(5);
        mAlphaBanner.setViews(mAlphaViews);

        mEngine.fiveItem().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                for (int i = 0; i < mAlphaViews.size(); i++) {
                    Glide.with(MainActivity.this).load(bannerModel.imgs.get(i)).placeholder(R.drawable.holder).error(R.drawable.holder).into(mAlphaViews.get(i));
                }
                mAlphaBanner.setTips(bannerModel.tips);
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
            }
        });
    }

    private void initZoomFade() {
        mZoomFadeBanner = (BGABanner) findViewById(R.id.banner_main_zoomFade);
        mZoomFadeViews = getViews(4);
        mZoomFadeBanner.setViews(mZoomFadeViews);

        mEngine.fourItem().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                for (int i = 0; i < mZoomFadeViews.size(); i++) {
                    Glide.with(MainActivity.this).load(bannerModel.imgs.get(i)).placeholder(R.drawable.holder).error(R.drawable.holder).into(mZoomFadeViews.get(i));
                }
                mZoomFadeBanner.setTips(bannerModel.tips);
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
            }
        });
    }

    private void initFade() {
        mFadeBanner = (BGABanner) findViewById(R.id.banner_main_fade);
        mFadeViews = getViews(3);
        mFadeBanner.setViews(mFadeViews);

        mEngine.threeItem().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                for (int i = 0; i < mFadeViews.size(); i++) {
                    Glide.with(MainActivity.this).load(bannerModel.imgs.get(i)).placeholder(R.drawable.holder).error(R.drawable.holder).into(mFadeViews.get(i));
                }
                mFadeBanner.setTips(bannerModel.tips);
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
            }
        });
    }

    private void initZoomCenter() {
        mZoomCenterBanner = (BGABanner) findViewById(R.id.banner_main_zoomCenter);
        mZoomCenterViews = getViews(6);
        mZoomCenterBanner.setViews(mZoomCenterViews);

        mEngine.sixItem().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                for (int i = 0; i < mZoomCenterViews.size(); i++) {
                    Glide.with(MainActivity.this).load(bannerModel.imgs.get(i)).placeholder(R.drawable.holder).error(R.drawable.holder).into(mZoomCenterViews.get(i));
                }
                mZoomCenterBanner.setTips(bannerModel.tips);
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
            }
        });
    }

    private void initZoom() {
        mZoomBanner = (BGABanner) findViewById(R.id.banner_main_zoom);
        mZoomViews = getViews(5);
        mZoomBanner.setViews(mZoomViews);

        mEngine.fiveItem().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                for (int i = 0; i < mZoomViews.size(); i++) {
                    Glide.with(MainActivity.this).load(bannerModel.imgs.get(i)).placeholder(R.drawable.holder).error(R.drawable.holder).into(mZoomViews.get(i));
                }
                mZoomBanner.setTips(bannerModel.tips);
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
            }
        });
    }

    private void initStack() {
        mStackBanner = (BGABanner) findViewById(R.id.banner_main_stack);
        mStackViews = getViews(4);
        mStackBanner.setViews(mStackViews);

        mEngine.fourItem().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                for (int i = 0; i < mStackViews.size(); i++) {
                    Glide.with(MainActivity.this).load(bannerModel.imgs.get(i)).placeholder(R.drawable.holder).error(R.drawable.holder).into(mStackViews.get(i));
                }
                mStackBanner.setTips(bannerModel.tips);
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
            }
        });
    }

    private void initZoomStack() {
        mZoomStackBanner = (BGABanner) findViewById(R.id.banner_main_zoomStack);
        mZoomStackViews = getViews(3);
        mZoomStackBanner.setViews(mZoomStackViews);

        mEngine.threeItem().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                for (int i = 0; i < mZoomStackViews.size(); i++) {
                    Glide.with(MainActivity.this).load(bannerModel.imgs.get(i)).placeholder(R.drawable.holder).error(R.drawable.holder).into(mZoomStackViews.get(i));
                }
                mZoomStackBanner.setTips(bannerModel.tips);
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
            }
        });
    }

    private void initDepth() {
        mDepthBanner = (BGABanner) findViewById(R.id.banner_main_depth);
        mDepthViews = getViews(6);
        mDepthBanner.setViews(mDepthViews);

        mEngine.sixItem().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                for (int i = 0; i < mDepthViews.size(); i++) {
                    Glide.with(MainActivity.this).load(bannerModel.imgs.get(i)).placeholder(R.drawable.holder).error(R.drawable.holder).into(mDepthViews.get(i));
                }
                mDepthBanner.setTips(bannerModel.tips);
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
            }
        });
    }

    private List<ImageView> getViews(int count) {
        List<ImageView> views = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            views.add((ImageView) getLayoutInflater().inflate(R.layout.view_image, null));
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
            default:
                break;
        }
    }
}
