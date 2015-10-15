package com.example.zq.kanfang;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zq.kanfang.adapter.FragmentAdapter;
import com.example.zq.kanfang.bean.InfoDetails;
import com.example.zq.kanfang.config.Config;
import com.example.zq.kanfang.fragment.CommentFragment;
import com.example.zq.kanfang.fragment.ZiXunFragment;
import com.example.zq.kanfang.tools.NUtil;
import com.example.zq.kanfang.tools.ParseTool;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zq on 2015/9/25.
 */
public class InfoDetailActivity extends FragmentActivity {


    //viewpager相关
    private ViewPager viewPager;
    private List<Fragment> fragments;
    private FragmentAdapter fragmentAdapter;

    //界面控件相关
    private EditText et_edit_comment;
    private Button btn_comment;
    private ImageView img_back;
    private ImageView img_share;
    private TextView textView;

    //资讯详情
    private String infoId;//详情ID
    private String infoCommentCount;
    private String infoCommentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_infodetail);

        //获得要显示详情和评论的资讯id
        infoId = getIntent().getStringExtra("infoid");
        infoCommentCount = getIntent().getStringExtra("infoCommentCount");
        infoCommentId = getIntent().getStringExtra("commentid");
        initViews();//初始化控件
        initFragments();
        initEvents();

    }

    private void initEvents() {

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position == 0) {
                    textView.setText("房产资讯");

                    btn_comment.setText(infoCommentCount);
                    Drawable drawable = getResources().getDrawable(R.drawable.icon_comment);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    btn_comment.setCompoundDrawables(drawable, null, null, null);

                }
                if (position == 1) {
                    textView.setText("评论");
                    btn_comment.setText("原文");
                    btn_comment.setCompoundDrawables(null, null, null, null);

                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!btn_comment.getText().equals("原文")) {
                    viewPager.setCurrentItem(1);
                } else {
                    viewPager.setCurrentItem(0);

                }
            }
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = String.format(Config.NEWS_DETAIL, infoId);
                String text = "你的好友向你分享了一条房产信息:" + url;
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/*");
                intent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(intent);

            }
        });


    }

    private void initFragments() {

        fragments = new ArrayList<Fragment>();
        ZiXunFragment ziXunFragment = new ZiXunFragment();
        Bundle bundle = new Bundle();
        bundle.putString("infoid", infoId);
        ziXunFragment.setArguments(bundle);

        CommentFragment commentFragment = new CommentFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("infoid", infoId);
        bundle1.putString("commentid", infoCommentId);
        commentFragment.setArguments(bundle1);

        fragments.add(ziXunFragment);
        fragments.add(commentFragment);
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);

        viewPager.setAdapter(fragmentAdapter);

    }

    private void initViews() {
        img_back = (ImageView) findViewById(R.id.img_back);
        img_share = (ImageView) findViewById(R.id.img_share);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        et_edit_comment = (EditText) findViewById(R.id.et_edit_comment);
        btn_comment = (Button) findViewById(R.id.btn_comment);
        textView = (TextView) findViewById(R.id.tv);

    }


}
