package cn.edu.ncist.ncistapkmarket.entity;

import java.util.List;

/**
 * Created by xxl on 2017/5/7.
 */

public class AppEntity {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 1521945
         * name : 欧朋浏览器
         * packageName : com.oupeng.mini.android
         * iconUrl : app/com.oupeng.mini.android/icon.jpg
         * stars : 3.5
         * size : 2376917
         * downloadUrl : app/com.oupeng.mini.android/com.oupeng.mini.android.apk
         * des : 欧朋浏览器7.8.8——最快的手机浏览器，没有之一，欢迎挑战。纤小的身体蕴藏着
         */

        private String id;
        private String name;
        private String packageName;
        private String iconUrl;
        private double stars;
        private int size;
        private String downloadUrl;
        private String des;

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

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public double getStars() {
            return stars;
        }

        public void setStars(double stars) {
            this.stars = stars;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }
    }
}
