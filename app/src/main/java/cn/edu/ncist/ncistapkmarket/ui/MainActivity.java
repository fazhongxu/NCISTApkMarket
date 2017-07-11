package cn.edu.ncist.ncistapkmarket.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.ncist.ncistapkmarket.MApplication;
import cn.edu.ncist.ncistapkmarket.R;
import cn.edu.ncist.ncistapkmarket.base.BaseActivity;
import cn.edu.ncist.ncistapkmarket.fragment.BeautyFragment;
import cn.edu.ncist.ncistapkmarket.fragment.CircleFragment;
import cn.edu.ncist.ncistapkmarket.fragment.HomeFragment;
import cn.edu.ncist.ncistapkmarket.fragment.MineFragment;
import cn.edu.ncist.ncistapkmarket.fragment.SoftwareFragment;
import cn.edu.ncist.ncistapkmarket.interfaces.Constants;
import cn.edu.ncist.ncistapkmarket.utils.SharedPreferencesUtils;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    @BindView(R.id.toobar)
    Toolbar toobar;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initNavigationBar();
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
        setSupportActionBar(toobar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//设置默认隐藏标题栏
        int themeColor = SharedPreferencesUtils.getInt(this, Constants.THEME_COLOR_KEY);
        toobar.setBackgroundColor(themeColor == -1 ? ContextCompat.getColor(this, R.color.colorPrimary) : themeColor);
        //初始化toolbar离状态栏的高度
        toobar.setPadding(0, getStatusBarHight(), 0, 0);
    }

    private int getStatusBarHight() {//获取状态栏高度
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 初始化控件
     */
    private void initNavigationBar() {
        bottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)//设置BottomNavigationBar背景色
                .setMode(BottomNavigationBar.MODE_FIXED)                        //设置导航栏模式 EIXED为固定不变的,SHIFTING为移位的，点击控件可以移动的
                .setActiveColor(R.color.colorPrimary)                          //设置bottomBavigationBar字体颜色
                .setInActiveColor(R.color.colorFontGray)                       //设置未选中bottomBavigationBar字体颜色
                .addItem(new BottomNavigationItem(R.mipmap.navi_home_normal,  //设置选中图背景图片
                        R.string.bottom_navigation_home)                      //设置底部文字
                )    //设置未选中的条目的背景图
                .addItem(new BottomNavigationItem(R.mipmap.navi_soft_normal,
                        R.string.bottom_navigation_software))
                .addItem(new BottomNavigationItem(R.mipmap.navi_beauty_normal,
                        R.string.bottom_navigation_beauty))
                .addItem(new BottomNavigationItem(R.mipmap.navi_circle_normal,
                        R.string.bottom_navigation_circle))
                .addItem(new BottomNavigationItem(R.mipmap.navi_mine_normal,
                        R.string.bottom_navigation_mine))
                .setFirstSelectedPosition(0)//设置默认选中项
                .initialise();//初始化

        //结合fragment使用
        fragments = getFragments();
        setDefaultFragment();
        bottomNavigationBar.setTabSelectedListener(this);

    }

    /**
     * 设置默认的fragment
     */
    private void setDefaultFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_container, HomeFragment.newInstance())
                .commit();
    }

    /**
     * 创建fragment，添加到集合中
     */
    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance());
        fragments.add(SoftwareFragment.newInstance());
        fragments.add(BeautyFragment.newInstance());
        fragments.add(CircleFragment.newInstance());
        fragments.add(MineFragment.newInstance());
        return fragments;
    }

    @Override
    public void onTabSelected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);//根据位置获取当前fragment
                if (fragment.isAdded()) {                   //判断当前的fragment是否是添加的状态
                    ft.replace(R.id.fl_container, fragment); //替换到activity中
                } else {
                    ft.add(R.id.fl_container, fragment);      //否则添加到activity中
                }
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabUnselected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                ft.remove(fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onTabReselected(int position) {

    }

    /**
     * Double click the backpress exit app
     */
    private int backPressedCount = 0;//点击返回键的次数

    @Override
    public void onBackPressed() {
        switch (backPressedCount++) {
            case 0:
                Toast.makeText(this, getResources().getString(R.string.exit_application), Toast.LENGTH_SHORT).show();
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask(){
                    @Override
                    public void run() {
                        backPressedCount = 0;
                    }
                };
                timer.schedule(timerTask,2000);
                break;
            case 1:
                MApplication.getInstant().exitActivity();
                break;
        }
    }
}
