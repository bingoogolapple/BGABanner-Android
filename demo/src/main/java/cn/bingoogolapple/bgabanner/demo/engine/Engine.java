package cn.bingoogolapple.bgabanner.demo.engine;

import java.util.List;

import cn.bingoogolapple.bgabanner.demo.model.BannerModel;
import cn.bingoogolapple.bgabanner.demo.model.RefreshModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;


/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/9/17 下午12:44
 * 描述:
 */
public interface Engine {

    @GET("{itemCount}item.json")
    Call<BannerModel> fetchItemsWithItemCount(@Path("itemCount") int itemCount);

    @GET
    Call<List<RefreshModel>> loadContentData(@Url String url);
}