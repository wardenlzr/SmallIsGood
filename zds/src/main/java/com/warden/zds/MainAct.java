package com.warden.zds;


import android.os.Bundle;
import android.view.View;

import com.warden.lib.base.BaseAct;
import com.warden.lib.util.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yubin 2020/10/21 0021  11:23
 */
public class MainAct extends BaseAct {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
    }

    private void getData() {
//        POST http://139.129.216.37:81/zxcity_restful/ws/rest

//        114.377916,30.605729 湖北省武汉市洪山区罗家港路1197号靠近江南·新天地C区
        JSONObject jsonObject = new JSONObject();
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
            jsonObject1.put("workType", 4);
            jsonObject1.put("workWay", 1);
            jsonObject1.put("workWifimac", "28:d1:27:83:87:07");
            jsonObject1.put("zUserCode", "z1c19971160515");
            jsonObject.put("data", jsonObject1.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String params = "cmd\tuserWork/checkTimeCardNE\n" +
                "data\t{\"deployId\":\"becbe9f63c2c11eb903700163e04c089\",\"facilityId\":\"b4c792e1022803fbunknown\",\"facilityName\":\"Redmi M2004J7AC\",\"gradeId\":\"bed691e53c2c11eb903700163e04c089\",\"isUpdate\":0,\"remark1\":\"Warden_redmi_WIFI6_5G\",\"remark5\":1,\"shopId\":1380,\"startWorkId\":\"39bdb1109d2f41ec84da64f502d8359f\",\"state\":2,\"userCode\":\"19971160515\",\"userId\":\"15916\",\"userName\":\"19971160515\",\"workAddress\":\"湖胡北省武汉市洪山区罗家港路1197号靠近江南·新天地C区\",\"workLatitude\":30.605729,\"workLongitude\":114.377916,\"workRemark\":\"\",\"workTimeId\":\"bed85b703c2c11eb903700163e04c089\",\"workType\":4,\"workWay\":1,\"workWifimac\":\"28:d1:27:83:87:07\",\"zUserCode\":\"z1c19971160515\"}\n";
        HttpUtils.doPostAsyn("http://139.129.216.37:81/zxcity_restful/ws/rest", jsonObject.toString(), new HttpUtils.CallBack() {
            @Override
            public void ok(String result) {
                try {
                    toast(result);
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fail(String exception) {
                toast(exception);
            }
        });
    }

    public void onWork(View view) {
        try {
            String url = "https://www.wanandroid.com/user/login";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", "warden");
            jsonObject.put("password", "yb911207");
            HttpUtils.doPostAsyn(url, jsonObject.toString(), new HttpUtils.CallBack() {
                @Override
                public void ok(String result) {
                    toast(result);
                }

                @Override
                public void fail(String exception) {
                    toast(exception);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void offWork(View view) {
        getData();
    }
}
