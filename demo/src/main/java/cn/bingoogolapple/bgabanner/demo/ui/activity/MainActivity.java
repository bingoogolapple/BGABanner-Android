package cn.bingoogolapple.bgabanner.demo.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.demo.App;
import cn.bingoogolapple.bgabanner.demo.R;
import cn.bingoogolapple.bgabanner.demo.engine.Engine;
import cn.bingoogolapple.bgabanner.demo.model.BannerModel;
import retrofit.Callback;
import retrofit.Response;

public class MainActivity extends AppCompatActivity {
    private BGABanner mDefaultBanner;
    private List<View> mDefaultViews;

    private BGABanner mCubeBanner;
    private List<View> mCubeViews;

    private BGABanner mAccordionBanner;
    private List<View> mAccordionViews;

    private BGABanner mFlipBanner;
    private List<View> mFlipViews;

    private BGABanner mRotateBanner;
    private List<View> mRotateViews;

    private BGABanner mAlphaBanner;
    private List<View> mAlphaViews;

    private BGABanner mZoomFadeBanner;
    private List<View> mZoomFadeViews;

    private BGABanner mFadeBanner;
    private List<View> mFadeViews;

    private BGABanner mZoomCenterBanner;
    private List<View> mZoomCenterViews;

    private BGABanner mZoomBanner;
    private List<View> mZoomViews;

    private BGABanner mStackBanner;
    private List<View> mStackViews;

    private BGABanner mZoomStackBanner;
    private List<View> mZoomStackViews;

    private BGABanner mDepthBanner;
    private List<View> mDepthViews;

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
        mDefaultViews = getViews(5);
        mDefaultBanner.setViews(mDefaultViews);

