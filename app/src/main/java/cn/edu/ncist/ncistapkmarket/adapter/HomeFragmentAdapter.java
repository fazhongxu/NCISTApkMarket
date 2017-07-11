package cn.edu.ncist.ncistapkmarket.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.ncist.ncistapkmarket.R;
import cn.edu.ncist.ncistapkmarket.entity.HomeAppDataEntity;
import cn.edu.ncist.ncistapkmarket.interfaces.ItemClickListener;
import cn.edu.ncist.ncistapkmarket.ui.AppDetailActivity;

/**
 * Created by xxl on 2017/4/27.
 * 首页数据RecyclerViewAdapter
 */

public class HomeFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int MOVE_PAGER = 100;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            switch (msg.what) {
                case MOVE_PAGER:

                    break;
            }
        }
    };

    private Context context;
    private List<HomeAppDataEntity.ListBean> list = new ArrayList<>();
    private List<String> pictrues;
    private HomeViewPagerAdapter homeViewPagerAdapter;

    public HomeFragmentAdapter(Context context, List<HomeAppDataEntity.ListBean> list, List<String> pictrues) {
        this.context = context;
        this.list = list;
        this.pictrues = pictrues;
    }

    /**
     * 条目类型枚举
     */
    private enum ITEM_TYPE {
        ITEM_TYPE_HEADER,
        ITEM_TYPE_CONTENT,
        ITEM_TYPE_FOOTER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        if (viewType == ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()) {
            return new HeaderViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_header, parent, false));
        } else if (viewType == ITEM_TYPE.ITEM_TYPE_CONTENT.ordinal()) {
            return new HomeDataViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home_data, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            if (position == 0) {
                if (homeViewPagerAdapter == null) {
                    homeViewPagerAdapter = new HomeViewPagerAdapter(context, pictrues);
                    ((HeaderViewHolder) holder).viewPager.setAdapter(homeViewPagerAdapter);
                    for (int i = 0; i < pictrues.size(); i++) {
                        LinearLayout llDots = ((HeaderViewHolder) holder).LLDots;
                        View view = new View(context);//动态创建小点
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(10, 10);
                        layoutParams.setMargins(10, 0, 10, 0);
                        view.setLayoutParams(layoutParams);
                        view.setBackgroundResource(R.drawable.shape_home_viewpager_dots);

                        llDots.addView(view);
                    }
                } else {
                    ((HeaderViewHolder) holder).viewPager.setAdapter(homeViewPagerAdapter);
                }
            }
        } else if (holder instanceof HomeDataViewHolder) {
            final HomeAppDataEntity.ListBean listBean = list.get(position - 1);//-1防止索引越界
            ((HomeDataViewHolder) holder).tvAppName.setText(listBean.getName());
            String des = listBean.getDes();//获取应用描述
            String subDes = null;
            if (des != null && des.length() > 10) {//截取应用描述
                subDes = des.substring(0, 10);
            } else {
                subDes = des;
            }
            ((HomeDataViewHolder) holder).tvAppDes.setText(subDes);
            String iconUrl = listBean.getIconUrl();
            StringBuffer sb = new StringBuffer(context.getString(R.string.app_api)).append(iconUrl);//拼接图片地址
            if (iconUrl != null) {
                Picasso.with(context)
                        .load(sb.toString())
                        .into(((HomeDataViewHolder) holder)
                                .ivAppIcon);
            }
            final double stars = listBean.getStars();
            ((HomeDataViewHolder) holder).rabLikeNumber.setRating(((float) stars));//设置星星评分
            //获取软件大小
            int size = listBean.getSize();
            String fileSize = android.text.format.Formatter.formatFileSize(context, size);//把kb转换为MB
            ((HomeDataViewHolder) holder).tvAppSize.setText(fileSize);
            ((HomeDataViewHolder) holder).rlDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AppDetailActivity.class);
                    intent.putExtra("packageName", listBean.getPackageName());
                    context.startActivity(intent);
                }
            });
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position == ITEM_TYPE.ITEM_TYPE_HEADER.ordinal()) {
            return ITEM_TYPE.ITEM_TYPE_HEADER.ordinal();
        } else if (position >= ITEM_TYPE.ITEM_TYPE_CONTENT.ordinal() && position < list.size() + 1) {
            return ITEM_TYPE.ITEM_TYPE_CONTENT.ordinal();
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * RecyclerView 头部 holder
     */
    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.viewPager)
        ViewPager viewPager;
        @BindView(R.id.ll_dots)
        LinearLayout LLDots;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 首页内容数据viewHolder
     */
    static class HomeDataViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_app_icon)
        ImageView ivAppIcon;
        @BindView(R.id.tv_app_name)
        TextView tvAppName;
        @BindView(R.id.rab_like_number)
        RatingBar rabLikeNumber;
        @BindView(R.id.tv_app_des)
        TextView tvAppDes;
        @BindView(R.id.ll_app_detail)
        LinearLayout llAppDetail;
        @BindView(R.id.tv_app_size)
        TextView tvAppSize;
        @BindView(R.id.tv_install)
        TextView tvInstall;
        @BindView(R.id.rl_detail)
        RelativeLayout rlDetail;

        public HomeDataViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
