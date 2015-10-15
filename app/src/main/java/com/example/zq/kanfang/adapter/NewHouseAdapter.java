package com.example.zq.kanfang.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zq.kanfang.R;
import com.example.zq.kanfang.bean.NewHouseInfo;
import com.example.zq.kanfang.tools.NUtil;

import java.util.List;

/**
 * Created by zq on 2015/9/28.
 */
public class NewHouseAdapter extends BaseAdapter {

    private Context context;
    private List<NewHouseInfo> datas;

    public NewHouseAdapter(Context context, List<NewHouseInfo> datas) {
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


        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_new_house, null);
            holder.img_newhouse = (ImageView) convertView.findViewById(R.id.img_newhouse);
            holder.tv_newhouse_title = (TextView) convertView.findViewById(R.id.tv_newhouse_title);
            holder.tv_region = (TextView) convertView.findViewById(R.id.tv_region);
            holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            holder.ll_mark = (LinearLayout) convertView.findViewById(R.id.ll_mark);
            convertView.setTag(holder);
        }

        ViewHolder holder1 = (ViewHolder) convertView.getTag();
        final NewHouseInfo newHouseInfo = datas.get(position);

        holder1.img_newhouse.setTag(newHouseInfo.getFcover());

        holder1.tv_newhouse_title.setText(newHouseInfo.getFname());
        holder1.tv_region.setText(newHouseInfo.getRegion());

        Log.i("info", newHouseInfo.getFpricedisplaystr());

        Log.i("info", newHouseInfo.getFcover());

        holder1.tv_price.setText(newHouseInfo.getFpricedisplaystr());

        holder1.tv_address.setText(newHouseInfo.getFaddress());

        holder1.ll_mark.removeAllViews();
        LinearLayout linearLayout = holder1.ll_mark;
        int[] colors = new int[]{Color.RED, Color.YELLOW, Color.BLUE};

        for (int i = 0; i < newHouseInfo.getBookmarks().size(); i++) {
            TextView tv = new TextView(context);
            tv.setText(newHouseInfo.getBookmarks().get(i));
            tv.setBackgroundColor(colors[i]);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 30);
            params.setMargins(10, 0, 10, 0);
            tv.setPadding(5, 0, 5, 0);
            tv.setLayoutParams(params);
            linearLayout.addView(tv);
        }

        NUtil.get(NUtil.TYPE_IMG, newHouseInfo.getFcover(), new NUtil.CallBack() {
            @Override
            public void response(String url, byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                ImageView imageView = (ImageView) parent.findViewWithTag(newHouseInfo.getFcover());
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        });

        return convertView;
    }


    class ViewHolder {
        ImageView img_newhouse;
        TextView tv_newhouse_title, tv_region, tv_price, tv_address;
        LinearLayout ll_mark;
    }
}
