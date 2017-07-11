package cn.edu.ncist.ncistapkmarket.net;

import cn.edu.ncist.ncistapkmarket.entity.HomeAppDataEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by xxl on 2017/4/29.
 * 首页数据接口
 */

public interface HomeAppDataService {
    //retrofit请求的子路径
    @GET("home")
    Call<HomeAppDataEntity> getHomeAppData(@Query("index") int index);
}
