package cn.edu.ncist.ncistapkmarket.net;


import cn.edu.ncist.ncistapkmarket.entity.ApkNewVersionEntity;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by xxl on 2017/4/23.
 * 应用新版本获取接口
 */

public interface ApkNewVersionService {
    @GET("newApp.json")
    Call<ApkNewVersionEntity> getNewVersionInfo();
}
