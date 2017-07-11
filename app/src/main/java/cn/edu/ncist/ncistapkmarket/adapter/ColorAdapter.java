package cn.edu.ncist.ncistapkmarket.adapter;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.ncist.ncistapkmarket.R;
import cn.edu.ncist.ncistapkmarket.entity.ColorsEntity;

/**
 * Created by xxl on 2017/4/26.
 */

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorViewHolder> {
    List<ColorsEntity> colorList = new ArrayList<>();

    public ColorAdapter(List<ColorsEntity> colorList) {
        this.colorList = colorList;
    }

    @Override
    public ColorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ColorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color, parent, false));
    }

    @Override
    public void onBindViewHolder(ColorViewHolder holder, int position) {
        final ColorsEntity colors = colorList.get(position);
        GradientDrawable background = (GradientDrawable) holder.view.getBackground();//view的background 是shape ，这里用GradientDrawable
        background.setColor(colors.getColor());
        holder.tvColorDes.setText(colors.getDes());
        //如果接口部位空，掉用接口里面声明的方法

        if (onItemClickListener != null) {
            holder.rlColor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(colors);
                }
            });
        }
        /*if (colors.getIsUsed()) {
            holder.colorUsed.setVisibility(View.VISIBLE);
        }else {
            holder.colorUsed.setVisibility(View.GONE);
        }*/
    }

    @Override
    public int getItemCount() {
        return colorList.size() == 0 ? 0 : colorList.size();
    }

    class ColorViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView tvColorDes;

        private RelativeLayout rlColor;
        private final ImageView colorUsed;

        public ColorViewHolder(View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.view_item_color);
            tvColorDes = (TextView) itemView.findViewById(R.id.tv_desc);
            rlColor = (RelativeLayout) itemView.findViewById(R.id.rl_color);
            colorUsed = (ImageView) itemView.findViewById(R.id.iv_color_used);
        }
    }

    private OnItemClickListener onItemClickListener;

    /**
     * 提供对外调用的方法，调用条目点击事件
     */
    public void setOnItemClickListner(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 条目点击接口
     */
    public interface OnItemClickListener {
        void onItemClick(ColorsEntity colorsBean);
    }

}

