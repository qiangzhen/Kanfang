package com.example.zq.kanfang.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zq.kanfang.ChooseCityActivity;
import com.example.zq.kanfang.CountActivity;
import com.example.zq.kanfang.InfoDetailActivity;
import com.example.zq.kanfang.LookingNewHouseAct;
import com.example.zq.kanfang.R;
import com.example.zq.kanfang.adapter.InfoAdapter;
import com.example.zq.kanfang.bean.Address;
import com.example.zq.kanfang.bean.Info;
import com.example.zq.kanfang.config.Config;
import com.example.zq.kanfang.customviews.CustomAddressView;
import com.example.zq.kanfang.tools.NUtil;
import com.example.zq.kanfang.tools.ParseTool;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zq on 2015/9/25.
 */
public class FirstPageFragment extends Fragment {


    private static String CITY_ID;
    private static String cityname;

    private static TextView tv_chooseCity;//选择城市
    private static CustomAddressView addressView;//广告页

    private static ListView listView;//下拉刷新组件的一个对象，用于设置headview
    private static PullToRefreshListView pullToRefreshListView;//下来刷新上拉加载组件

    /**
     * 头部
     */
    private static ViewPager vPager;
    private static TextView titleTv;
    private static LinearLayout navLayout;

    private static InfoAdapter infoAdapter;
    private static List<Info> infoList;

    private View view_address;//广告view
    private View view_nav;//导航视图
    private View view_more;
    private Boolean isLoadMoreView = false;

    //加载更多
    private TextView tv_more;
    private LinearLayout ll_more;
    private LinearLayout ll_newHouse;
    private LinearLayout ll_count;

    private int upIndex = 10;
    private int downIndex = 10;


