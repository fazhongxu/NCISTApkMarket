package cn.edu.ncist.ncistapkmarket.entity;

/**
 * Created by xxl on 2017/4/27.
 * 颜色实体类
 */

public class ColorsEntity {
    private String des; //颜色描述
    private int color;  //颜色值
    private boolean isUsed;//是否被使用

    public ColorsEntity(String des, int color) {
        this.des = des;
        this.color = color;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }
}
