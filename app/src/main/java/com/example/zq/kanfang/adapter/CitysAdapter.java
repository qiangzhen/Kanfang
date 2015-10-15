package com.example.zq.kanfang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zq.kanfang.R;
import com.example.zq.kanfang.bean.City;

import java.util.List;

/**
 * Created by zq on 2015/9/24.
 */
public class CitysAdapter extends BaseAdapter {

    private Context context;
    private List<City> datas;


    public CitysAdapter(Context context, List<City> datas) {
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
    public int getViewTypeCount() {
        return City.getTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        return datas.get(position).getType();
    }

    @Override
    public boolean isEnabled(int position) {
        if (getItemViewType(position) == City.TYPE_LABEL) {
            return false;
        }

        return super.isEnabled(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (getItemViewType(position) == City.TYPE_LABEL) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_data_label, null);
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_data_city, null);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.titleId);
        textView.setText(datas.get(position).getName());

        return convertView;
    }
}
