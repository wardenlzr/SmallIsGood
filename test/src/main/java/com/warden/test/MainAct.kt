package com.warden.test

import android.os.Bundle
import android.view.View
import com.warden.lib.base.BaseAct
import com.warden.test.mycase.CameraAct
import com.warden.test.mycase.CircleImgShotAnimAct
import com.warden.test.mycase.KotlinTestAct
import com.warden.test.mycase.TestLunchModeAct

class MainAct : BaseAct() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)
        loge()
    }

    fun testCamera(view: View?) {
        CameraAct.start(activity)
    }

    fun testLunch(view: View?) {
        TestLunchModeAct.start(activity)
    }

    fun testKotlin(view: View?) {
        KotlinTestAct.start(activity)
    }

    fun testCircleImg(view: View) {
        CircleImgShotAnimAct.start(activity)
    }
}