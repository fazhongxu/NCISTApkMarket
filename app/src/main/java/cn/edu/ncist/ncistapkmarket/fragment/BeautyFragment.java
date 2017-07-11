package cn.edu.ncist.ncistapkmarket.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import cn.edu.ncist.ncistapkmarket.R;
import cn.edu.ncist.ncistapkmarket.base.BaseFragment;

/**
 * Created by xxl on 2017/4/26.
 */

public class BeautyFragment extends BaseFragment {


    @BindView(R.id.tv_text)
    TextView tvText;
    private static BeautyFragment beautyFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_beauty;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        tvText.setText("排行");
    }

    @Override
    protected void initData() {

    }

    public static Fragment newInstance() {
        if (beautyFragment == null) {
            beautyFragment = new BeautyFragment();
        }
        return beautyFragment;
    }
}
