package cn.edu.ncist.ncistapkmarket.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.edu.ncist.ncistapkmarket.R;
import cn.edu.ncist.ncistapkmarket.adapter.AppFragmenAdapter;
import cn.edu.ncist.ncistapkmarket.adapter.GameFragmenAdapter;
import cn.edu.ncist.ncistapkmarket.base.BaseActivity;
import cn.edu.ncist.ncistapkmarket.base.BaseFragment;
import cn.edu.ncist.ncistapkmarket.entity.AppEntity;
import cn.edu.ncist.ncistapkmarket.entity.GameEntity;
import cn.edu.ncist.ncistapkmarket.view.AutoLoadRecylerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xxl on 2017/5/7.
 */

public class GameFragment extends BaseFragment {

    @BindView(R.id.game_swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.game_recyclerview)
    AutoLoadRecylerView recyclerView;

    private int index = 0;

    List<GameEntity.ListBean> list = new ArrayList<>();
    private GameFragmenAdapter gameFragmenAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initSwipeRefresh();
        initRecyclerView();
    }

    @Override
    protected void initData() {
        index = 0;
        getData(index);
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
                index = 0;
                getData(index);
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    /**
     * 联网获取数据
     *
     * @param index 请求的页号，即请求的是第几页
     */
    private void getData(final int index) {
        OkHttpClient okhttp = new OkHttpClient();
        final Request.Builder request = new Request.Builder().url(getString(R.string.app_api) + getString(R.string.game) + "?index=" + index);
        request.method("GET", null);
        Request build = request.build();
        Call call = okhttp.newCall(build);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                BaseActivity activity = (BaseActivity) GameFragment.this.context;
                if (index >= 2) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(GameFragment.this.context, "没有更多了", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }
                GameEntity gameEntity = new Gson().fromJson("{list:" + response.body().string() + "}", GameEntity.class);
                List<GameEntity.ListBean> listBeen = gameEntity.getList();
                if (index == 0) {
                    list.clear();
                    list.addAll(listBeen);
                } else {
                    list.addAll(listBeen);
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gameFragmenAdapter.fillToData(list);
                    }
                });
                recyclerView.setLoading(false);
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    /**
     * 初始recyclerView
     */
    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        gameFragmenAdapter = new GameFragmenAdapter(context);
        recyclerView.setAdapter(gameFragmenAdapter);
        recyclerView.setLoadMoreListener(new AutoLoadRecylerView.loadMoreListener() {
            @Override
            public void onLoadMore() {
                index++;
                getData(index);
                Toast.makeText(context, "加载更多中...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
