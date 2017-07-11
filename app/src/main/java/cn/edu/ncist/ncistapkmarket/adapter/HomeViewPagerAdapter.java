package cn.edu.ncist.ncistapkmarket.adapter;

import android.content.Context;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.edu.ncist.ncistapkmarket.R;

/**
 * Created by xxl on 2017/4/30.
 * 首页ViewPager的适配器
 */

public class HomeViewPagerAdapter extends PagerAdapter {
    private List<String> imageUrl = new ArrayList<>();
    private List<ImageView> imageViews = new ArrayList<>();
    private Context context;

    public HomeViewPagerAdapter(Context context, List<String> imageUrl) {
        this.imageUrl = imageUrl;
        this.context = context;
        if (this.imageUrl != null && this.imageUrl.size() > 0) {//根据图片张数动态创建图片
            for (int i = 0; i < this.imageUrl.size(); i++) {
                ImageView image = new ImageView(context);
                imageViews.add(image);
            }
        }
    }

    @Override
    public int getCount() {
        return imageUrl.size() == 0 ? 0 : imageUrl.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = imageViews.get(position);
        container.addView(imageView);
        if (imageUrl != null) {
            Picasso.with(context)
                    .load(context.getString(R.string.app_api) + imageUrl.get(position))
                    .into(imageView);
        }
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
