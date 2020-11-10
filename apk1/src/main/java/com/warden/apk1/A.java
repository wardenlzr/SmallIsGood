package com.warden.apk1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

/**
 * Create by yubin on 2020/9/14 0014 - 18:00
 */
public class A extends Activity {

    public Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        Uri uri = getIntent().getData();
        log(uri.toString());

        String uriPath = uri.getPath();
        //content://com.tencent.mm.external.fileprovider/external/Android/data/com.tencent.mm/MicroMsg/Download/app-debug.apk.1
//        String content = uri.getQueryParameter("content");

        uriPath = uriPath.substring(0, uriPath.lastIndexOf(".apk") + 4);
        log(uriPath);
        File apk = new File(uriPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            Uri uri1 = FileProvider.getUriForFile(this, "com.warden.apk1.fileprovider", apk);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apk), "application/vnd.android.package-archive");
        }
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999) {
            View view = new View(context);
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 500);
        }
    }*/

    public void log(String str) {
        if (TextUtils.isEmpty(str)) {
            str = "null";
        }
        Log.e("Warden", str);
    }
}
