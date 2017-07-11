package cn.edu.ncist.ncistapkmarket.adapter;

import android.content.Context;
import android.content.Intent;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.ncist.ncistapkmarket.R;
import cn.edu.ncist.ncistapkmarket.entity.AppEntity;
import cn.edu.ncist.ncistapkmarket.entity.GameEntity;
import cn.edu.ncist.ncistapkmarket.ui.AppDetailActivity;

/**
 * Created by xxl on 2017/5/7.
 */

public class GameFragmenAdapter extends RecyclerView.Adapter<GameFragmenAdapter.AppViewHolder> {

    private List<GameEntity.ListBean> list = new ArrayList<>();
    private Context context;

    public GameFragmenAdapter(Context context) {
        this.context = context;
    }

    public void fillToData(List<GameEntity.ListBean> list) {
        this.list = list;
        this.notifyDataSetChanged();
        Log.e("size", list.size() + "");
    }

    @Override
    public AppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppViewHolder(LayoutInflater.from(context).inflate(R.layout.item_game_data, parent, false));
    }

    @Override
    public void onBindViewHolder(AppViewHolder holder, int position) {
        final GameEntity.ListBean listBean = list.get(position);
        holder.tvAppName.setText(listBean.getName());
        String des = listBean.getDes();//获取应用描述
        String subDes = null;
        if (des != null && des.length() > 10) {//截取应用描述
            subDes = des.substring(0, 10);
        } else {
            subDes = des;
        }
        holder.tvAppDes.setText(subDes);
        String iconUrl = listBean.getIconUrl();
        StringBuffer sb = new StringBuffer(context.getString(R.string.app_api)).append(iconUrl);//拼接图片地址
        if (iconUrl != null) {
            Picasso.with(context)
                    .load(sb.toString())
                    .into(holder
                            .ivAppIcon);
        }
        final double stars = listBean.getStars();
        holder.rabLikeNumber.setRating(((float) stars));//设置星星评分
        //获取软件大小
        int size = listBean.getSize();
//        String fileSize = android.text.format.Formatter.formatFileSize(context, size);//把kb转换为MB/
//        holder.tvAppSize.setText(fileSize);
        holder.rlDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AppDetailActivity.class);
                intent.putExtra("packageName", listBean.getPackageName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size() == 0 ? 0 : list.size();
    }

    static class AppViewHolder extends RecyclerView.ViewHolder {
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
//        @BindView(R.id.tv_install)
//        TextView tvInstall;
        @BindView(R.id.rl_detail)
        RelativeLayout rlDetail;

        public AppViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
//            tvInstall.setVisibility(View.GONE);
        }
    }
}
