package cn.edu.ncist.ncistapkmarket.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.edu.ncist.ncistapkmarket.R;
import cn.edu.ncist.ncistapkmarket.adapter.AppFragmenAdapter;
import cn.edu.ncist.ncistapkmarket.base.BaseFragment;
import cn.edu.ncist.ncistapkmarket.entity.AppEntity;
import cn.edu.ncist.ncistapkmarket.view.AutoLoadRecylerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xxl on 2017/4/26.
 */

public class SoftwareFragment2 extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

    }

/*    @BindView(R.id.recyclerview)
    AutoLoadRecylerView recyclerview;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private static SoftwareFragment2 softwareFragment;
    private AppFragmenAdapter appAdapter;
    private int index = 0;//请求页号
    private List<AppEntity.ListBean> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_software;
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

    *//**
     * 初始recyclerView
     *//*
    private void initRecyclerView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(context));
        appAdapter = new AppFragmenAdapter(context);
        recyclerview.setAdapter(appAdapter);
        recyclerview.setLoadMoreListener(new AutoLoadRecylerView.loadMoreListener() {
            @Override
            public void onLoadMore() {
                index ++;
                getData(index);
            }
        });
    }

    *//**
     * 联网获取数据
     *
     * @param index 请求的页号，即请求的是第几页
     *//*
    private void getData(final int index) {
        OkHttpClient okhttp = new OkHttpClient();
        final Request.Builder request = new Request.Builder().url(getString(R.string.app_api) + getString(R.string.apps) + "?index=" + index);
        request.method("GET", null);
        Request build = request.build();
        Call call = okhttp.newCall(build);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (index >= 2) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "没有更多数据了", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }
                AppEntity appEntity = new Gson().fromJson("{list:" + response.body().string() + "}", AppEntity.class);
                final List<AppEntity.ListBean> listBeen = appEntity.getList();
                if (index == 0) {
                    list.clear();
                    list.addAll(listBeen);
                } else {
                    list.addAll(listBeen);
                    recyclerview.setLoading(false);
                }
                getActivity().runOnUiThread(new Runnable() {
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

    public static Fragment newInstance() {
        if (softwareFragment == null) {
            softwareFragment = new SoftwareFragment2();
        }
        return softwareFragment;
    }*/
}
