package cn.edu.ncist.ncistapkmarket.entity;

import java.util.List;

/**
 * Created by xxl on 2017/5/20.
 * 游戏实体类
 */

public class GameEntity {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 1642739
         * name : 捕鱼达人土豪金
         * packageName : org.cocos2dx.GoldenFishGame
         * iconUrl : app/org.cocos2dx.GoldenFishGame/icon.jpg
         * stars : 2.5
         * size : 9815944
         * downloadUrl : app/org.cocos2dx.GoldenFishGame/org.cocos2dx.GoldenFishGame.apk
         * des : 捕鱼达人土豪金是捕鱼达人原班团队（fishingjoy）继捕鱼达人2研发的一款精
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
