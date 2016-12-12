package cn.bingoogolapple.bgabanner.demo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.demo.App;
import cn.bingoogolapple.bgabanner.demo.R;
import cn.bingoogolapple.bgabanner.demo.engine.Engine;
import cn.bingoogolapple.bgabanner.demo.model.BannerModel;
import cn.bingoogolapple.bgabanner.transformer.TransitionEffect;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements BGABanner.Delegate<ImageView, String>, BGABanner.Adapter<ImageView, String> {
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

        setTitle("BGABannerDemo");
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
        mDefaultBanner.setDelegate(this);
        mCubeBanner.setDelegate(this);
    }

    private void loadData() {
        loadData(mDefaultBanner, 1);
        loadData(mCubeBanner, 2);
        loadData(mAccordionBanner, 3);
        loadData(mFlipBanner, 4);
        loadData(mRotateBanner, 5);
        loadData(mAlphaBanner, 6);
        loadData(mZoomFadeBanner, 3);
        loadData(mFadeBanner, 4);
        loadData(mZoomCenterBanner, 5);
        loadData(mZoomBanner, 6);
        loadData(mStackBanner, 3);
        loadData(mZoomStackBanner, 4);
        loadData(mDepthBanner, 5);
    }

    private void loadData(final BGABanner banner, final int count) {
        mEngine.fetchItemsWithItemCount(count).enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                BannerModel bannerModel = response.body();

                /**
                 * 设置是否开启自动轮播，需要在 setData 方法之前调用，并且调了该方法后必须再调用一次 setData 方法
                 * 例如根据图片当图片数量大于 1 时开启自动轮播，等于 1 时不开启自动轮播
                 */
//                banner.setAutoPlayAble(bannerModel.imgs.size() > 1);

                banner.setAdapter(MainActivity.this);
                banner.setData(bannerModel.imgs, bannerModel.tips);
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
                Toast.makeText(App.getInstance(), "网络数据加载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
        Toast.makeText(banner.getContext(), "点击了第" + (position + 1) + "页", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
        Glide.with(itemView.getContext())
                .load(model)
                .placeholder(R.drawable.holder)
                .error(R.drawable.holder)
                .dontAnimate()
                .centerCrop()
                .into(itemView);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_main_visible:
                mDefaultBanner.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_main_invisible:
                mDefaultBanner.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_main_gone:
                mDefaultBanner.setVisibility(View.GONE);
                break;
            case R.id.tv_main_enable_auto_play:
                /**
                 * 设置是否开启自动轮播，需要在 setData 方法之前调用，并且调了该方法后必须再调用一次 setData 方法
                 * 例如根据图片当图片数量大于 1 时开启自动轮播，等于 1 时不开启自动轮播
                 */
                mDefaultBanner.setAutoPlayAble(true);
                break;
            case R.id.tv_main_disable_auto_play:
                /**
                 * 设置是否开启自动轮播，需要在 setData 方法之前调用，并且调了该方法后必须再调用一次 setData 方法
                 * 例如根据图片当图片数量大于 1 时开启自动轮播，等于 1 时不开启自动轮播
                 */
                mDefaultBanner.setAutoPlayAble(false);
                break;
            case R.id.tv_main_start_auto_play:
                // 仅在 autoPlayAble 为 true 时才会生效「开发者使用该库时不用调用该方法，这里只是为了演示而已，界面可见时在 BGABanner 内部已经帮开发者调用了该方方法」
                mDefaultBanner.startAutoPlay();
                break;
            case R.id.tv_main_stop_auto_play:
                // 仅在 autoPlayAble 为 true 时才会生效「开发者使用该库时不用调用该方法，这里只是为了演示而已，界面不可见时在 BGABanner 内部已经帮开发者调用了该方方法」
                mDefaultBanner.stopAutoPlay();
                break;
            case R.id.tv_main_select_page_one:
                mDefaultBanner.setCurrentItem(0);
                break;
            case R.id.tv_main_select_page_two:
                mDefaultBanner.setCurrentItem(1);
                break;
            case R.id.tv_main_select_page_three:
                mDefaultBanner.setCurrentItem(2);
                break;
            case R.id.tv_main_select_page_four:
                mDefaultBanner.setCurrentItem(3);
                break;
            case R.id.tv_main_select_page_five:
                mDefaultBanner.setCurrentItem(4);
                break;
            case R.id.tv_main_get_item_count:
                Toast.makeText(App.getInstance(), "广告条总页数为 " + mDefaultBanner.getItemCount(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_main_get_current_item:
                Toast.makeText(App.getInstance(), "广告当前索引位置为 " + mDefaultBanner.getCurrentItem(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_main_load_one_item:
                loadData(mDefaultBanner, 1);
                break;
            case R.id.tv_main_load_two_item:
                loadData(mDefaultBanner, 2);
                break;
            case R.id.tv_main_load_three_item:
                loadData(mDefaultBanner, 3);
                break;
            case R.id.tv_main_load_five_item:
                loadData(mDefaultBanner, 5);
                break;
            case R.id.tv_main_cube:
                mDefaultBanner.setTransitionEffect(TransitionEffect.Cube);
                break;
            case R.id.tv_main_depth:
                mDefaultBanner.setTransitionEffect(TransitionEffect.Depth);
                break;
            case R.id.tv_main_flip:
                mDefaultBanner.setTransitionEffect(TransitionEffect.Flip);
                break;
            case R.id.tv_main_rotate:
                mDefaultBanner.setTransitionEffect(TransitionEffect.Rotate);
                break;
            case R.id.tv_main_alpha:
                mDefaultBanner.setTransitionEffect(TransitionEffect.Alpha);
                break;
            case R.id.tv_main_listview_demo:
                startActivity(new Intent(this, ListViewDemoActivity.class));
                break;
            case R.id.tv_main_recyclerview_demo:
                startActivity(new Intent(this, RecyclerViewDemoActivity.class));
                break;
            case R.id.tv_main_fresco:
                startActivity(new Intent(this, FrescoDemoActivity.class));
                break;

            default:
                break;
        }
    }
}
