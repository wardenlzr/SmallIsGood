package com.warden.test;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.warden.lib.base.BaseAct;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 通过相机获取原图和缩略图
 */
public class CameraAct extends BaseAct {

    private ImageView iv;

    private String filePath;
    private FileInputStream fis;

    public static void start(Context context) {
        context.startActivity(new Intent(context, CameraAct.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_camera);
        iv = (ImageView) findViewById(R.id.iv);
        filePath = Environment.getExternalStorageState() + "/test.png";
    }

    public void camera1(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 999);
    }

    public void camera2(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = Uri.fromFile(new File(filePath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 998);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 999) {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                //获取缩略图
                iv.setImageBitmap(bitmap);
            } else if (requestCode == 998) {
                FileInputStream fis = null;
                try {
                    //获取原图
                    fis = new FileInputStream(filePath);
                    Bitmap bitmap = BitmapFactory.decodeStream(fis);
                    iv.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}