        mEngine.fiveItem().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                SimpleDraweeView simpleDraweeView;
                for (int i = 0; i < mDefaultViews.size(); i++) {
                    simpleDraweeView = (SimpleDraweeView) mDefaultViews.get(i);
                    simpleDraweeView.setImageURI(Uri.parse(bannerModel.imgs.get(i)));

                    // 为每一页添加点击事件
                    final int finalPosition = i;
                    simpleDraweeView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(App.getInstance(), "点击了第" + (finalPosition + 1) + "页", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                mDefaultBanner.setTips(bannerModel.tips);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    private void initCube() {
        mCubeBanner = (BGABanner) findViewById(R.id.banner_main_cube);
        mCubeViews = getViews(6);
        mCubeBanner.setViews(mCubeViews);

        mEngine.sixItem().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                SimpleDraweeView simpleDraweeView;
                for (int i = 0; i < mCubeViews.size(); i++) {
                    simpleDraweeView = (SimpleDraweeView) mCubeViews.get(i);
                    simpleDraweeView.setImageURI(Uri.parse(bannerModel.imgs.get(i)));
                }
                // 也可以不设置tips
//                mCubeBanner.setTips(bannerModel.tips);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    private void initAccordion() {
        mAccordionBanner = (BGABanner) findViewById(R.id.banner_main_accordion);
        mAccordionViews = getViews(4);
        mAccordionBanner.setViews(mAccordionViews);

        mEngine.fourItem().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                SimpleDraweeView simpleDraweeView;
                for (int i = 0; i < mAccordionViews.size(); i++) {
                    simpleDraweeView = (SimpleDraweeView) mAccordionViews.get(i);
                    simpleDraweeView.setImageURI(Uri.parse(bannerModel.imgs.get(i)));
                }
                mAccordionBanner.setTips(bannerModel.tips);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    private void initFlip() {
        mFlipBanner = (BGABanner) findViewById(R.id.banner_main_flip);
        mFlipViews = getViews(3);
        mFlipBanner.setViews(mFlipViews);

        mEngine.threeItem().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                SimpleDraweeView simpleDraweeView;
                for (int i = 0; i < mFlipViews.size(); i++) {
                    simpleDraweeView = (SimpleDraweeView) mFlipViews.get(i);
                    simpleDraweeView.setImageURI(Uri.parse(bannerModel.imgs.get(i)));
                }
                mFlipBanner.setTips(bannerModel.tips);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    private void initRotate() {
        mRotateBanner = (BGABanner) findViewById(R.id.banner_main_rotate);
        mRotateViews = getViews(6);
        mRotateBanner.setViews(mRotateViews);

        mEngine.sixItem().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                SimpleDraweeView simpleDraweeView;
                for (int i = 0; i < mRotateViews.size(); i++) {
                    simpleDraweeView = (SimpleDraweeView) mRotateViews.get(i);
                    simpleDraweeView.setImageURI(Uri.parse(bannerModel.imgs.get(i)));
                }
                mRotateBanner.setTips(bannerModel.tips);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    private void initAlpha() {
        mAlphaBanner = (BGABanner) findViewById(R.id.banner_main_alpha);
        mAlphaViews = getViews(5);
        mAlphaBanner.setViews(mAlphaViews);

        mEngine.fiveItem().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                SimpleDraweeView simpleDraweeView;
                for (int i = 0; i < mAlphaViews.size(); i++) {
                    simpleDraweeView = (SimpleDraweeView) mAlphaViews.get(i);
                    simpleDraweeView.setImageURI(Uri.parse(bannerModel.imgs.get(i)));
                }
                mAlphaBanner.setTips(bannerModel.tips);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    private void initZoomFade() {
        mZoomFadeBanner = (BGABanner) findViewById(R.id.banner_main_zoomFade);
        mZoomFadeViews = getViews(4);
        mZoomFadeBanner.setViews(mZoomFadeViews);

        mEngine.fourItem().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                SimpleDraweeView simpleDraweeView;
                for (int i = 0; i < mZoomFadeViews.size(); i++) {
                    simpleDraweeView = (SimpleDraweeView) mZoomFadeViews.get(i);
                    simpleDraweeView.setImageURI(Uri.parse(bannerModel.imgs.get(i)));
                }
                mZoomFadeBanner.setTips(bannerModel.tips);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    private void initFade() {
        mFadeBanner = (BGABanner) findViewById(R.id.banner_main_fade);
        mFadeViews = getViews(3);
        mFadeBanner.setViews(mFadeViews);

        mEngine.threeItem().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                SimpleDraweeView simpleDraweeView;
                for (int i = 0; i < mFadeViews.size(); i++) {
                    simpleDraweeView = (SimpleDraweeView) mFadeViews.get(i);
                    simpleDraweeView.setImageURI(Uri.parse(bannerModel.imgs.get(i)));
                }
                mFadeBanner.setTips(bannerModel.tips);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    private void initZoomCenter() {
        mZoomCenterBanner = (BGABanner) findViewById(R.id.banner_main_zoomCenter);
        mZoomCenterViews = getViews(6);
        mZoomCenterBanner.setViews(mZoomCenterViews);

        mEngine.sixItem().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                SimpleDraweeView simpleDraweeView;
                for (int i = 0; i < mZoomCenterViews.size(); i++) {
                    simpleDraweeView = (SimpleDraweeView) mZoomCenterViews.get(i);
                    simpleDraweeView.setImageURI(Uri.parse(bannerModel.imgs.get(i)));
                }
                mZoomCenterBanner.setTips(bannerModel.tips);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    private void initZoom() {
        mZoomBanner = (BGABanner) findViewById(R.id.banner_main_zoom);
        mZoomViews = getViews(5);
        mZoomBanner.setViews(mZoomViews);

        mEngine.fiveItem().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                SimpleDraweeView simpleDraweeView;
                for (int i = 0; i < mZoomViews.size(); i++) {
                    simpleDraweeView = (SimpleDraweeView) mZoomViews.get(i);
                    simpleDraweeView.setImageURI(Uri.parse(bannerModel.imgs.get(i)));
                }
                mZoomBanner.setTips(bannerModel.tips);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    private void initStack() {
        mStackBanner = (BGABanner) findViewById(R.id.banner_main_stack);
        mStackViews = getViews(4);
        mStackBanner.setViews(mStackViews);

        mEngine.fourItem().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                SimpleDraweeView simpleDraweeView;
                for (int i = 0; i < mStackViews.size(); i++) {
                    simpleDraweeView = (SimpleDraweeView) mStackViews.get(i);
                    simpleDraweeView.setImageURI(Uri.parse(bannerModel.imgs.get(i)));
                }
                mStackBanner.setTips(bannerModel.tips);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    private void initZoomStack() {
        mZoomStackBanner = (BGABanner) findViewById(R.id.banner_main_zoomStack);
        mZoomStackViews = getViews(3);
        mZoomStackBanner.setViews(mZoomStackViews);

        mEngine.threeItem().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                SimpleDraweeView simpleDraweeView;
                for (int i = 0; i < mZoomStackViews.size(); i++) {
                    simpleDraweeView = (SimpleDraweeView) mZoomStackViews.get(i);
                    simpleDraweeView.setImageURI(Uri.parse(bannerModel.imgs.get(i)));
                }
                mZoomStackBanner.setTips(bannerModel.tips);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    private void initDepth() {
        mDepthBanner = (BGABanner) findViewById(R.id.banner_main_depth);
        mDepthViews = getViews(6);
        mDepthBanner.setViews(mDepthViews);

        mEngine.sixItem().enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                SimpleDraweeView simpleDraweeView;
                for (int i = 0; i < mDepthViews.size(); i++) {
                    simpleDraweeView = (SimpleDraweeView) mDepthViews.get(i);
                    simpleDraweeView.setImageURI(Uri.parse(bannerModel.imgs.get(i)));
                }
                mDepthBanner.setTips(bannerModel.tips);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    private List<View> getViews(int count) {
        List<View> views = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            views.add(getLayoutInflater().inflate(R.layout.view_image, null));
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
