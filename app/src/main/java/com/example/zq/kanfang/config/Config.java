package com.example.zq.kanfang.config;

/**
 * Created by zq on 2015/9/23.
 */
public class Config {

    /**
     * 选择城市
     */
    public static final String CHOICE_CITY = "http://ikft.house.qq.com/index.php?guid=866500021200250&devua=appkft_1080_1920_XiaomiMI4LTE_1.8.3_Android19&act=kftcitylistnew&channel=71&devid=866500021200250&appname=QQHouse&mod=appkft";


    /**
     * 广告
     */
    public static final String FIRST_PAGE_WEBVIEW = "http://ikft.house.qq.com/index.php?guid=866500021200250&devua=appkft_1080_1920_XiaomiMI4LTE_1.8.3_Android19&devid=866500021200250&appname=QQHouse&mod=appkft&act=homepage&channel=71&cityid=%s";

    /**
     * 首页 ListView内容
     * 1)进入时：reqnum=10,pageflag=0,buttonmore=0;
     * 2)点击查看更多时：reqnum=20,pageflag=0,buttonmore=1;
     * 3)刷新时：reqnum=20,pageflag=1,buttonmore=1;
     */
    public static final String FIRST_PAGE_LISTVIEW = "http://ikft.house.qq.com/index.php?guid=866500021200250&devua=appkft_1080_1920_XiaomiMI4LTE_1.8.3_Android19&devid=866500021200250&appname=QQHouse&mod=appkft&reqnum=%d&pageflag=%d&act=newslist&channel=71&buttonmore=%d&cityid=%s";
    /**
     * 资讯详情
     */
    public static final String NEWS_DETAIL = "http://ikft.house.qq.com/index.php?guid=866500021200250&devua=appkft_1080_1920_XiaomiMI4LTE_1.8.3_Android19&devid=866500021200250&appname=QQHouse&mod=appkft&act=newsdetail&channel=71&newsid=%s";


    /**
     * 资讯评论
     */
    public static final String NEWS_COMMENT = "http://ikft.house.qq.com/index.php?guid=866500021200250&devua=appkft_1080_1920_XiaomiMI4LTE_1.8.3_Android19&devid=866500021200250&appname=QQHouse&mod=appkft&reqnum=20&pageflag=0&act=newscomment&channel=71&targetid=%s";

    /**
     * 找新房
     */
    public static final String LOOKING_NEWHOUSE = "http://ikft.house.qq.com/index.php?guid=866500021200250&devua=appkft_1080_1920_XiaomiMI4LTE_1.8.3_Android19&rn=10&order=0&searchtype=normal&devid=866500021200250&page=%d&appname=QQHouse&mod=appkft&act=searchhouse&channel=71&cityid=%s";


    /**
     * 找新房 楼盘信息
     */
    public static final String NEW_HOUSE_INFO = "http://ikft.house.qq.com/index.php?guid=866500021200250&devua=appkft_1080_1920_XiaomiMI4LTE_1.8.3_Android19&hid=%s&devid=866500021200250&appname=QQHouse&mod=appkft&act=houseinfo&channel=71";


    /**
     * 打折优惠
     */
    public static final String DISCOUNT_SEAL = "http://ikft.house.qq.com/index.php?&page=%d&appname=QQHouse&mod=appkft&act=discountslist&channel=71&cityid=%s";


    /**
     * 选择了新的城市
     */

    public static final String NEW_CITY = "com.zq.newcity";

    /**
     * 居室
     */

    public static final String[] jushi = new String[]{"不限", "一居", "二居", "三居", "四居", "五居", "别墅", "楼层平面图", "六居", "复式", "排屋", "跃层", "开间"};
    /**
     * 开盘时间
     */

    public static final String[] times = new String[]{"不限", "本月开盘", "下月开盘", "3月内开盘", "6月内开盘", "一年内开盘"};

    /**
     * 项目特色
     */

    public static final String[] tese = new String[]{"不限", "打折优惠", "小户型", "低总价", "现房", "教育地产", "品牌开发商", "刚需房", "婚房", "花园洋房", "不限购", "loft", "改善住宅", "投资地产", "豪华社区", "公园地产", "生态宜居", "旅游地产", "水景地产", "养老地产", "海景地产", "地铁沿线"};

    /**
     * 排序
     */

    public static final String[] paixu = new String[]{"默认排序", "价格由高到低", "价格由低到高", "开盘时间由近到远"};
}