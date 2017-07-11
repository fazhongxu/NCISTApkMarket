package cn.edu.ncist.ncistapkmarket.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by xxl on 2017/4/23.
 * 文件下载工具类
 */

public class FileDownLoadUtil {
    private OnDownloadFinishLisener onDownloadFinishLisener;

    /**
     * 对外提供方法，监听是否下载完成的操作
     * @param onDownloadFinishLisener
     */
    public void setOnDownloadFinishLisener (OnDownloadFinishLisener onDownloadFinishLisener) {
        this.onDownloadFinishLisener = onDownloadFinishLisener;
    }
    private FileOutputStream fos;
    /**
     * 通过okhttp 网络下载文件
     *
     * @param context
     * @param body
     */
    public static void downLoadFile(final Context context, final ResponseBody body) {
        new Thread() {
            @Override
            public void run() {
                FileOutputStream fos = null;
                BufferedInputStream bis = null;
                InputStream is = null;
                try {
                    is = body.byteStream();
                    File file = new File(Environment.getExternalStorageDirectory(), "ncistapkmarket.apk");
                    fos = new FileOutputStream(file);
                    bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        fos.flush();
                    }
                    // TODO: 2017/5/29
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setAction("android.intent.action.VIEW");
                    intent.addCategory("android.intent.category.DEFAULT");
                    String type = "application/vnd.android.package-archive";
                    Uri data = Uri.parse("file:///" + file);
                    intent.setDataAndType(data, type);
                    context.startActivity(intent);

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (fos != null)
                            fos.close();
                        if (bis != null)
                            bis.close();
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    /**
     * 定义一个回调接口，当下载完成之后调用
     */
    public interface OnDownloadFinishLisener{
        void onFinish(boolean finish);
    }
}
