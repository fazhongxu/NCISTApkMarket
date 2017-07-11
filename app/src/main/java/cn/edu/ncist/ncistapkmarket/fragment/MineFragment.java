package cn.edu.ncist.ncistapkmarket.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import cn.edu.ncist.ncistapkmarket.R;
import cn.edu.ncist.ncistapkmarket.base.BaseFragment;
import cn.edu.ncist.ncistapkmarket.sharesdk.onekeyshare.OnekeyShare;
import cn.edu.ncist.ncistapkmarket.ui.ThemeSwitchActivty;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by xxl on 2017/4/26.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {

    private static MineFragment mineFragment;

    @BindView(R.id.ll_theme_switch)
    LinearLayout llThemeSwitch;
    @BindView(R.id.ll_settings)
    LinearLayout llSettings;
    @BindView(R.id.ll_share)
    LinearLayout llShare;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        llThemeSwitch.setOnClickListener(this);
        llShare.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    public static Fragment newInstance() {
        if (mineFragment == null) {
            mineFragment = new MineFragment();
        }
        return mineFragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_theme_switch:
                //跳转到主题切换页面
                startActivity(new Intent(v.getContext(), ThemeSwitchActivty.class));
                break;
            case R.id.ll_share:
                showShare();
                break;
        }
    }

    private void showShare() {
        ShareSDK.initSDK(context);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("发现华科应用软件商店这个软件不错，快来下载使用吧！");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(context);
    }
}
