package com.example.zq.kanfang.bean;

/**
 * Created by zq on 2015/9/23.
 */
public class City {


    public static final int TYPE_LABEL = 0;
    public static final int TYPE_NAME = 1;


    private String name;
    private String cityid;
    private String category;

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public static int getTypeCount() {
        return 2;
    }

    public int getType() {
        if (category.equals("label")) {
            return TYPE_LABEL;
        }
        return TYPE_NAME;
    }

    public City() {

    }

    public City(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", cityid='" + cityid + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
