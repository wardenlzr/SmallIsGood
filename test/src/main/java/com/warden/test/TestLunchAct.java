package com.warden.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.warden.lib.base.BaseAct;

public class TestLunchAct extends BaseAct {

    public static void start(Context context){
        context.startActivity(new Intent(context, TestLunchAct.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_lunch);
        int a[] = new int[3];
        System.out.println("a[0]="+a[0]);
        System.out.println("a[1]="+a[1]);
        System.out.println("a[2]="+a[2]);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        loge();
    }

    public void test(View view) {
        loge();
        start(activity);
//        finish();
    }
}
