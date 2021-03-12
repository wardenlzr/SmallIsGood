package com.warden.djt;


import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.warden.lib.base.BaseAct;
import com.warden.lib.util.ColorUtils;
import com.warden.lib.util.HttpUtils;

import org.json.JSONObject;

/**
 * Created by yubin 2020/10/21 0021  11:23
 */
public class MainAct extends BaseAct {

    private RelativeLayout root;
    private TextView tv;
    private ProgressBar pb;
    private Long time = 0l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        root = findViewById(R.id.root);
        tv = findViewById(R.id.tv);
        pb = findViewById(R.id.pb);
        root.setOnClickListener(v -> {
            /*if (AppUtils.isFastClick()) {
                toast("请勿重复点击");
                return;
            }*/
            getData();
        });
        root.setBackgroundColor(ColorUtils.getRandomBGColor());

        boolean isDarkMode = (this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
        loge("isDarkMode:" + isDarkMode);
        if (isDarkMode) {
            tv.setTextColor(Color.YELLOW);
        } else {
            tv.setTextColor(Color.BLACK);
        }
        /*tv.setOnClickListener(v -> {
            ClipboardUtils.copy(tv.getText().toString());
            toast("内容已复制");
        });*/
        checkApp();
    }

    private void checkApp() {
        String url = "http://mockhttp.cn/mock/aaa";
//        String url = "http://mockhttp.cn/mock/aaa123";
        HttpUtils.doGetAsyn(url, new HttpUtils.CallBack() {
            @Override
            public void ok(String result) {
                try {
                    loge(result);
                    JSONObject jsonObject = new JSONObject(result);
                    boolean isDemo = jsonObject.getBoolean("isDemo");
                    if (isDemo) {
                        getData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fail(String exception) {

            }
        });
    }

    //manifest中配置android:configChanges="uiMode"才会走此方法
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //判断是否是深色模式
        //    android:forceDarkAllowed="false"禁止使用深色模式
        //val isDarkMode = this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK===Configuration.UI_MODE_NIGHT_YES
        boolean isDarkMode = (this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
        loge("isDarkMode:" + isDarkMode);
        if (isDarkMode) {
            tv.setTextColor(Color.YELLOW);
        } else {
            tv.setTextColor(Color.BLACK);
        }
    }

    private boolean change = false;
    private void getData() {
        pb.setVisibility(View.VISIBLE);
        String url = "http://www.iamwawa.cn/home/dujitang/ajax";
        if (change){
            url = "http://www.iamwawa.cn/home/lizhi/ajax";
        }
        HttpUtils.doGetAsyn(url, new HttpUtils.CallBack() {
            @Override
            public void ok(String result) {
                try {
                    change = !change;
                    loge(result);
                    pb.setVisibility(View.GONE);
                    root.setBackgroundColor(ColorUtils.getRandomBGColor());
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");
                    String data = "";
                    if (status.equals("1")) {
                        data = jsonObject.getString("data");
                    } else {
                        data = jsonObject.getString("info");
                    }
                    tv.setText(data);
                } catch (Exception e) {
                    e.printStackTrace();
                    tv.setText("没有毒鸡汤, 好好努力就行了");
                }
            }

            @Override
            public void fail(String exception) {
                pb.setVisibility(View.GONE);
                toast(exception);
            }
        });
    }

}
