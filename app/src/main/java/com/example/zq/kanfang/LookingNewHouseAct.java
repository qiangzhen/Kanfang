package com.example.zq.kanfang;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.zq.kanfang.adapter.NewHouseAdapter;
import com.example.zq.kanfang.bean.Info;
import com.example.zq.kanfang.bean.NewHouseInfo;
import com.example.zq.kanfang.config.Config;
import com.example.zq.kanfang.tools.ParseTool;
import com.example.zq.kanfang.utils.VolleyUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zq on 2015/9/28.
 */
public class LookingNewHouseAct extends Activity {


    private String cityId;
    private int page = 1;

    private PullToRefreshListView pullToRefreshListView;
    private List<NewHouseInfo> newHouseInfos;
    private NewHouseAdapter newHouseAdapter;

    private TextView tv_total;
    private TextView tv_type;
    private TextView tv_price;
    private TextView tv_more;

    //PopWindow相关
    private PopupWindow popupWindowType;
    private PopupWindow popupWindowPrice;
    private PopupWindow popupWindowMore;


    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looking_newhouse);

        cityId = getIntent().getStringExtra("cityId");
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.ptr_listview);
        newHouseInfos = new ArrayList<NewHouseInfo>();


        initViews();
        initPopWindow();
        initEvents();
        initDatas();
        initPTR();

    }

    private void initEvents() {
        tv_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (popupWindowType != null && popupWindowType.isShowing()) {
                    popupWindowType.dismiss();
                } else {
                    if (popupWindowPrice.isShowing()) {
                        popupWindowPrice.dismiss();
                    }
                    if (popupWindowMore.isShowing()) {
                        popupWindowMore.dismiss();
                    }
                    popupWindowType.showAsDropDown(tv_type);
                }
            }
        });


        tv_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindowPrice != null && popupWindowPrice.isShowing()) {
                    popupWindowPrice.dismiss();
                } else {
                    if (popupWindowType.isShowing()) {
                        popupWindowType.dismiss();
                    }
                    if (popupWindowMore.isShowing()) {
                        popupWindowMore.dismiss();
                    }
                    popupWindowPrice.showAsDropDown(tv_type);
                }
            }
        });


        tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (popupWindowMore != null && popupWindowMore.isShowing()) {
                    popupWindowMore.dismiss();
                } else {
                    if (popupWindowType.isShowing()) {
                        popupWindowType.dismiss();
                    }
                    if (popupWindowPrice.isShowing()) {
                        popupWindowPrice.dismiss();
                    }
                    popupWindowMore.showAsDropDown(tv_more);
                }
            }
        });

        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewHouseInfo newHouseInfo = newHouseInfos.get(position);

                Intent intent = new Intent(LookingNewHouseAct.this, NewHouseDetailAct.class);
                intent.putExtra("fid", newHouseInfo.getFid());
                startActivity(intent);

            }
        });
    }

    private void initViews() {
        tv_total = (TextView) findViewById(R.id.tv_total);
        tv_type = (TextView) findViewById(R.id.tv_new_type);
        tv_price = (TextView) findViewById(R.id.tv_new_price);
        tv_more = (TextView) findViewById(R.id.tv_new_more);

    }

    private void initPopWindow() {
        View pop_type = getLayoutInflater().inflate(R.layout.popwindow_type, null, false);
        popupWindowType = new PopupWindow(pop_type, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        View pop_price = getLayoutInflater().inflate(R.layout.popwindow_price, null, false);
        popupWindowPrice = new PopupWindow(pop_price, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        final View pop_more = getLayoutInflater().inflate(R.layout.popwindow_more, null, false);
        popupWindowMore = new PopupWindow(pop_more, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        tv1 = (TextView) pop_more.findViewById(R.id.tv_jushi);
        tv2 = (TextView) pop_more.findViewById(R.id.tv_kaipan);
        tv3 = (TextView) pop_more.findViewById(R.id.tv_tese);
        tv4 = (TextView) pop_more.findViewById(R.id.tv_paixu);

        //final LinearLayout ll = (LinearLayout) pop_more.findViewById(R.id.ll_navv);

        final ListView lv = (ListView) pop_more.findViewById(R.id.iv_new_more);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                initBackgrounds();
                tv1.setBackgroundColor(Color.parseColor("WHITE"));
                ArrayAdapter<String> arrayAdapter =
                        new ArrayAdapter<String>(getApplicationContext(), R.layout.item, R.id.tv_new, Config.jushi);
                lv.setAdapter(arrayAdapter);
            }


        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initBackgrounds();
                tv2.setBackgroundColor(Color.parseColor("WHITE"));
                ArrayAdapter<String> arrayAdapter =
                        new ArrayAdapter<String>(getApplicationContext(), R.layout.item, R.id.tv_new, Config.times);
                lv.setAdapter(arrayAdapter);
            }
        });

        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initBackgrounds();
                tv3.setBackgroundColor(Color.parseColor("WHITE"));
                ArrayAdapter<String> arrayAdapter =
                        new ArrayAdapter<String>(getApplicationContext(), R.layout.item, R.id.tv_new, Config.tese);
                lv.setAdapter(arrayAdapter);
            }
        });

        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initBackgrounds();
                tv4.setBackgroundColor(Color.parseColor("WHITE"));
                ArrayAdapter<String> arrayAdapter =
                        new ArrayAdapter<String>(getApplicationContext(), R.layout.item, R.id.tv_new, Config.paixu);
                lv.setAdapter(arrayAdapter);
            }
        });


    }

    private void initBackgrounds() {
        tv1.setBackgroundColor(Color.parseColor("#7c7c7c"));
        tv2.setBackgroundColor(Color.parseColor("#7c7c7c"));
        tv3.setBackgroundColor(Color.parseColor("#7c7c7c"));
        tv4.setBackgroundColor(Color.parseColor("#7c7c7c"));

    }


    private void initPTR() {
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                StringRequest request = new StringRequest(String.format(Config.LOOKING_NEWHOUSE, ++page, cityId), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("ereer", response);

                        List<NewHouseInfo> newHouseInfos1 = ParseTool.getNewHouseInfo(response);
                        newHouseInfos.addAll(newHouseInfos1);
                        newHouseAdapter.notifyDataSetChanged();
                        pullToRefreshListView.onRefreshComplete();

                    }
                }, null);

                VolleyUtils.getmQueue(getApplicationContext()).add(request);
            }
        });
    }

    private void initDatas() {

        Log.e("errer", cityId + "sfsd");
        StringRequest request = new StringRequest(String.format(Config.LOOKING_NEWHOUSE, page, cityId), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("ereer", response);

                newHouseInfos = ParseTool.getNewHouseInfo(response);
                tv_total.setText("共有" + newHouseInfos.get(0).getTotal() + "个楼盘");

                Log.e("sdfsdfs", newHouseInfos.get(0).getFname() + "==========");

                newHouseAdapter = new NewHouseAdapter(getApplicationContext(), newHouseInfos);

                pullToRefreshListView.setAdapter(newHouseAdapter);

            }
        }, null);

        VolleyUtils.getmQueue(getApplicationContext()).add(request);
    }


}
