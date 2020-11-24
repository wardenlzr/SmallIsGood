package com.warden.lib.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.warden.lib.base.BaseApp;

import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by yubin
 * 2018/5/30 0030-下午 5:50
 */
public class ColorUtils {

    private static Context context = BaseApp.context;

    private ColorUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    //获取父类的背景颜色(如果没有,在往上一级)
    public static String obtainBgColor(View v) {
        try {
            Drawable background = v.getBackground();
            if (background == null) {
                return obtainBgColor((View)v.getParent());
            } else {
                ColorDrawable colorDrawable = (ColorDrawable)background;
                int color = colorDrawable.getColor();
                return int2HexColor(color);
            }
        } catch (Exception var4) {
            var4.printStackTrace();
            return "#FFFFFFFF";
        }
    }

    //将int颜色代码 转换为 十六进制
    public static String int2HexColor(int color) {
        StringBuffer sb = new StringBuffer();
        String R = Integer.toHexString(Color.red(color));
        String G = Integer.toHexString(Color.green(color));
        String B = Integer.toHexString(Color.blue(color));
        R = R.length() == 1 ? "0" + R : R;
        G = G.length() == 1 ? "0" + G : G;
        B = B.length() == 1 ? "0" + B : B;
        sb.append("#");
        sb.append(R);
        sb.append(G);
        sb.append(B);
        return sb.toString();
    }

    //将十六进制 颜色代码 转换为 int
    public static int hex2IntColor(String color) {
        /*String reg = "#[a-f0-9A-F]{8}";
        if (!Pattern.matches(reg, color)) {
            color = "#ffffffff";
        }*/
        return Color.parseColor(color);
    }
    //反转颜色(黑色变成白色)
    public static String reserveColor(String originalColor) {
        StringBuilder sb = (new StringBuilder()).append("#");
        if (!originalColor.startsWith("#")) {
            originalColor = "#" + originalColor;
        }

        int start = originalColor.length() == 9 ? 3 : 1;

        for(int i = start; i < originalColor.length(); ++i) {
            String st = originalColor.charAt(i) + "";
            int temp = Integer.parseInt(st, 16);
            sb.append("" + Integer.toHexString(15 - temp).toUpperCase());
        }

        return sb.toString();
    }

    //判断是否是浅色
    public static boolean isLightColor(int color) {
        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        if (darkness < 0.5) {
            return true; // It's a light color
        } else {
            return false; // It's a dark color
        }
    }

    //获取一个随机颜色
    public static int getRandomColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return Color.rgb(r, g, b);
    }

    public final static String[] placeholderColors = {"#e0ddcd", "#dfcecf", "#d4e0cd", "#e0d2cd", "#d6cee0", "#cdd4df", "#cde1dd"};

    //好看的背景颜色
    public static int getRandomBGColor() {
        int i = new Random().nextInt(placeholderColors.length);
        return Color.parseColor(placeholderColors[i]);
    }

    /**
     * 根据颜色资源Id，取得颜色的int色值
     *
     * @param colorId
     * @return color
     */
    public static int getResourcesColor(int colorId) {
        int color = 0x00ffffff;
        try {
            color = context.getResources().getColor(colorId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return color;
    }



    /**
     * 设置颜色透明度
     *
     * @param color
     * @param alpha
     * @return color
     */
    public static int setColorAlpha(int color, int alpha) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }
}
