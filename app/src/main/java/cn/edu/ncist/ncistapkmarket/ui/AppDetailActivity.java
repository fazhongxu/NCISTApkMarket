package cn.edu.ncist.ncistapkmarket.ui;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.ncist.ncistapkmarket.R;
import cn.edu.ncist.ncistapkmarket.base.BaseActivity;
import cn.edu.ncist.ncistapkmarket.entity.AppDetailEntity;
import cn.edu.ncist.ncistapkmarket.interfaces.Constants;
import cn.edu.ncist.ncistapkmarket.net.AppDetailService;
import cn.edu.ncist.ncistapkmarket.utils.ApkUtil;
import cn.edu.ncist.ncistapkmarket.utils.DownloadInfo;
import cn.edu.ncist.ncistapkmarket.utils.DownloadManager;
import cn.edu.ncist.ncistapkmarket.utils.DownloadService;
import cn.edu.ncist.ncistapkmarket.utils.GsonUtil;
import cn.edu.ncist.ncistapkmarket.utils.SharedPreferencesUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * app详情页面
 */
public class AppDetailActivity extends BaseActivity {

    @BindView(R.id.toobar)
    Toolbar toobar;
    @BindView(R.id.iv_app_icon)
    ImageView ivAppIcon;
    @BindView(R.id.tv_app_name)
    TextView tvAppName;
    @BindView(R.id.rb)
    RatingBar rb;
    @BindView(R.id.tv_app_size)
    TextView tvAppSize;
    @BindView(R.id.tv_install_num)
    TextView tvInstallNum;
    @BindView(R.id.tv_official)
    TextView tvOfficial;
    @BindView(R.id.rl_app_detail)
    RelativeLayout rlAppDetail;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.ll_app_detail_screen_container)
    LinearLayout llAppDetailScreenContainer;
    @BindView(R.id.tv_app_introduce)
    TextView tvAppIntroduce;
    @BindView(R.id.tv_app_msg)
    TextView tvAppMsg;
    @BindView(R.id.tv_app_release_version)
    TextView tvAppReleaseVersion;
    @BindView(R.id.tv_app_release_date)
    TextView tvAppReleaseDate;
    @BindView(R.id.btn_install)
    Button btnInstall;
    @BindView(R.id.pb_download_progress)
    ProgressBar pbDownloadProgress;

    private String packageName;
    private DownloadManager downloadManager;
    private DownloadInfo downLoadInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);
        ButterKnife.bind(this);
        //获取通过Intent传递过来的数据
        packageName = getIntent().getStringExtra("packageName");
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initToolbar();
    }

    /**
     * 初始化toolbar
     */
    private void initToolbar() {
//        toobar.setTitle(getString(R.string.app_detail));
        setSupportActionBar(toobar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//设置默认隐藏标题栏
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置支持返回键显示
        int themeColor = SharedPreferencesUtils.getInt(this, Constants.THEME_COLOR_KEY);
        toobar.setBackgroundColor(themeColor == -1 ? ContextCompat.getColor(this, R.color.colorPrimary) : themeColor);
        //初始化toolbar离状态栏的高度
        toobar.setPadding(0, getStatusBarHeight(), 0, 0);
    }

    /**
     * 获取状态栏高度
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");//status_bar_height 获取Android设备中状态栏id
        if (resourceId > 0) {
            result = getResources().getDimensionPixelOffset(resourceId);
        }
        return result;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        getDetailData();
    }

    /**
     * 获取网络数据
     */
    public void getDetailData() {
        OkHttpClient okHttpClient = new OkHttpClient();  //新建OkHttpClient
        Request.Builder builder = new Request.Builder().url(getString(R.string.app_detail_api) + packageName);//设置url
        builder.method("GET", null);//设置请求方法
        Request build = builder.build();//执行build
        Call call = okHttpClient.newCall(build);//新建请求

        call.enqueue(new Callback() {//调用方法执行网络请求
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = GsonUtil.GsonString(response.body().string());//把json数据转换成String类型
                String s1 = s.replaceAll("\\\\", "");//后台传入了\n，解析出错，所以用正则去掉\n
                String jsonString = s1.substring(1, s1.length() - 1);//去掉sring两边的""
                AppDetailEntity appDetailEntity = GsonUtil.GsonToBean(jsonString, AppDetailEntity.class);//把得到的json字符串转换为实体类
                initView(appDetailEntity);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("okhttp", "失败了" + e.getMessage());
            }

        });
    }

    /**
     * 初始化显示
     */
    private void initView(final AppDetailEntity appDetailEntity) {
        //在子线程中更新ui
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (appDetailEntity != null) {
                    Log.e("222", appDetailEntity.getIconUrl());
                    Picasso.with(AppDetailActivity.this)            //加载图片
                            .load(getString(R.string.app_api) + appDetailEntity.getIconUrl())
                            .into(ivAppIcon);
                    tvAppName.setText(appDetailEntity.getName());//app名称
                    rb.setRating((float) appDetailEntity.getStars());//设置评分星星
                    int appSize = appDetailEntity.getSize();
                    String fileSize = Formatter.formatFileSize(AppDetailActivity.this, appSize);//格式化软件大小
                    tvAppSize.setText(fileSize);
                    tvInstallNum.setText(appDetailEntity.getDownloadNum());//设置下载安装量
                    //应用简介
                    tvAppIntroduce.setText(appDetailEntity.getDes());//软件介绍
                    //详细信息
                    tvAppReleaseVersion.setText(getString(R.string.app_version) + appDetailEntity.getVersion());//设置版本号
                    tvAppReleaseDate.setText(getString(R.string.app_date) + appDetailEntity.getDate());//设置发布日期
                    List<AppDetailEntity.SafeBean> safe = appDetailEntity.getSafe();//安全描述信息
                    for (AppDetailEntity.SafeBean app : safe) {
                        System.out.println(app.getSafeDes());
                        System.out.println(app.getSafeDesUrl());
                        System.out.println(app.getSafeDesColor());
                        System.out.println(app.getSafeUrl());
                    }
                }
            }
        });

        downloadManager = DownloadService.getDownloadManager(AppDetailActivity.this);
        //下载
        btnInstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downLoadInfo = downloadManager.getDownloadInfoByAppId(appDetailEntity.getId() + "");
                if (downLoadInfo == null) {
                    //参数 appId应用编号
                    String appId = appDetailEntity.getId() + "";
                    //参数 下载地址
                    String url = getString(R.string.app_api) + appDetailEntity.getDownloadUrl();
                    //参数 文件名
                    String fileName = appDetailEntity.getName();
                    //参数 保存路径
                    String target = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ncist/" + packageName + ".apk";
                    //参数  是否支持断点
                    boolean autoResume = true;
                    //参数  重命名
                    boolean autoRename = false;
                    //参数回调对象.处理界面更新业务逻辑
                    try {
                        downloadManager.addNewDownload(appId, url, fileName, target, autoResume, autoRename, callback);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }else {
                    //继传
                    //3.5 下载按钮的点击事件。
                    //loading当前处于下载状态
                    if (downLoadInfo.getState() == HttpHandler.State.LOADING) {
                        try {
                            downloadManager.stopDownload(downLoadInfo);
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                        btnInstall.setText("继续");
                    } else {
                        try {
                            downloadManager.resumeDownload(downLoadInfo, callback);
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
    //步骤四.由于整个下载过程没有界面更新，用户不知道下载进度。所以编写进度更新的业务逻辑。编写在CallBack空方法里面
    RequestCallBack<File> callback = new RequestCallBack<File>() {
        //下载过程中的进度变化
        @Override
        public void onLoading(long total, long current, boolean isUploading) {
            super.onLoading(total, current, isUploading);
            if (total == 0) {
                total = 1;
            }
            int percent = (int) (current * 100f / total + 0.5f);
            btnInstall.setText(percent + "%");
            pbDownloadProgress.setMax(100);
            pbDownloadProgress.setProgress(percent);
        }
        @Override
        public void onSuccess(ResponseInfo<File> responseInfo) {
            btnInstall.setText("安装");
            pbDownloadProgress.setMax(100);
            pbDownloadProgress.setProgress(100);
            //调用隐式意图的安装
            ApkUtil.install(AppDetailActivity.this,responseInfo.result.getAbsolutePath());
        }

        @Override
        public void onFailure(HttpException e, String s) {
            btnInstall.setText("继续");
        }
    };

    //步骤五。进入页面需要进行回显示（把断点记录出来。设置给界面）
    private void resumeDownLoadInfo(String appId) {
        //5.1获取断点记录
        DownloadInfo downloadInfo = downloadManager.getDownloadInfoByAppId(appId);
        if (downloadInfo == null) {
            btnInstall.setText("下载");
            pbDownloadProgress.setMax(100);
            pbDownloadProgress.setProgress(0);
        } else {
            //5.2计算百分比
            long total = downloadInfo.getFileLength();
            if (total == 0) {
                total = 1;
            }
            int percent = (int) (downloadInfo.getProgress() * 100f / total + 0.5f);
            pbDownloadProgress.setMax(100);
            pbDownloadProgress.setProgress(percent);
            if (downloadInfo.getState() == HttpHandler.State.LOADING) {
                btnInstall.setText(percent + "%");
            } else {
                btnInstall.setText("继续");
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
