package cn.edu.ncist.ncistapkmarket.entity;

import java.util.List;

/**
 * Created by xxl on 2017/4/29.
 * 首页APP数据实体类
 */

public class HomeAppDataEntity {
    public List<ListBean> list;
    public List<String> picture;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }

    public static class ListBean {
        /**
         * des : 产品介绍：有缘是时下最受大众单身男女亲睐的婚恋交友软件。有缘网专注于通过轻松、
         * downloadUrl : app/com.youyuan.yyhl/com.youyuan.yyhl.apk
         * iconUrl : app/com.youyuan.yyhl/icon.jpg
         * id : 1525490
         * name : 有缘网
         * packageName : com.youyuan.yyhl
         * size : 3876203
         * stars : 4
         */

        private String des;
        private String downloadUrl;
        private String iconUrl;
        private String id;
        private String name;
        private String packageName;
        private int size;
        private double stars;

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public double getStars() {
            return stars;
        }

        public void setStars(double stars) {
            this.stars = stars;
        }
    }
}
