package com.warden.zds;


import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.warden.lib.base.BaseAct;
import com.warden.lib.util.HttpUtils;

import org.json.JSONObject;

/**
 * Created by yubin 2020/10/21 0021  11:23
 */
public class MainAct extends BaseAct {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        CheckBox cb = findViewById(R.id.cb);
        cb.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                Config.isTest = true;
            } else {
                Config.isTest = false;
            }
            Config.change();
            loge(Config.BASEURL);
        });
        loge(Config.BASEURL);
    }

    // state 1 上午, 2 下午,3上午迟到,4 下午早退
    //workType <1上午上班 2上午下班 3下午上班 4下午下班>
    private void getData(int state, int workType) {
//        POST http://139.129.216.37:81/zxcity_restful/ws/rest

//        114.377916,30.605729 湖北省武汉市洪山区罗家港路1197号靠近江南·新天地C区
//        请求参数，请求参数应该是 name1=value1&name2=value2 的形式
        String params = "";
        params = "cmd=userWork/checkTimeCardNE&data={" +
                "    'deployId': '" + Config.DEPLOYID + "', " +
                "    'facilityId': 'b4c792e1022803fbunknown', " +
                "    'facilityName': 'Redmi M2004J7AC', " +
                "    'gradeId': '" + Config.DEPLOYID + "', " +
                "    'isUpdate': 0, " +
                "    'remark1': 'WIFI6_5G', " +
                "    'remark5': 1, " +
                "    'shopId': 1380, " +
                "    'startWorkId': '39bdb1109d2f41ec84da64f502d8359f', " +
                "    'state': " + state + ", " +//1 上午, 2 下午,3上午迟到,4 下午早退
                "    'userCode': " + Config.PHONE + ", " +
                "    'userId': " + Config.USERID + ", " +
                "    'userName': " + Config.PHONE + ", " +
                "    'workAddress': '湖北省-武汉市洪山区罗家港路1197号靠近江南·新天地C区', " +
                "    'workLatitude': 30.605729, " +
                "    'workLongitude': 114.377916, " +
                "    'workRemark': '练摊', " +
                "    'workTimeId': '" + Config.DEPLOYID + "', " +
                "    'workType': " + workType + ", " +
                "    'workWay': 1, " +
                "    'workWifimac': '28:d1:27:83:87:07', " +
                "    'zUserCode': 'z1c19971160515'" +
                "}";

        /*JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cmd", "userWork/checkTimeCardNE");
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("deployId", "becbe9f63c2c11eb903700163e04c089");
            jsonObject1.put("facilityId", "b4c792e1022803fbunknown");
            jsonObject1.put("facilityName", "Redmi M2004J7AC");
            jsonObject1.put("gradeId", "bed691e53c2c11eb903700163e04c089");
            jsonObject1.put("isUpdate", 0);
            jsonObject1.put("remark1", "Warden_redmi_WIFI6_5G");
            jsonObject1.put("remark5", 1);
            jsonObject1.put("shopId", 1380);
            jsonObject1.put("startWorkId", "39bdb1109d2f41ec84da64f502d8359f");
            jsonObject1.put("state", 2);
            jsonObject1.put("userCode", "19971160515");
            jsonObject1.put("userId", "15916");
            jsonObject1.put("userName", "19971160515");
            jsonObject1.put("workAddress", "湖北省武汉市洪山区罗家港路1197号靠近江南·新天地C区");
            jsonObject1.put("workLatitude", 30.605729);
            jsonObject1.put("workLongitude", 114.377916);
            jsonObject1.put("workRemark", "");
            jsonObject1.put("workTimeId", "bed85b703c2c11eb903700163e04c089");
            jsonObject1.put("workType", workType);//<1上午上班 2上午下班 3下午上班 4下午下班>
            jsonObject1.put("workWay", 1);
            jsonObject1.put("workWifimac", "28:d1:27:83:87:07");
            jsonObject1.put("zUserCode", "z1c19971160515");
            jsonObject.put("data", jsonObject1.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        HttpUtils.doPostAsyn(Config.BASEURL, params, new HttpUtils.CallBack() {
            @Override
            public void ok(String result) {
                try {
                    toast("操作成功");
                    loge(result);
                    JSONObject jsonObject = new JSONObject(result);
                    String code = jsonObject.getString("code");
                    String msg = jsonObject.getString("msg");
                    if (workType == 1) {
                        toast("上班" + msg);
                    }
                    if (workType == 4) {
                        toast("下班" + msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fail(String exception) {
                loge(exception);
                toast(exception);
            }
        });
    }

    public void onWork(View view) {
        getData(1, 1);
        /*try {
            String url = "https://www.wanandroid.com/user/login";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", "warden");
            jsonObject.put("password", "yb911207");
            HttpUtils.doPostAsyn(url, "username=warden&password=yb911207", new HttpUtils.CallBack() {
                @Override
                public void ok(String result) {
                    loge(result);
                    toast(result);
                }

                @Override
                public void fail(String exception) {
                    loge(exception);
                    toast(exception);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public void offWork(View view) {
        getData(2, 4);
    }
}
