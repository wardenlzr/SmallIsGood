package com.warden.zds;

/**
 * Created by yubin 2021/1/5 0005  10:34
 */
public class Config {
    public static boolean isTest = true;

    //    正式的参数
    public static String BASEURL = "";
    public static String USERID = "";
    public static String PHONE = "";
    public static String DEPLOYID = "";


    static {
        change();
    }
    //flag true测试的参数
    public static void change(){
        if (isTest) {
            //    测试的参数
            BASEURL = "http://139.129.216.37:81/zxcity_restful/ws/rest";//测试
            USERID = "15916";
            PHONE = "19971160515";
            DEPLOYID = "becbe9f63c2c11eb903700163e04c089";
        }else {
            ////正式的参数
            BASEURL = "http://app.izxcs.com:81/zxcity_restful/ws/rest";
            USERID = "83457";
            PHONE = "19971160515";
            DEPLOYID = "2a3428ca411e11eb8b5b0c42a135e9b8";//正式的班次ID
        }
    }

}
