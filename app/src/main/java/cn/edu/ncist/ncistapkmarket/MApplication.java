package cn.edu.ncist.ncistapkmarket;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by xxl on 2017/4/22.
 */

/**
 * Application,用于初始化环境，获取全局上下文,退出整个应用等操作
 */
public class MApplication extends Application {

    private static MApplication application;
    private static List<Activity> activityList;
    public Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();//获取全局上下文

        //init ShareSdk
        ShareSDK.initSDK(this,"1e08d2e64ddd7");
    }

    /**
     * 单例设计模式
     */
    public static MApplication getInstant() {
        if (application == null) {
            application = new MApplication();
        }
        return application;
    }

    /**
     * 注册activity，把activity添加到集合中，方便退出，防止内存泄漏
     */
    public void addActivity(Activity activity) {
        activityList = new ArrayList<>();
        activityList.add(activity);
    }
    /**
     * 退出所有activity
     */
    public void exitActivity () {
        for (Activity activity : activityList) {
            activity.finish();
        }
        activityList.clear();
        System.exit(0);//退出JVM虚拟机，防止应用程序退出之后虚拟机还在运行，导致资源浪费
    }
}
