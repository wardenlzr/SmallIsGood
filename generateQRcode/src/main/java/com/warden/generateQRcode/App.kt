package com.warden.generateQRcode

import android.os.StrictMode
import com.warden.lib.base.BaseApp

class App : BaseApp(){
    override fun onCreate() {
        super.onCreate()
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        builder.detectFileUriExposure()
    }
}