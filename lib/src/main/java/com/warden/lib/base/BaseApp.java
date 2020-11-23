package com.warden.lib.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by yubin 2020/11/23 0023  10:26
 */
public class BaseApp extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
