package cn.edu.ncist.ncistapkmarket.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.edu.ncist.ncistapkmarket.R;
import cn.edu.ncist.ncistapkmarket.adapter.AppFragmenAdapter;
import cn.edu.ncist.ncistapkmarket.base.BaseActivity;
import cn.edu.ncist.ncistapkmarket.base.BaseFragment;
import cn.edu.ncist.ncistapkmarket.entity.AppEntity;
import cn.edu.ncist.ncistapkmarket.view.AutoLoadRecylerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xxl on 2017/5/7.
 */

public class AppFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    AutoLoadRecylerView recyclerview;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private int index = 0;//请求页号
    private List<AppEntity.ListBean> list = new ArrayList<>();
    private AppFragmenAdapter appAdapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
//                    appAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_app;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initSwipeRefresh();
        initRecyclerView();
    }

    @Override
    protected void initData() {
        getData(0);
    }

    /**
     * 联网获取数据
     *
     * @param index 请求的页号，即请求的是第几页
     */
    private void getData(final int index) {
        OkHttpClient okhttp = new OkHttpClient();
        final Request.Builder request = new Request.Builder().url(getString(R.string.app_api) + getString(R.string.apps) + "?index=" + index);
        request.method("GET", null);
        Request build = request.build();
        Call call = okhttp.newCall(build);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                AppEntity appEntity = new Gson().fromJson("{list:" + response.body().string() + "}", AppEntity.class);
                final List<AppEntity.ListBean> listBeen = appEntity.getList();
                if (index ==0 ) {
                    list.clear();
                    list.addAll(listBeen);
                }else {
                    list.addAll(listBeen);
                }
                BaseActivity activity = (BaseActivity) AppFragment.this.context;

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        appAdapter.fillToData(list);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    /**
     * 初始化下拉刷新
     */
    private void initSwipeRefresh() {
        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(getContext(), android.R.color.holo_orange_light)
                , ContextCompat.getColor(getContext(), android.R.color.holo_blue_bright)
                , ContextCompat.getColor(getContext(), android.R.color.holo_purple));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                getData(0);
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    /**
     * 初始recyclerView
     */
    private void initRecyclerView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(context));
        appAdapter = new AppFragmenAdapter(context);
        recyclerview.setAdapter(appAdapter);
    }
}
