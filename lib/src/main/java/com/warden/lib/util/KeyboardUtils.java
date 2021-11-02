package com.warden.lib.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtils {
    /**
     * 动态隐藏软键盘
     */
    public static void hideSoftInput(View view) {
        if (view == null) {
            L.e("view == null");
            return;
        }
        InputMethodManager inputManger = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManger.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 动态显示软键盘
     */
    public static void showSoftInput(View view) {
        if (view == null) {
            L.e("view == null");
            return;
        }
        InputMethodManager inputManager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(view, 0);
    }

    /**
     * 切换键盘显示与否状态
     */
    public static void toggleSoftInput(View view) {
        if (view == null) {
            L.e("view == null");
            return;
        }
        InputMethodManager inputManager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

}
