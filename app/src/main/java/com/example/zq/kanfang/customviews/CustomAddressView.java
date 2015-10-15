package com.example.zq.kanfang.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zq.kanfang.R;
import com.example.zq.kanfang.tools.AUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zq on 2015/9/24.
 */
public class CustomAddressView extends FrameLayout implements AUtils.Callback {


    public static final String KEY_TITLE = "title";
    public static final String KEY_URL = "url";


    private ViewPager vPager;
    private TextView titleTv;
    private LinearLayout navLayout;

    private List<ImageView> imgViews; //ViewPager中显示的所有图片控件
    private List<Map<String, String>> datas; //存储广告图片的数据源

    private Handler mHandler = new Handler();

    public CustomAddressView(Context context, ViewPager vPager, TextView titleTv, LinearLayout navLayout) {

        super(context);

        this.vPager = vPager;
        this.titleTv = titleTv;
        this.navLayout = navLayout;


    }


    public CustomAddressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomAddressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setDatas(final List<Map<String, String>> datas) {
        this.datas = datas;
        titleTv.setText(datas.get(0).get(KEY_TITLE)); //默认显示第一个广告标题


        createViews();
        vPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                titleTv.setText(datas.get(position).get(KEY_TITLE));
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void createViews() {
        // TODO 根据数据源创建ViewPager中显示的UI
        imgViews = new ArrayList<ImageView>();
        ImageView imgView = null;
        for (Map<String, String> map : datas) {
            imgView = new ImageView(getContext());
            imgView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgView.setTag(map.get(KEY_URL));


            imgViews.add(imgView);
        }
        //设置ViewPager的适配器
        vPager.setAdapter(new ImageAdapter());

        loadImgs();

    }

    private void loadImgs() {
        // TODO 加载网络图片
        for (Map<String, String> map : datas) {
            AUtils.get(map.get(KEY_URL), this);
        }

        new PlayImgThread().start();//开始播放
    }


    @Override
    public void response(String url, byte[] bytes) {

        // TODO 网络下载完成的回调方法
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        for (ImageView imgView : imgViews) {
            if (imgView.getTag().equals(url)) {

                imgView.setImageBitmap(bitmap);
            }
        }
    }


    class ImageAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            // TODO Auto-generated method stub

            return imgViews.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imgViews.get(position));
            return imgViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imgViews.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }
    }

    private int curPosition = 0; //当前ViewPager页面的位置

    class PlayImgThread extends Thread {
        @Override
        public void run() {
            try {
                while (CustomAddressView.this != null) {

                    if (curPosition == imgViews.size()) {
                        curPosition = 0;
                    }

                    Thread.sleep(3000);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() { //在主线程中执行的

                            if (curPosition == imgViews.size()) {
                                curPosition = 0;
                            }
                            titleTv.setText(datas.get(curPosition).get(KEY_TITLE));

                            if (curPosition == 0)
                                vPager.setCurrentItem(curPosition++, false);//false不带有动画效果地切换
                            else
                                vPager.setCurrentItem(curPosition++);

                        }
                    });

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
