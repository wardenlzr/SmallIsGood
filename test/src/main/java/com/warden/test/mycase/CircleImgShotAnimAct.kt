package com.warden.test.mycase

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.warden.lib.base.BaseAct
import com.warden.test.R
import kotlinx.android.synthetic.main.act_circle_img_shot_anim.*

/**
 * 圆形图片呼吸动画
 */
class CircleImgShotAnimAct : BaseAct() {

    companion object {
        // 静态方法
        fun start(context: Context) {
            val intent = Intent(context, CircleImgShotAnimAct::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_circle_img_shot_anim)
        initCircleImg(iv)
        initShotAnim(iv)
    }

    /**
     * 呼吸动画
     */
    private fun initShotAnim(iv: ImageView) {
        val animatorSet = AnimatorSet();
        val animatorX = ObjectAnimator.ofFloat(iv, "scaleX", 1f, 1.2f, 1f);
        val animatorY = ObjectAnimator.ofFloat(iv, "scaleY", 1f, 1.2f, 1f);
        animatorX.repeatCount = -1;
        animatorY.repeatCount = -1;
        animatorSet.play(animatorX).with(animatorY);
        animatorSet.setDuration(1000).start();
    }

    /**
     * 设置圆形
     */
    private fun initCircleImg(iv: ImageView) {
        val src = BitmapFactory.decodeResource(resources, R.drawable.sylm); //获取Bitmap图片
        val bitmapDrawable = RoundedBitmapDrawableFactory.create(resources, src); //创建RoundedBitmapDrawable对象
        bitmapDrawable.isCircular = true
        bitmapDrawable.setAntiAlias(true); //设置抗锯齿
        iv.setImageDrawable(bitmapDrawable); //显示圆角
    }
}