    private String citySPId;
    private String citySPName;
    //当前碎片所用布局
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = View.inflate(getActivity(), R.layout.fragment_first_page, null);
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.listview);
        tv_chooseCity = (TextView) view.findViewById(R.id.tv_choose_city);
        infoList = new ArrayList<>();
        infoAdapter = new InfoAdapter(getActivity(), infoList);
        pullToRefreshListView.setAdapter(infoAdapter);


        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("city", Context.MODE_PRIVATE);
        citySPId = sharedPreferences.getString("cityid", "1");
        citySPName = sharedPreferences.getString("cityname", "北京");

        tv_chooseCity.setText(citySPName);
        initAddressView(citySPId);
        loadInfoDatas(citySPId);

        initEvents();
        listView = pullToRefreshListView.getRefreshableView();
        listView.addHeaderView(view_address);
        listView.addHeaderView(view_nav);
        initPTR2();
        setPullDownLayout();
        return view;
    }

    private void setPullDownLayout() {

        // TODO Auto-generated method stub
        java.text.DateFormat df = DateFormat.getDateFormat(getActivity().getApplicationContext());

        //获取下拉的布局
        ILoadingLayout proxy =
                pullToRefreshListView.getLoadingLayoutProxy(true, false);

        proxy.setPullLabel("下拉刷新");
        proxy.setReleaseLabel("放开以加载...");
        proxy.setRefreshingLabel("玩命加载中....");
        proxy.setLastUpdatedLabel("最后的更新时间：" +
                df.format(new Date()));
    }

    private void initPTR2() {

        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                downIndex = downIndex + 10;
                String urlPath = String.format(Config.FIRST_PAGE_LISTVIEW, downIndex, 1, 1, CITY_ID);
                NUtil.get(NUtil.TYPE_TEXT, urlPath, new NUtil.CallBack() {
                    @Override
                    public void response(String url, byte[] bytes) {
                        try {
                            String jsonStr = new String(bytes, "utf-8");
                            infoList.clear();
                            infoList = ParseTool.getInofs(jsonStr);
                            infoAdapter = new InfoAdapter(getActivity().getApplicationContext(), infoList);
                            pullToRefreshListView.setAdapter(infoAdapter);
                            pullToRefreshListView.onRefreshComplete();

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                upIndex = upIndex + 10;
                String urlPath = String.format(Config.FIRST_PAGE_LISTVIEW, upIndex, 0, 1, CITY_ID);
                NUtil.get(NUtil.TYPE_TEXT, urlPath, new NUtil.CallBack() {
                    @Override
                    public void response(String url, byte[] bytes) {
                        try {
                            String jsonStr = new String(bytes, "utf-8");

                            List<Info> infos = ParseTool.getInofs(jsonStr);
                            infoList.addAll(infos);
                            infoAdapter.notifyDataSetChanged();
                            pullToRefreshListView.onRefreshComplete();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }

    private void loadInfoDatas(String citySPId) {

        String urlPath = String.format(Config.FIRST_PAGE_LISTVIEW, 10, 0, 0, citySPId);
        NUtil.get(NUtil.TYPE_TEXT, urlPath, new NUtil.CallBack() {
            @Override
            public void response(String url, byte[] bytes) {
                try {
                    String jsonStr = new String(bytes, "utf-8");
                    infoList.clear();
                    infoList = ParseTool.getInofs(jsonStr);
                    infoAdapter = new InfoAdapter(getActivity(), infoList);
                    pullToRefreshListView.setAdapter(infoAdapter);
                    pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            int headViewCount = listView.getHeaderViewsCount();
                            if (headViewCount == 3) {
                                if (position == 0 || position == 1 || position == 2) {
                                    return;
                                } else {
                                    Info info = infoList.get(position - listView.getHeaderViewsCount());
                                    toDetailActvity(info);
                                }
                            }
                            if (headViewCount == 4) {
                                if (position == 0 || position == 1 || position == 2 || position == 3) {

                                    return;
                                } else {
                                    Info info = infoList.get(position - listView.getHeaderViewsCount());
                                    toDetailActvity(info);
                                }
                            }


                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void toDetailActvity(Info info) {
        String infoId = info.getId();
        String infoCommentCount = info.getCommentcount();
        String commentid = info.getCommentid();
        Intent intent = new Intent(getActivity(), InfoDetailActivity.class);
        intent.putExtra("infoid", infoId);
        intent.putExtra("infoCommentCount", infoCommentCount);
        intent.putExtra("commentid", commentid);
        startActivity(intent);
    }

    //初始化广告页
    private void initAddressView(String citySPId) {

        addressView = new CustomAddressView(getActivity(), vPager, titleTv, navLayout);


        String url = String.format(Config.FIRST_PAGE_WEBVIEW, citySPId);
        NUtil.get(NUtil.TYPE_TEXT, url, new NUtil.CallBack() {
            @Override
            public void response(String url, byte[] bytes) {
                try {

                    List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
                    Map<String, String> map = null;

                    List<Address> addresses = new ArrayList<Address>();
                    String jsonStr = new String(bytes, "utf-8");

                    addresses = ParseTool.getAddress(jsonStr);

                    for (int i = 0; i < addresses.size(); i++) {
                        map = new HashMap<String, String>();
                        map.put(CustomAddressView.KEY_TITLE, addresses.get(i).getTitle());
                        map.put(CustomAddressView.KEY_URL, addresses.get(i).getPicUrl());
                        datas.add(map);
                    }
                    addressView.setDatas(datas);


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    //初始化控件
    private void initViews() {

        view_address = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.view_first_address, null);
        vPager = (ViewPager) view_address.findViewById(R.id.vp_address);
        titleTv = (TextView) view_address.findViewById(R.id.titleId);
        navLayout = (LinearLayout) view_address.findViewById(R.id.navLayoutId);
        view_nav = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.view_first_nav, null);
        tv_more = (TextView) view_nav.findViewById(R.id.tv_more);
        ll_more = (LinearLayout) view_nav.findViewById(R.id.ll_more);
        ll_newHouse = (LinearLayout) view_nav.findViewById(R.id.ll_newHouse);
        view_more = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.view_first_nav_more, null);
        ll_count = (LinearLayout) view_nav.findViewById(R.id.ll_count);

    }

    //初始化事件

    private void initEvents() {
        tv_chooseCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChooseCityActivity.class);
                startActivity(intent);
            }
        });

        /**
         * 加载更多
         */
        ll_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isLoadMoreView) {
                    listView.addHeaderView(view_more);
                    isLoadMoreView = true;
                } else {
                    listView.removeHeaderView(view_more);
                    isLoadMoreView = false;
                }
            }
        });

        ll_newHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CITY_ID != null) {
                    Intent intent = new Intent(getActivity(), LookingNewHouseAct.class);
                    intent.putExtra("cityId", CITY_ID);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(getActivity(), LookingNewHouseAct.class);
                    intent.putExtra("cityId", "1");
                    startActivity(intent);
                }
            }
        });

        ll_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CITY_ID != null) {
                    Intent intent = new Intent(getActivity(), CountActivity.class);
                    intent.putExtra("cityId", CITY_ID);
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(getActivity(), CountActivity.class);
                    intent.putExtra("cityId", "1");
                    startActivity(intent);
                }
            }
        });

    }

    //更新当前城市
    public static class NewCityBroadReceiver extends BroadcastReceiver {

        public NewCityBroadReceiver() {
            super();
        }

        @Override
        public void onReceive(final Context context, Intent intent) {

            cityname = intent.getExtras().getString("cityname");
            tv_chooseCity.setText(cityname);
            String cityid = intent.getExtras().getString("cityid");
            CITY_ID = cityid;
            String urlPath = String.format(Config.FIRST_PAGE_LISTVIEW, 10, 0, 0, intent.getExtras().getString("cityid"));
            NUtil.get(NUtil.TYPE_TEXT, urlPath, new NUtil.CallBack() {
                @Override
                public void response(String url, byte[] bytes) {
                    try {
                        String jsonStr = new String(bytes, "utf-8");
                        infoList.clear();
                        infoList = ParseTool.getInofs(jsonStr);
                        infoAdapter = new InfoAdapter(context.getApplicationContext(), infoList);
                        pullToRefreshListView.setAdapter(infoAdapter);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            });

            /**
             * 更新广告页
             */
            final String addressUrl = String.format(Config.FIRST_PAGE_WEBVIEW, cityid);
            addressView = new CustomAddressView(context.getApplicationContext(), vPager, titleTv, navLayout);
            NUtil.get(NUtil.TYPE_TEXT, addressUrl, new NUtil.CallBack() {
                @Override
                public void response(String url, byte[] bytes) {
                    try {
                        List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
                        Map<String, String> map = null;

                        List<Address> addresses = new ArrayList<Address>();
                        String jsonStr = new String(bytes, "utf-8");
                        addresses = ParseTool.getAddress(jsonStr);

                        for (int i = 0; i < addresses.size(); i++) {
                            map = new HashMap<String, String>();
                            map.put(CustomAddressView.KEY_TITLE, addresses.get(i).getTitle());
                            map.put(CustomAddressView.KEY_URL, addresses.get(i).getPicUrl());
                            datas.add(map);
                        }
                        addressView.setDatas(datas);

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("city", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("cityid", CITY_ID);
        editor.putString("cityname", cityname);

        editor.commit();


    }
}
