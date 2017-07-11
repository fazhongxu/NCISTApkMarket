/**
 * 创建日期:2016年7月22日下午2:57:26
 * 作者:Administrator
 * 描述:TODO
 */
package cn.edu.ncist.ncistapkmarket.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/***
 *apk安装
 */
public class ApkUtil {


    public static void install(Activity activity, String target) {
        // Intent:开启服务或者打开页面
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        String type = "application/vnd.android.package-archive";
        Uri data = Uri.parse("file:///" + target);
        intent.setDataAndType(data, type);
        activity.startActivity(intent);
    }

}
