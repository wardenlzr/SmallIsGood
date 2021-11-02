package com.warden.lib.util;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThreadUtils {
    //创建一个单线程的线程池
    private static Executor executor = Executors.newSingleThreadExecutor();
    //创建一个Handler 这个handler对应的是主线程的消息队列
    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void runOnBackstageThread(Runnable runnable) {
        executor.execute(runnable);
    }

    public static void runOnUIThread(Runnable runnable) {
        handler.post(runnable);
    }
}
