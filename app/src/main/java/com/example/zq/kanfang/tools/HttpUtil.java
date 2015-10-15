package com.example.zq.kanfang.tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zq on 2015/9/22.
 */
public class HttpUtil {


    /**
     * 根据指定的地址获取字节数组数据
     *
     * @param path 获取数据的url地址
     * @return
     */

    public static byte[] get(String path) {

        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);

            InputStream inputStream = null;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            if (connection.getResponseCode() == 200) {
                inputStream = connection.getInputStream();
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = inputStream.read(buffer)) != -1) {
                    bos.write(buffer, 0, len);
                }
            }

            //返回字节数组
            return bos.toByteArray();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }


}
