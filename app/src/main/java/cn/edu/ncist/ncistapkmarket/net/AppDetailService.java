package cn.edu.ncist.ncistapkmarket.net;

import cn.edu.ncist.ncistapkmarket.entity.AppDetailEntity;
import cn.edu.ncist.ncistapkmarket.entity.HomeAppDataEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by xxl on 2017/5/1.
 * app详情的接口
 */

public interface AppDetailService {
    //detail是Retrofit的子路径
    @GET("detail")
    Call<AppDetailEntity> getAppDetailInfo(@Query("packageName")String packageName);//此packageName请求参数
}
