package cn.edu.ncist.ncistapkmarket.utils;

import android.content.Context;
import android.content.SharedPreferences;

import cn.edu.ncist.ncistapkmarket.interfaces.Constants;

/**
 * Created by xxl on 2017/4/26.
 */

public class SharedPreferencesUtils {

    private static SharedPreferences sharedPreferences;

    /**
     * 保存int值的SharedPre
     *
     * @param context
     * @param key
     * @param value
     */
    public static boolean putInt(Context context, String key, int value) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(Constants.THEME_COLORS, Context.MODE_PRIVATE);//ThemColors xml 文件没必要每次都创建，如为空则创建
        }
        return sharedPreferences.edit().putInt(key, value).commit();
    }

    /**
     * 获取int值得SharedPre
     * l
     *
     * @param context
     * @param key
     * @return
     */
    public static int getInt(Context context, String key) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(Constants.THEME_COLORS, Context.MODE_PRIVATE);
        }
        return sharedPreferences.getInt(key, -1);
    }
}
