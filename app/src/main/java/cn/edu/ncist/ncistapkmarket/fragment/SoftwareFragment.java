package cn.edu.ncist.ncistapkmarket.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.edu.ncist.ncistapkmarket.R;
import cn.edu.ncist.ncistapkmarket.adapter.AppFragmenAdapter;
import cn.edu.ncist.ncistapkmarket.adapter.SoftwareFragmentAdapter;
import cn.edu.ncist.ncistapkmarket.base.BaseFragment;
import cn.edu.ncist.ncistapkmarket.entity.AppEntity;
import cn.edu.ncist.ncistapkmarket.interfaces.Constants;
import cn.edu.ncist.ncistapkmarket.utils.SharedPreferencesUtils;

/**
 * Created by xxl on 2017/4/26.
 */

public class SoftwareFragment extends BaseFragment {

    @BindView(R.id.software_tablayout)
    TabLayout tableLayout;
    @BindView(R.id.software_viewPager)
    ViewPager viewPager;

    private static SoftwareFragment softwareFragment;
    private AppFragmenAdapter appAdapter;
    private int index = 0;//请求页号
    private List<AppEntity.ListBean> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_software;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initTabs();
    }

    @Override
    protected void initData() {
    }
    /**
     * 初始化tablayout
     */
    private void initTabs() {
        int themeColor = SharedPreferencesUtils.getInt(context, Constants.THEME_COLOR_KEY);
        tableLayout.setSelectedTabIndicatorColor(themeColor == -1 ? ContextCompat.getColor(context, R.color.colorPrimary) : themeColor);
//        SoftwareFragmentAdapter sfa = new SoftwareFragmentAdapter(getFragmentManager());
        //Fragment(父)里面嵌套viewPager+fragment(子)，子fragment的adapter必须使用getChildFragmentManager()，而不是getFragmentManager()
        SoftwareFragmentAdapter sfa = new SoftwareFragmentAdapter(getChildFragmentManager());
        viewPager.setAdapter(sfa);
        tableLayout.setupWithViewPager(viewPager);
    }

    public static Fragment newInstance() {
        if (softwareFragment == null) {
            softwareFragment = new SoftwareFragment();
        }
        return softwareFragment;
    }
}
