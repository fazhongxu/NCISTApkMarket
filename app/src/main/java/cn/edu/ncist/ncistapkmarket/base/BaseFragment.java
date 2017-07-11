package cn.edu.ncist.ncistapkmarket.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by xxl on 2017/4/22.
 */

/**
 * Fragment的基类，用于处理fragment的共同操作
 */
public abstract class BaseFragment extends Fragment {
    protected Context context;//获取上下文方便子类使用

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        this.context = getContext();
        initData();
        View view = LayoutInflater.from(context).inflate(layoutId, container, false);
        ButterKnife.bind(this, view);//注意这里传入的值是this,view，单独传入view会导致子类通过ButterKnife获取空指针
        initView(view, savedInstanceState);
        return view;
    }


    /**
     * 获取布局id,由子类实现,onCreateView中需要加载view,传入一个id,让父类加载
     */
    protected abstract int getLayoutId();

    /**
     * 初始化控件
     */
    protected abstract void initView(View view, Bundle savedInstanceState);

    /**
     * 初始化数据
     */
    protected abstract void initData();
}
