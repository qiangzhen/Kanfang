package com.example.zq.kanfang.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zq on 2015/9/28.
 */
public class VolleyUtils {

    public static RequestQueue mQueue;
    public static ImageLoader mLoader;

    private static LruCache<String, Bitmap> lruCache;//强引用缓存，一级缓存
    private static Map<String, SoftReference<Bitmap>> softCache;//软引用缓存，二级缓存

    public static RequestQueue getmQueue(Context context) {
        if (mQueue == null) {
            mQueue = Volley.newRequestQueue(context);
        }
        return mQueue;
    }

    public static ImageLoader getmLoader(Context context) {


        if (mLoader == null) {
            //实例化二级缓存
            softCache = new HashMap<String, SoftReference<Bitmap>>();

            //实例化一级缓存
            lruCache = new LruCache<String, Bitmap>(2 * 1024 * 1024) {

                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }

                @Override
                protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {

                    if (evicted) {
                        //讲移除的成员放到二级缓存中
                        softCache.put(key, new SoftReference<Bitmap>(oldValue));
                    }
                    super.entryRemoved(evicted, key, oldValue, newValue);
                }
            };

            mLoader = new ImageLoader(getmQueue(context), new ImageLoader.ImageCache() {
                @Override
                public Bitmap getBitmap(String url) {

                    Bitmap bitmap = lruCache.get(url);

                    if (bitmap == null) {
                        SoftReference<Bitmap> softReference = softCache.get(url);
                        if (softReference != null) {
                            bitmap = softReference.get();
                            if (bitmap != null) {
                                lruCache.put(url, bitmap);

                                softCache.remove(softReference);
                            }
                        } else {
                            bitmap = ImageUtils.getImg(url);
                            if (bitmap != null) {
                                lruCache.put(url, bitmap);
                            }
                        }
                    }

                    return bitmap;
                }

                @Override
                public void putBitmap(String url, Bitmap bitmap) {

                    //将图片放到一级缓存中
                    lruCache.put(url, bitmap);
                    try {
                        ImageUtils.saveImg(url, bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        return mLoader;
    }

}
