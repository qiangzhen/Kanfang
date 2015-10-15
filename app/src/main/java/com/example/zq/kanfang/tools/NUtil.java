package com.example.zq.kanfang.tools;

/**
 * Created by zq on 2015/9/22.
 */

import android.os.Handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 提供下载图片和文本的工具方法
 */
public class NUtil {


    /**
     * 文本类型
     */
    public static final int TYPE_TEXT = 0;

    /**
     * 图片类型
     */
    public static final int TYPE_IMG = 1;

    public interface CallBack {

        /**
         * 回掉接口，用于处理最后下载的数据
         *
         * @param type  下载数据的类型，文本或者图片
         * @param url
         * @param bytes
         */
        public void response(String url, byte[] bytes);
    }


    private static Handler handler = new Handler();
    private static ExecutorService service = Executors.newFixedThreadPool(5);


    public static void get(final int type, final String url, final CallBack callBack) {

        service.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                    InputStream inputStream = connection.getInputStream();
                    byte[] buffer = new byte[10 * 1024];
                    int len = -1;

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();

                    if (connection.getResponseCode() == 200) {
                        while ((len = inputStream.read(buffer)) != -1) {
                            bos.write(buffer, 0, len);
                        }
                    }

                    final byte[] bytes = bos.toByteArray();
//                    if (type == TYPE_IMG) {
//                        ImageUtils.saveImg(url, bytes);
//                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callBack.response(url, bytes);
                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
