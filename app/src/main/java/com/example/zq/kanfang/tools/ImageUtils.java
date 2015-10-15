package com.example.zq.kanfang.tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 对下载的图片进行的相关操作
 * Created by zq on 2015/9/22.
 */
public class ImageUtils {


    /**
     * 缓存路径
     */
    public static final String CACHDIR = Environment.getExternalStorageDirectory() + "/myimages";

    /**
     * 判断当前扩展卡是否挂载
     *
     * @return
     */
    public static boolean isMounted() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }


    /**
     * 根据图片路径获得图片名称
     * 该方法解析的图片地址最后以图片名称结尾：xxxxxx/xxxxx/ssss.jpg
     *
     * @param url
     * @return 图片名称
     */
    public static String getName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);

    }

    /**
     * 根据图片路径获得图片名称
     * 该方法解析的图片地址最后不以图片名称结尾：xxxxxx/xxxxx/ssss.jpg&xxxx=0xxx
     *
     * @param url
     * @param end
     * @return 图片名称
     */
    public static String getName(String url, int end) {
        return url.substring(url.lastIndexOf("/") + 1, end);
    }

    public static void saveImg(String url, byte[] bytes) throws IOException {

        /**
         * 当前扩展卡没有挂载，直接返回
         */
        if (!isMounted()) {
            return;
        }


        File dir = new File(CACHDIR);

        /**
         * 缓存路径不存在，则创建
         */
        if (!dir.exists()) dir.mkdirs();

        FileOutputStream fos = new FileOutputStream(new File(CACHDIR, getName(url)));
        fos.write(bytes);
        fos.close();

    }

    /**
     * 若扩展卡含有图片则获得
     *
     * @param url
     * @return
     */
    public static Bitmap getBitmap(String url) {

        if (!isMounted()) {
            return null;
        }
        File file = new File(CACHDIR, getName(url));
        if (file.exists()) {
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        }
        return null;
    }
}
