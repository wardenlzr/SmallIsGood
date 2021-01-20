package com.warden.test;

import android.os.Bundle;
import android.view.View;

import com.warden.lib.base.BaseAct;

public class MainAct extends BaseAct {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

    }

    public void testCamera(View view) {
        CameraAct.start(activity);
    }

    public void testLunch(View view) {
        TestLunchAct.start(activity);
    }
}
