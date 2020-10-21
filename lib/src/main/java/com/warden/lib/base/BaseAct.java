package com.warden.lib.base;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by yubin 2020/10/21 0021  11:23
 */
public class BaseAct extends Activity {

    protected Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
    }

    public void loge(String s) {
        Log.e("Warden", "log() called with: s = [" + s + "]");
    }

    public void toast(String s) {
        try {
            Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}