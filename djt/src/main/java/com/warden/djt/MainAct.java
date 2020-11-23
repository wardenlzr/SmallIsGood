package com.warden.djt;


import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.warden.lib.base.BaseAct;
import com.warden.lib.util.ClipboardUtils;
import com.warden.lib.util.ColorUtils;
import com.warden.lib.util.HttpUtils;
import com.warden.lib.util.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yubin 2020/10/21 0021  11:23
 */
public class MainAct extends BaseAct {

    private RelativeLayout root;
    private TextView tv;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        root = findViewById(R.id.root);
        tv = findViewById(R.id.tv);
        pb = findViewById(R.id.pb);
        root.setOnClickListener(v -> getData());
        root.setBackgroundColor(ColorUtils.getRandomBGColor());

        /*tv.setOnClickListener(v -> {
            ClipboardUtils.copy(tv.getText().toString());
            toast("内容已复制");
        });*/
        getData();
    }

    private void getData() {
        pb.setVisibility(View.VISIBLE);
        HttpUtils.doGetAsyn("http://www.iamwawa.cn/home/dujitang/ajax", new HttpUtils.CallBack() {
            @Override
            public void ok(String result) {
                try {
                    pb.setVisibility(View.GONE);
                    root.setBackgroundColor(ColorUtils.getRandomBGColor());
                    JSONObject jsonObject = new JSONObject(result);
                    String data = jsonObject.getString("data");
                    tv.setText(data);
                } catch (JSONException e) {
                    e.printStackTrace();
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
