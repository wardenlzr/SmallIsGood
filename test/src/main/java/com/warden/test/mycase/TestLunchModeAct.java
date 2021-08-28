package com.warden.test.mycase;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.warden.lib.base.BaseAct;
import com.warden.test.R;

public class TestLunchModeAct extends BaseAct {

    public static void start(Context context){
        context.startActivity(new Intent(context, TestLunchModeAct.class));
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

    // 呼吸动画
    private void initShotAnim(ImageView iv) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(iv, "scaleX", 1f, 1.2f, 1f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(iv, "scaleY", 1f, 1.2f, 1f);
        animatorX.setRepeatCount(-1);
        animatorY.setRepeatCount(-1);
        animatorSet.play(animatorX).with(animatorY);
        animatorSet.setDuration(1000).start();
    }

    // 圆形Img
    private void initCircleImg(ImageView view) {
        Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.sylm); //获取Bitmap图片
        RoundedBitmapDrawable bitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), src); //创建RoundedBitmapDrawable对象
        bitmapDrawable.setCircular(true); // 设置圆形
        bitmapDrawable.setAntiAlias(true); //设置抗锯齿
        view.setImageDrawable(bitmapDrawable); //显示圆角
    }
}
