package com.example.zq.kanfang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.zq.kanfang.R;
import com.example.zq.kanfang.bean.InfoDetails;
import com.example.zq.kanfang.config.Config;
import com.example.zq.kanfang.tools.NUtil;
import com.example.zq.kanfang.tools.ParseTool;

import java.io.UnsupportedEncodingException;

/**
 * Created by zq on 2015/9/26.
 */
public class ZiXunFragment extends Fragment {


    View view;
    private String infoId;//详情ID

    private WebView webView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        infoId = getArguments().getString("infoid");

        Log.e("err", infoId);

        view = View.inflate(getActivity(), R.layout.fragment_zixun, null);
        initViews();
        loadDatas();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view;
    }

    private void loadDatas() {

        NUtil.get(NUtil.TYPE_TEXT, String.format(Config.NEWS_DETAIL, infoId), new NUtil.CallBack() {
            @Override
            public void response(String url, byte[] bytes) {
                try {
                    String jsonStr = new String(bytes, "utf-8");
                    InfoDetails infoDetails = ParseTool.getInfoDetails(jsonStr);

                    String surlDetails = infoDetails.getSurl();
                    String id = infoDetails.getId();
                    webView.loadUrl(surlDetails + id);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void initViews() {

        webView = (WebView) view.findViewById(R.id.webview);
    }
}
