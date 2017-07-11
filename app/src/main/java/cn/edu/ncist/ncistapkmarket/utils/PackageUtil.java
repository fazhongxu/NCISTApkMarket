package cn.edu.ncist.ncistapkmarket.utils;

/**
 * Created by xxl on 2017/4/22.
 */

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 获取应用包信息
 */
public class PackageUtil {

    private static int versionCode;
    private static String versionName;

    /**
     * 获取当前应用版本号
     */
    public static int getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;//获取当前应用版本
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
    /**
     * 获取当前应用版本名称
     */
    public static String  getVersionName(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;//获取当前版本名称
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
