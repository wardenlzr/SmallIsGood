package com.warden.lib.util;

import android.widget.Toast;

import com.warden.lib.base.BaseApp;

/**
 * Created by yubin 2020/11/23 0023  10:24
 */
public class ToastUtils {
    public static void toast(String s) {
        try {
            Toast.makeText(BaseApp.context, s, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
