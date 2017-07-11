package cn.edu.ncist.ncistapkmarket.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.ncist.ncistapkmarket.R;
import cn.edu.ncist.ncistapkmarket.entity.ApkNewVersionEntity;
import cn.edu.ncist.ncistapkmarket.net.ApkNewVersionDowloadService;
import cn.edu.ncist.ncistapkmarket.net.ApkNewVersionService;
import cn.edu.ncist.ncistapkmarket.utils.FileDownLoadUtil;
import cn.edu.ncist.ncistapkmarket.utils.PackageUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.tv_version_name)
    TextView tvVersionName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        String versionName = PackageUtil.getVersionName(this);
        tvVersionName.setText("v" + versionName);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //获取APP版本号，用于和服务器上的版本号做比较
        int versionCode = PackageUtil.getVersionCode(this);
        getServiceData();
    }

    /**
     * 联网获取服务器数据
     */
    private void getServiceData() {
        //联网获取新版本
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.version_download_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApkNewVersionService apkNewVersionService = retrofit.create(ApkNewVersionService.class);
        Call<ApkNewVersionEntity> call = apkNewVersionService.getNewVersionInfo();
        call.enqueue(new Callback<ApkNewVersionEntity>() {
            @Override
            public void onResponse(Call<ApkNewVersionEntity> call, Response<ApkNewVersionEntity> response) {
//                Log.i("text",response.body().getVersionName());
                if (response.body() != null) {
                    //获取服务器上的版本，与这个做比较，如果服务器上的版本大于当前版本，就下载
                    if (response.body().getVersionCode() > PackageUtil.getVersionCode(SplashActivity.this)) {
                        showAlertDialog(response.body());
                    }else {
                        enterMain();//如果获取到的服务器上的版本和当前版本一致或者比当前版本低，就进入首页
                    }
                }
            }

            @Override
            public void onFailure(Call<ApkNewVersionEntity> call, Throwable t) {
                Log.i("text", "失败了");
                enterMain();
            }
        });
    }

    /**
     * 弹出是否下载的对话框
     */
    private void showAlertDialog(final ApkNewVersionEntity data) {
        //自定义dialog样式
        View dialogView = getLayoutInflater().inflate(R.layout.alertdialog_item, null);
        TextView des = (TextView) dialogView.findViewById(R.id.tv_newversion_des);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.search_newversion_hint) + data.getVersionName() + getString(R.string.search_download_hint));

        builder.setPositiveButton(R.string.dialog_download, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //点击下载，开始下载新版本
                downloadNewVersion(data.getDownloadUrl());
            }
        }).setNegativeButton(R.string.dialog_download_after, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterMain();
            }
        });
        des.setText(data.getVersionDes());
        builder.setView(dialogView);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                enterMain();
            }
        });
        builder.show();
    }

    /**
     * 下载最新版本
     */
    private void downloadNewVersion(String url) {
        //这里的url是拆分过的url,在服务器上只有下载的具体根路径，请求需要在根据经的后面拼接子路径，拼接出来的
        //路径是真实可访问下载的路径，相当于传给服务端一个请求参数，参数是子路径
        Retrofit build = new Retrofit.Builder()
                .baseUrl(url)
                .build();
        ApkNewVersionDowloadService dowloadService = build.create(ApkNewVersionDowloadService.class);
        Call<ResponseBody> call = dowloadService.donloadFile();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody body = response.body();
                // TODO: 2017/4/23
                FileDownLoadUtil.downLoadFile(SplashActivity.this,response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * 进入主页
     */
    private void enterMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
