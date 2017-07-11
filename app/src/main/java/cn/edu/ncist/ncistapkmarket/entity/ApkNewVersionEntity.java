package cn.edu.ncist.ncistapkmarket.entity;

/**
 * Created by xxl on 2017/4/23.
 * apk新版本实体类
 */

public class ApkNewVersionEntity {

    private int versionCode;//版本号
    private String versionName;//版本名称
    private String versionDes;//版本描述
    private String downloadUrl;//下载地址

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionDes() {
        return versionDes;
    }

    public void setVersionDes(String versionDes) {
        this.versionDes = versionDes;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
