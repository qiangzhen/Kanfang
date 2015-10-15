package com.example.zq.kanfang;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zq.kanfang.adapter.CitysAdapter;
import com.example.zq.kanfang.bean.City;
import com.example.zq.kanfang.bean.Info;
import com.example.zq.kanfang.config.Config;
import com.example.zq.kanfang.fragment.FirstPageFragment;
import com.example.zq.kanfang.tools.NUtil;
import com.example.zq.kanfang.tools.ParseTool;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.example.zq.kanfang.fragment.FirstPageFragment.*;

/**
 * Created by zq on 2015/9/23.
 */
public class ChooseCityActivity extends Activity {


    private ListView listView;
    private Button cancle;
    private List<City> cityList;
    private CitysAdapter citysAdapter;
    private TextView location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_choose_activity);


        loadDatas();
        initViews();
        initEvents();


    }


    private void initEvents() {

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(ChooseCityActivity.this)
                        .setMessage("取消选择？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();

                            }
                        })
                        .setNegativeButton("继续选择", null)
                        .show();
            }
        });
    }

    private void initViews() {
        listView = (ListView) findViewById(R.id.listview_citys);
        cancle = (Button) findViewById(R.id.btn_cancle);
        location = (TextView) findViewById(R.id.tv_location);
    }

    private void loadDatas() {

        cityList = new ArrayList<City>();

        NUtil.get(NUtil.TYPE_TEXT, Config.CHOICE_CITY, new NUtil.CallBack() {
            @Override
            public void response(String url, byte[] bytes) {
                String jsonStr = null;
                try {
                    jsonStr = new String(bytes, "utf-8");

                    cityList = ParseTool.getCityList(jsonStr);

                    citysAdapter = new CitysAdapter(getApplicationContext(), cityList);
                    listView.setAdapter(citysAdapter);


                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            City city = cityList.get(position);
                            String cityName = city.getName();
                            String cityId = city.getCityid();
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putString("cityname", cityName);
                            bundle.putString("cityid", cityId);

                            //Log.e("hah", cityName);
                            // Log.e("hahasdfs", cityList.get(position).toString());


                            intent.putExtras(bundle);

                            intent.setAction(Config.NEW_CITY);
                            getApplicationContext().sendBroadcast(intent);
                            finish();
                        }
                    });


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });


    }
}
