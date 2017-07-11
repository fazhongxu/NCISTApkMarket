package cn.edu.ncist.ncistapkmarket.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.edu.ncist.ncistapkmarket.R;
import cn.edu.ncist.ncistapkmarket.adapter.HomeFragmentAdapter;
import cn.edu.ncist.ncistapkmarket.base.BaseFragment;
import cn.edu.ncist.ncistapkmarket.entity.HomeAppDataEntity;
import cn.edu.ncist.ncistapkmarket.net.HomeAppDataService;
import cn.edu.ncist.ncistapkmarket.view.AutoLoadRecylerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xxl on 2017/4/26.
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    AutoLoadRecylerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private static HomeFragment homeFragment;

    List<HomeAppDataEntity.ListBean> data = new ArrayList<>();//存放网络请求获取得到的数据的集合
    private HomeFragmentAdapter homeFragmentAdapter;

    List<String> pictures = new ArrayList<>();//存放网络请求获取得到的ViewPager图片地址

    private int index = 0;//请求页数的索引，get方式传递请求的参数

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        homeFragmentAdapter = new HomeFragmentAdapter(getContext(), data, pictures);
        recyclerView.setAdapter(homeFragmentAdapter);

        //上拉加载
        recyclerView.setLoadMoreListener(new AutoLoadRecylerView.loadMoreListener() {
            @Override
            public void onLoadMore() {
                ++index;
                Toast.makeText(context, "加载更多", Toast.LENGTH_SHORT).show();
                getData(index);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initSwipeRefreshLayout();
        initRecyclerView();
    }

    /**
     * 初始化下拉刷新进度条
     */
    private void initSwipeRefreshLayout() {
        //设置进度条样式
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), android.R.color.holo_orange_light)
                , ContextCompat.getColor(getContext(), android.R.color.holo_blue_bright)
                , ContextCompat.getColor(getContext(), android.R.color.holo_purple));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setLoading(false);//设置可以加载更多
                        index = 0;//获取第一页数据
                        getData(index);
                        swipeRefreshLayout.setRefreshing(false);//隐藏进度条
                    }
                }, 500);
            }
        });
    }

    @Override
    protected void initData() {
        index = 0;
        getData(index);
    }

    public static Fragment newInstance() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        return homeFragment;
    }

    /**
     * 请求网络获取数据
     */
    public void getData(final int page) {
        if (page > 2) {
            Toast.makeText(getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
            return;
        }
        //初始化数据
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.app_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final HomeAppDataService homeAppDataService = retrofit.create(HomeAppDataService.class);
        Call<HomeAppDataEntity> call = homeAppDataService.getHomeAppData(page);
        call.enqueue(new Callback<HomeAppDataEntity>() {
            @Override
            public void onResponse(Call<HomeAppDataEntity> call, Response<HomeAppDataEntity> response) {
                List<HomeAppDataEntity.ListBean> list = response.body().getList();
                List<String> picture = response.body().getPicture();//首页展示的ViewPager使用的图片的url地址
                if (page == 0) {
                    data.clear();//如果是第一页数据，清空集合，保证每次获取的都是最新的数据
                    data.addAll(list);//把请求成功的数据添加到集合中
                    homeFragmentAdapter.notifyDataSetChanged();
                    if (picture != null) {//如果有数据，添加到集合中
                        pictures.addAll(picture);
                        homeFragmentAdapter.notifyDataSetChanged();
                    }
                    if (pictures != null) {//如果集合中有数据，清空集合，保证每次都是新的数据，防止ViewPager滑动的时候数组下表越界
                        pictures.clear();
                        pictures.addAll(picture);
                        homeFragmentAdapter.notifyDataSetChanged();
                    }
                } else {//获取下一页数据
                    data.addAll(list);
                    homeFragmentAdapter.notifyDataSetChanged();//通知adapter更新数据
                    recyclerView.setLoading(false);
                }
//                if (picture != null) {//如果有数据，添加到集合中
//                    pictures.addAll(picture);
//                    homeFragmentAdapter.notifyDataSetChanged();
//                }
//                if (pictures != null) {//如果集合中有数据，清空集合，保证每次都是新的数据，防止ViewPager滑动的时候数组下表越界
//                    pictures.clear();
//                    pictures.addAll(picture);
//                    homeFragmentAdapter.notifyDataSetChanged();
//                }
            }

            @Override
            public void onFailure(Call<HomeAppDataEntity> call, Throwable t) {
                Log.e("text", "失败了" + t.getMessage());
            }
        });
    }
}
