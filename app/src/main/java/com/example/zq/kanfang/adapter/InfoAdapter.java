package com.example.zq.kanfang.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zq.kanfang.R;
import com.example.zq.kanfang.bean.Info;
import com.example.zq.kanfang.tools.NUtil;

import java.util.List;


/**
 * Created by zq on 2015/9/25.
 */
public class InfoAdapter extends BaseAdapter {

    private Context context;
    private List<Info> datas;


    public InfoAdapter(Context context, List<Info> datas) {
        this.context = context;
        this.datas = datas;


    }

    @Override
    public int getCount() {

        return datas.size();
    }

    @Override
    public Object getItem(int position) {

        return datas.get(position);
    }


    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        Log.i("info", "=====");
        TextView title;
        TextView summary;
        TextView comment;
        final ImageView imageView;


        Info info = datas.get(position);

        if (info.getType().equals("0")) {
            convertView = LayoutInflater.from(context).inflate(R.layout.info_item1, null);

            title = (TextView) convertView.findViewById(R.id.tv_title);
            summary = (TextView) convertView.findViewById(R.id.tv_summary);
            comment = (TextView) convertView.findViewById(R.id.tv_comment);
            imageView = (ImageView) convertView.findViewById(R.id.img_info);


            title.setText(info.getTitle());
            summary.setText(info.getSummary());
            if (!info.getCommentcount().equals("0")) {
                comment.setText(info.getCommentcount() + "评");
            } else {
                comment.setText("");

            }

            Log.i("info", info.getTitle());

            Log.i("info", info.getSummary());

            final String imgUrl = info.getGroupthumbnail();
            //  imageView.setImageResource(R.mipmap.ic_launcher);


            imageView.setTag(imgUrl);


            NUtil.get(NUtil.TYPE_IMG, imgUrl, new NUtil.CallBack() {
                @Override
                public void response(String url, byte[] bytes) {

                    Log.i("info===", imgUrl);

                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    ImageView imageView1 = (ImageView) parent.findViewWithTag(imgUrl);
                    if (imageView1 != null) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            });
            return convertView;

        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.info_item2, null);

            title = (TextView) convertView.findViewById(R.id.tv_title);
            imageView = (ImageView) convertView.findViewById(R.id.img_info);
            title.setText(info.getTitle());

            String url = info.getGroupthumbnail();
            imageView.setImageResource(R.mipmap.ic_launcher);
            imageView.setTag(url);

            NUtil.get(NUtil.TYPE_IMG, url, new NUtil.CallBack() {
                @Override
                public void response(String url, byte[] bytes) {

                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    ImageView imageView1 = (ImageView) parent.findViewWithTag(url);
                    if (imageView1 != null) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            });
            return convertView;
        }


    }
}
