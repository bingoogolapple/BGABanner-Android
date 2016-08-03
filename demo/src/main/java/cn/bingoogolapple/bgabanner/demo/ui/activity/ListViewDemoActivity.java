package cn.bingoogolapple.bgabanner.demo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAAdapterViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.demo.App;
import cn.bingoogolapple.bgabanner.demo.R;
import cn.bingoogolapple.bgabanner.demo.engine.Engine;
import cn.bingoogolapple.bgabanner.demo.model.BannerModel;
import cn.bingoogolapple.bgabanner.demo.model.RefreshModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/7/21 下午8:26
 * 描述:
 */
public class ListViewDemoActivity extends Activity {
    private ListView mContentLv;
    private BGABanner mBanner;
    private ContentAdapter mContentAdapter;

    private Engine mEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_demo);
        mContentLv = (ListView) findViewById(R.id.lv_content);

        mEngine = App.getInstance().getEngine();

        initListView();

        loadBannerData();
        loadContentData();
    }

    /**
     * 初始化ListView
     */
    private void initListView() {
        // 初始化HeaderView
        View headerView = View.inflate(this, R.layout.layout_header, null);
        mBanner = (BGABanner) headerView.findViewById(R.id.banner);


        mBanner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
                ((SimpleDraweeView) view).setImageURI(Uri.parse(String.valueOf(model)));
            }
        });

        // 初始化ListView
        mContentLv.addHeaderView(headerView);
        mContentAdapter = new ContentAdapter(this);
        mContentLv.setAdapter(mContentAdapter);
    }

    /**
     * 加载头部广告条的数据
     */
    private void loadBannerData() {
        mEngine.fetchItemsWithItemCount(5).enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                mBanner.setData(bannerModel.imgs, bannerModel.tips);
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
                Toast.makeText(App.getInstance(), "加载广告条数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 加载内容列表数据
     */
    private void loadContentData() {
        mEngine.loadContentData("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/api/defaultdata.json").enqueue(new Callback<List<RefreshModel>>() {
            @Override
            public void onResponse(Call<List<RefreshModel>> call, Response<List<RefreshModel>> response) {
                mContentAdapter.setData(response.body());
            }

            @Override
            public void onFailure(Call<List<RefreshModel>> call, Throwable t) {
                Toast.makeText(App.getInstance(), "加载内容数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private class ContentAdapter extends BGAAdapterViewAdapter<RefreshModel> {

        public ContentAdapter(Context context) {
            super(context, R.layout.item_normal);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, RefreshModel model) {
            helper.setText(R.id.tv_item_normal_title, model.title).setText(R.id.tv_item_normal_detail, model.detail);
        }
    }
}
