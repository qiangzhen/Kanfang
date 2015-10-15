package com.example.zq.kanfang.tools;

import android.util.Log;

import com.android.volley.ServerError;
import com.example.zq.kanfang.bean.Address;
import com.example.zq.kanfang.bean.City;
import com.example.zq.kanfang.bean.Comment;
import com.example.zq.kanfang.bean.Info;
import com.example.zq.kanfang.bean.InfoDetails;
import com.example.zq.kanfang.bean.NewHouseInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zq on 2015/9/23.
 */
public class ParseTool {

    //I,O,U,V

    private static String[] type = {"hotcities", "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q",
            "R", "S", "T", "W", "X", "Y", "Z"};


    /**
     * 获得城市列表
     *
     * @param jsonStr
     * @return
     */
    public static List<City> getCityList(String jsonStr) {


        List<City> cities = new ArrayList<City>();

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONObject object = jsonObject.getJSONObject("cities");

            for (int i = 0; i < type.length; i++) {
                City city = new City();

                if (type[i].equals("hotcities"))
                    city.setName("热门城市");
                else
                    city.setName(type[i]);
                city.setCategory("label");
                cities.add(city);

                JSONArray array = object.getJSONArray(type[i]);
                for (int j = 0; j < array.length(); j++) {

                    City city1 = new City();

                    JSONObject object1 = array.getJSONObject(j);
                    city1.setName(object1.getString("cityname"));
                    city1.setCityid(object1.getString("cityid"));
                    city1.setCategory("name");
                    cities.add(city1);
                }


            }

            return cities;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 获得展示广告信息
     */
    public static List<Address> getAddress(String jsonStr) {

        try {
            List<Address> addresses = new ArrayList<Address>();
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Address address = new Address();
                address.setPicUrl(object.getString("picurl"));
                address.setTitle(object.getString("title"));
                //address.setHouseid(object.getString("houseid"));

                addresses.add(address);
            }

            return addresses;


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 获得资讯列表
     */

    public static List<Info> getInofs(String jsonStr) {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);


            JSONArray jsonArray = jsonObject.getJSONArray("data");
            List<Info> infos = new ArrayList<Info>();


            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Info info = new Info();

                info.setId(object.getString("id"));
                info.setType(object.getString("type"));
                info.setTitle(object.getString("title"));
                info.setSummary(object.getString("summary"));
                info.setGroupthumbnail(object.getString("groupthumbnail"));
                info.setCommentcount(object.getString("commentcount"));
                info.setCommentid(object.getString("commentid"));

                infos.add(info);
            }

            return infos;
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    public static InfoDetails getInfoDetails(String jsonStr) {


        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            InfoDetails details = new InfoDetails();
            details.setId(jsonObject.getString("id"));
            details.setTitle(jsonObject.getString("title"));
            details.setSource(jsonObject.getString("source"));
            details.setTime(jsonObject.getString("time"));
            details.setUrl(jsonObject.getString("url"));
            details.setSurl(jsonObject.getString("surl"));


            return details;
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }


    private static List<Comment> comments;

    public static List<Comment> getComments(String jsonStr) {
        comments = new ArrayList<Comment>();

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            JSONArray jsonArray = jsonObject1.getJSONArray("comments");


            for (int i = 0; i < jsonArray.length(); i++) {
                Comment comment = new Comment();

                JSONObject object = jsonArray.getJSONObject(i);

                comment.setContent(object.getString("content"));
                comment.setNick(object.getString("nick"));
                comment.setRegion(object.getString("region"));
                comment.setTime(object.getString("time"));
                comment.setHead(object.getString("head"));
                comments.add(comment);
            }

            return comments;

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }


    private static List<NewHouseInfo> houseInfos;

    public static List<NewHouseInfo> getNewHouseInfo(String response) {

        houseInfos = new ArrayList<NewHouseInfo>();

        try {

            JSONObject jsonObject = new JSONObject(response);


            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {

                NewHouseInfo newHouseInfo = new NewHouseInfo();
                newHouseInfo.setTotal(jsonObject.getString("total"));
                JSONObject object = jsonArray.getJSONObject(i);
                newHouseInfo.setFid(object.getString("fid"));
                newHouseInfo.setFcover(object.getString("fcover"));
                newHouseInfo.setFname(object.getString("fname"));
                newHouseInfo.setFaddress(object.getString("faddress"));
                newHouseInfo.setRegion(object.getString("fregion"));
                newHouseInfo.setFpricedisplaystr(object.getString("fpricedisplaystr"));


                JSONArray array = object.getJSONArray("bookmark");

                List<String> strings = new ArrayList<String>();
                for (int j = 0; j < array.length(); j++) {
                    JSONObject object1 = array.getJSONObject(j);
                    strings.add(object1.getString("tag"));
                }
                newHouseInfo.setBookmarks(strings);

                houseInfos.add(newHouseInfo);

            }

            return houseInfos;


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}

