package com.warden.lib.util;

import android.content.Context;
import android.graphics.Color;

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
     * 将十六进制 颜色代码 转换为 int
     *
     * @return color
     */
    public static int hex2IntColor(String color) {
        String reg = "#[a-f0-9A-F]{8}";
        if (!Pattern.matches(reg, color)) {
            color = "#ffffffff";
        }
        return Color.parseColor(color);
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
