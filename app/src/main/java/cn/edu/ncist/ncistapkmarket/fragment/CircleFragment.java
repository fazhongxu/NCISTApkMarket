package cn.edu.ncist.ncistapkmarket.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import cn.edu.ncist.ncistapkmarket.R;
import cn.edu.ncist.ncistapkmarket.base.BaseFragment;

/**
 * Created by xxl on 2017/4/26.
 * 圈子 可以浏览帖子和发帖 管理员可以删帖
 */

public class CircleFragment extends BaseFragment {


    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.pb_load_progress)
    ProgressBar loadProgress;
    private static CircleFragment circleFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_circle;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        initWebWiew();
    }

    /**
     * 初始化webview
     */
    private void initWebWiew() {
        webView.getSettings().setJavaScriptEnabled(true);//设置支持javascript
        webView.getSettings().setUseWideViewPort(true);//设置自适应屏幕大小
        webView.getSettings().setLoadWithOverviewMode(true);

        webView.getSettings().setSupportZoom(true);//设置只是缩放
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);//不显示控制缩放的按钮


        webView.loadUrl(getString(R.string.forum_api));
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient(){//设置加载进度条显示
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    loadProgress.setVisibility(View.GONE);
                }else {
                    loadProgress.setVisibility(View.VISIBLE);
                    loadProgress.setProgress(newProgress);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    public static Fragment newInstance() {
        if (circleFragment == null) {
            circleFragment = new CircleFragment();
        }
        return circleFragment;
    }
    //back建控制网页后退

}
