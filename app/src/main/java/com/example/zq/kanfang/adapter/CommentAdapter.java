package com.example.zq.kanfang.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zq.kanfang.R;
import com.example.zq.kanfang.bean.Comment;
import com.example.zq.kanfang.tools.NUtil;

import java.util.List;

/**
 * Created by zq on 2015/9/28.
 */
public class CommentAdapter extends BaseAdapter {

    private Context context;
    private List<Comment> datas;

    public CommentAdapter(Context context, List<Comment> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        int count = 0;
        if (datas != null) {
            count = datas.size();
        }
        return count;
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

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment, null);
            holder = new ViewHolder();

            holder.img_user = (ImageView) convertView.findViewById(R.id.img_user);
            holder.tv_nick = (TextView) convertView.findViewById(R.id.tv_nick);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_comment_content = (TextView) convertView.findViewById(R.id.tv_comment_content);
            convertView.setTag(holder);

        }
        final Comment comment = datas.get(position);


        holder = (ViewHolder) convertView.getTag();
        holder.img_user.setImageResource(R.mipmap.ic_launcher);
        holder.img_user.setTag(comment.getHead());


        holder.tv_nick.setText(comment.getNick());
        holder.tv_time.setText(comment.getRegion() + " " + comment.getTime());
        holder.tv_comment_content.setText(comment.getContent());


        NUtil.get(NUtil.TYPE_IMG, comment.getHead(), new NUtil.CallBack() {
            @Override
            public void response(String url, byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                ImageView imageView = (ImageView) parent.findViewWithTag(comment.getHead());
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);

                }
            }
        });

        return convertView;
    }


    class ViewHolder {
        ImageView img_user;
        TextView tv_nick, tv_time, tv_comment_content;

    }
}
