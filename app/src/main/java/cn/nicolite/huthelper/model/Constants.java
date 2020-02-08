package cn.nicolite.huthelper.model;

/**
 * 常量类 放置一些常量, 如APPID、APPKEY等
 * Created by djxf on 17-10-13.
 */

public class Constants {

    //DataBase Name
    public static final String DBNAME = "huthelper-db";

    //API URL
    public static final String BASE_URL = "https://api.huthelper.cn";//登录aseur和一切baseurl

    //iamge
    public static final String PICTURE_URL = "http://images.huthelper.cn:8888";//图片url，上传也是

    //SOME URL
    public static final String ARTICLE_BASE_URL = "http://218.75.197.121:8888";
    public static final String FRESHMANGUIDE_URL = ARTICLE_BASE_URL + "/home/post/19"; //新生攻略
    public static final String CHANGE_PWD = BASE_URL + "/auth/resetPass";
    public static final String HELP =  BASE_URL + "/home/xkxy";
    public static final String PERMISSON = BASE_URL + "/home/xkxy";//软件许可协议
    public static final String LIBRARY = "http://218.75.197.121:8889/opac/m/index";
    public static final String HOMEWORK = BASE_URL + "/api/v3/Home/myHomeWork/";
    public static final String BLOG = "http://nicolite.cn";//开发者博客之一
    public static final String OPEN_SOURCE = "https://github.com/nicolite/HutHelper";//开源地址
    public static final String CLUB_ACTIVITY = "http://gengjiahao.cn:5000/articles/all/?a=android";//社团活动
    public static final String S_CALENDAR = "http://images.huthelper.cn:8888/uploads/huthelper/img/web1.png";//校历
    public static final String S_MAP = "http://223.111.182.121:8888/public/pics/map.jpg";//工大地图
    public static final String PROTATOL = "https://api.huthelper.cn/home/hehe";//隐私协议
    public static final String JXBM = "https://api.huthelper.cn/home/jx";//驾校报名
    public static final String REGISTER = "https://api.huthelper.cn/auth/register";//注册
    public static final String IMURL = "https://m.huthelper.cn/im/#/im?user_id=%s&key=%s&to_user_id=%s";//聊天链接
    public static final String IMLIST = "https://m.huthelper.cn/im/#/list-popup?user_id=%s&key=%s";//消息列表

    //XIAOMI PUSH
    public static final String XIAOMI_APP_ID = "2882303761517605932";
    public static final String XIAOMI_APP_KEY = "5561760591932";

    //HUAWEI PUSH
    public static final String HUAWEI_APP_ID = "100288343";
    public static final String HUAWE_APP_SECRET = "626f189b7d3eac79b39ef42515a5744f";

    //Bugly
    public static final String BUGLY_APPID = "06373e7169";

    //MTA
    public static final String MTA_APPKEY = "ACIDT96D3M7R";

    //广点通
    public static final String GDT_POSID = "7050548342590541";
    public static final String GDT_APPID = "1106930772";

    //Activity Request Result Code
    public static final int REQUEST = 100;
    public static final int DELETE = 300;
    public static final int PUBLISH = 400;
    public static final int CHANGE = 500;
    public static final int RESULT = 600;
    public static final int REFRESH = 700;

    //Action
    public static final String MainBroadcast = "cn.nicolite.huthelper.mainbroadcast"; //主页面广播actioin

    //Broadcast type
    public static final int BROADCAST_TYPE_NOTICE = 0;
    public static final int BROADCAST_TYPE_REFRESH_AVATAR = 1;

    //RSA PublicKey
    public static final String rsaPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZ1T65TCvVK+vndaRgeE7PhLS+vYdNNMTjCD1WO1eRFqBuTM5rMP7h3Hq+jsTicFh1/FoPRjwvlWhuCXZi6IqjphvVvXZso74xhNLA8Lml750qkkyaDKD5Pr2PGIaJ6h4WKvt7sBhDQ6LZQcz83Disp1frgtWhPytyJr7D1I4DvwIDAQAB";

    public static final String  USER_ID = "all_class_id";

    //sp 存储用户相关信息
    public static final String USER_XML = "user_sp";
    public static final String USER_PWD = "user_pwd";
    public static final String USER_QZZH = "user_qzzh"; //用户在登录强智教务系统的账号

    //相关webview URL
    public static final String WEB_EMPTY_CLASS = "http://m.huthelper.cn/im/#/qz/empty-room";
    public static final String WEB_EXAM = "http://m.huthelper.cn/im/#/qz/exam-plan";
    public static final String WEB_GRADE = "http://m.huthelper.cn/im/#/qz/score";

}
