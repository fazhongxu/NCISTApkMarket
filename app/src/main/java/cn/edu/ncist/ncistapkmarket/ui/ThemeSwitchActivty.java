package cn.edu.ncist.ncistapkmarket.ui;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.ncist.ncistapkmarket.R;
import cn.edu.ncist.ncistapkmarket.adapter.ColorAdapter;
import cn.edu.ncist.ncistapkmarket.base.BaseActivity;
import cn.edu.ncist.ncistapkmarket.entity.ColorsEntity;
import cn.edu.ncist.ncistapkmarket.interfaces.Constants;
import cn.edu.ncist.ncistapkmarket.utils.SharedPreferencesUtils;

public class ThemeSwitchActivty extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_theme_switch)
    RecyclerView recyclerview;
    private ArrayList<ColorsEntity> colorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_switch_activty);
        ButterKnife.bind(this);
        initToolbar();
        initData();
    }
    /**
     * 初始化toolbar
     */
    private void initToolbar() {
        toolbar.setTitle(getString(R.string.theme_switch));//toolbar设置标题要在setSupportActionBar(toolbar) 之前设置，否则设置无效
        setSupportActionBar(toolbar);//设置支持actionBar，才能调用下面这行代码
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回键可用
        int themeColor = SharedPreferencesUtils.getInt(this, Constants.THEME_COLOR_KEY);
        toolbar.setBackgroundColor(themeColor == -1 ? ContextCompat.getColor(this, R.color.colorPrimary) : themeColor);
        //设置Toolbar距离顶部距离
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
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
        initColor();
        initRecyclerView();
    }

    /**
     * 初始化主题颜色
     */
    private void initColor() {
        colorList = new ArrayList<>();
        colorList.add(new ColorsEntity("水鸭绿", ContextCompat.getColor(this, R.color.colorPrimary)));
        colorList.add(new ColorsEntity("高级灰", ContextCompat.getColor(this, R.color.colorGray)));
        colorList.add(new ColorsEntity("幽默粉", ContextCompat.getColor(this, R.color.colorPink)));
        colorList.add(new ColorsEntity("珊瑚色", ContextCompat.getColor(this, R.color.coral)));
        colorList.add(new ColorsEntity("浅绿色", ContextCompat.getColor(this, R.color.aqua)));
        colorList.add(new ColorsEntity("黄褐色", ContextCompat.getColor(this, R.color.khaki)));
        colorList.add(new ColorsEntity("金色", ContextCompat.getColor(this, R.color.gold)));
        colorList.add(new ColorsEntity("天蓝亮色", ContextCompat.getColor(this, R.color.lightskyblue)));
        colorList.add(new ColorsEntity("暗青色", ContextCompat.getColor(this, R.color.darkcyan)));
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        final ColorAdapter colorAdapter = new ColorAdapter(colorList);
        recyclerview.setAdapter(colorAdapter);
        colorAdapter.setOnItemClickListner(new ColorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ColorsEntity colorsBean) {
                int color = colorsBean.getColor();
                int spColor = SharedPreferencesUtils.getInt(ThemeSwitchActivty.this, Constants.THEME_COLOR_KEY);
//                ObjectAnimator.ofArgb(mToolbar,"backgroundColor",spColor,color);//min api21以上
                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(toolbar, "backgroundColor",
                        spColor == -1 ? ContextCompat.getColor(ThemeSwitchActivty.this, R.color.colorPrimary) : spColor, color);
                objectAnimator.setDuration(500);
                objectAnimator.setEvaluator(new ArgbEvaluator());
                objectAnimator.start();
                

                colorsBean.setIsUsed(true);//设置被使用

                //保存主题色
                SharedPreferencesUtils.putInt(ThemeSwitchActivty.this, Constants.THEME_COLOR_KEY, color);
            }
        });
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
