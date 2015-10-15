package com.example.zq.kanfang;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.example.zq.kanfang.adapter.AbsAdapter;
import com.example.zq.kanfang.bean.Discount;
import com.example.zq.kanfang.config.Config;
import com.example.zq.kanfang.utils.VolleyUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zq on 2015/9/30.
 */
public class CountActivity extends Activity {

    private PullToRefreshListView lv_dis;
    private ListView listView;
    private AbsAdapter<Discount> absAdapter;
    private List<Discount> datas;

    private String url;
    private int page = 1;
    private String cityid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);

        cityid = getIntent().getStringExtra("cityId");
        url = String.format(Config.DISCOUNT_SEAL, page, cityid);
        lv_dis = (PullToRefreshListView) findViewById(R.id.lv_dis);
        listView = lv_dis.getRefreshableView();


        initListView();
        initDatas();
        initEvents();

    }

    private void initEvents() {


        lv_dis.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        lv_dis.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {

                String u = String.format(Config.DISCOUNT_SEAL, page++, cityid);

                StringRequest stringRequest = new StringRequest(u, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            TypeToken<List<Discount>> typeToken = new TypeToken<List<Discount>>() {
                            };

                            Gson gson = new Gson();

                            Log.i("info", jsonArray.toString());

                            List<Discount> d = gson.fromJson(jsonArray.toString(), typeToken.getType());
                            datas.addAll(d);
                            absAdapter.notifyDataSetChanged();

                            lv_dis.onRefreshComplete();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, null);

                VolleyUtils.getmQueue(getApplicationContext()).add(stringRequest);


            }
        });
    }

    private void initDatas() {

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    TypeToken<List<Discount>> typeToken = new TypeToken<List<Discount>>() {
                    };

                    Gson gson = new Gson();

                    Log.i("info", jsonArray.toString());

                    List<Discount> d = gson.fromJson(jsonArray.toString(), typeToken.getType());
                    datas.addAll(d);


                    absAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, null);

        VolleyUtils.getmQueue(getApplicationContext()).add(stringRequest);
    }

    private void initListView() {

        datas = new ArrayList<Discount>();
        absAdapter = new AbsAdapter<Discount>(getApplicationContext(), R.layout.item_discount, datas) {

            @Override
            public void bindView(ViewHolder viewHolder, Discount data) {

                ImageView imageView = (ImageView) viewHolder.getView(R.id.img_dis);
                imageView.setImageResource(R.mipmap.ic_launcher);

                String path = data.getCover();
                VolleyUtils.getmLoader(getApplicationContext()).get(path, ImageLoader.getImageListener(imageView, R.mipmap.ic_launcher, android.R.drawable.ic_menu_delete));

                TextView title = (TextView) viewHolder.getView(R.id.tv_dis_title);
                TextView address = (TextView) viewHolder.getView(R.id.tv_dis_address);
                TextView price = (TextView) viewHolder.getView(R.id.tv_dis_price);
                TextView discount = (TextView) viewHolder.getView(R.id.tv_dis_discount);

                title.setText(data.getName());
                address.setText(data.getAddress());
                price.setText(data.getPrice_pre() + data.getPrice_value() + data.getPrice_unit());
                discount.setText("优惠:" + data.getDiscount());

            }
        };

        lv_dis.setAdapter(absAdapter);

    }
}
