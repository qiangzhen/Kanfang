package com.example.zq.kanfang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zq.kanfang.R;
import com.example.zq.kanfang.adapter.CommentAdapter;
import com.example.zq.kanfang.bean.Comment;
import com.example.zq.kanfang.bean.InfoDetails;
import com.example.zq.kanfang.config.Config;
import com.example.zq.kanfang.tools.NUtil;
import com.example.zq.kanfang.tools.ParseTool;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zq on 2015/9/26.
 */
public class CommentFragment extends Fragment {

    private String infoCommentId;
    private String infoId;


    private CommentAdapter commentAdapter;
    private List<Comment> comments;

    private View view;
    private ListView listView;
    private View view_comment_top;


    private TextView tv_comment_title;
    private TextView tv_comment_sourcrAndTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        infoCommentId = getArguments().getString("commentid");
        infoId = getArguments().getString("infoid");

        Log.e("id", infoCommentId);

        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_comment, null);
        view_comment_top = LayoutInflater.from(getActivity()).inflate(R.layout.view_comment_top, null);
        listView = (ListView) view.findViewById(R.id.lv_comment);


        tv_comment_title = (TextView) view_comment_top.findViewById(R.id.tv_comment_title);
        tv_comment_sourcrAndTitle = (TextView) view_comment_top.findViewById(R.id.tv_comment_sourcrAndTitle);


        comments = new ArrayList<Comment>();
        initDatas();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return view;
    }


    private void initDatas() {


        Log.e("err", "loading");

        NUtil.get(NUtil.TYPE_TEXT, String.format(Config.NEWS_DETAIL, infoId), new NUtil.CallBack() {
            @Override
            public void response(String url, byte[] bytes) {
                try {
                    String jsonStr = new String(bytes, "utf-8");
                    InfoDetails infoDetails = ParseTool.getInfoDetails(jsonStr);

                    tv_comment_title.setText(infoDetails.getTitle());
                    tv_comment_sourcrAndTitle.setText(infoDetails.getSource() + "  " + infoDetails.getTime());

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });


        NUtil.get(NUtil.TYPE_TEXT, String.format(Config.NEWS_COMMENT, infoCommentId), new NUtil.CallBack() {
            @Override
            public void response(String url, byte[] bytes) {
                try {
                    String jsonStr = new String(bytes, "utf-8");
                    comments.clear();
                    comments = ParseTool.getComments(jsonStr);
                    commentAdapter = new CommentAdapter(getActivity(), comments);
                    listView.setAdapter(commentAdapter);

                    listView.addHeaderView(view_comment_top);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
