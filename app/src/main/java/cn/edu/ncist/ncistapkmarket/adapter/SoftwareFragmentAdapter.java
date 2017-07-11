package cn.edu.ncist.ncistapkmarket.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.edu.ncist.ncistapkmarket.fragment.AppFragment;
import cn.edu.ncist.ncistapkmarket.fragment.CategoryFragment;
import cn.edu.ncist.ncistapkmarket.fragment.GameFragment;

/**
 * Created by xxl on 2017/5/7.
 */

public class SoftwareFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments =  new ArrayList<>();
    private String[] fragmentsTitle = new String[]{"应用", "游戏", "分类"};

    public SoftwareFragmentAdapter(FragmentManager fm) {
        super(fm);
        initFragments();
    }

    /**
     * 初始化fragments
     */
    private void initFragments() {
        fragments.add(new AppFragment());
        fragments.add(new GameFragment());
        fragments.add(new CategoryFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size() == 0 ? 0 : fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentsTitle[position];
    }
}
