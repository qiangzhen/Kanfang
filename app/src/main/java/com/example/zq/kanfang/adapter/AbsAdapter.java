package com.example.zq.kanfang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.zq.kanfang.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zq on 2015/9/29.
 */
public abstract class AbsAdapter<T> extends BaseAdapter {

    private Context context;
    private int layoutResId;
    private List<T> datas;

    public AbsAdapter(Context context, int layoutResId, List<T> datas) {
        this.context = context;
        this.layoutResId = layoutResId;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutResId, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        bindView(viewHolder, datas.get(position));
        return convertView;
    }


    public abstract void bindView(ViewHolder viewHolder, T data);

    public static class ViewHolder {
        private Map<Integer, View> cacheViews;
        private View itemView;

        public ViewHolder(View itemView) {
            this.cacheViews = new HashMap<Integer, View>();
            this.itemView = itemView;
        }


        public View getView(int id) {
            View v = cacheViews.get(id);
            if (v == null) {
                v = itemView.findViewById(id);
                if (v != null) {
                    cacheViews.put(id, v);
                }
            }
            return v;
        }
    }
}
