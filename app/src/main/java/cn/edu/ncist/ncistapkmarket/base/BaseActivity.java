package cn.edu.ncist.ncistapkmarket.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import cn.edu.ncist.ncistapkmarket.MApplication;
import cn.edu.ncist.ncistapkmarket.R;
import cn.edu.ncist.ncistapkmarket.interfaces.Constants;
import cn.edu.ncist.ncistapkmarket.utils.SharedPreferencesUtils;

/**
 * Created by xxl on 2017/4/22.
 */

/**
 * activity的基类，用于处理所有activity都有的共同特点
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initStateBar();
        MApplication.getInstant().addActivity(this);
    }
    /**
     * 初始化状态栏
     */
    private void initStateBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//21 5.0以上设置为全透明状态
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//19 4.4半透明
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | layoutParams.flags);
        }
    }
}
