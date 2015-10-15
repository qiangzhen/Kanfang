package com.example.zq.kanfang;

import android.app.Activity;
import android.os.Bundle;

import com.example.zq.kanfang.config.Config;

/**
 * Created by zq on 2015/9/29.
 */
public class NewHouseDetailAct extends Activity {


    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newhouse_detail);

        String fid = getIntent().getStringExtra("fid");
        url = String.format(Config.NEW_HOUSE_INFO, fid);

    }
}
