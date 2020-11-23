package com.warden.lib.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.warden.lib.base.BaseApp;

/**
 * 获取剪切板数据 ClipboardManager.getPrimaryClip();
 *
 *
 * Created by yubin 2020/11/23 0023  11:34
 */
public class ClipboardUtils {

    //复制普通文本
    public static void copy(String str) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) BaseApp.context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", str);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }

}
