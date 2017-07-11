package cn.edu.ncist.ncistapkmarket.net;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;

/**
 * Created by xxl on 2017/4/23.
 */

public interface ApkNewVersionDowloadService {
    @Streaming
    @GET("ncistapkmarket.apk")
    Call<ResponseBody> donloadFile();
}
