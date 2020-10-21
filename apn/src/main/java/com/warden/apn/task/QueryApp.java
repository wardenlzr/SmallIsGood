package com.warden.apn.task;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.warden.apn.MainAct;
import com.warden.apn.bean.AppInfo;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yubin 2020/10/21 0021  11:23
 */
public class QueryApp extends Thread {

    private MainAct act;
    private int filter; // 0:所有应用程序 1：系统程序 2：第三方应用程序 3：安装在SDCard的应用程序
    private PackageManager pm;

    public QueryApp(MainAct act) {
        this.act = act;
    }

    @Override
    public void run() {
        super.run();
        final List<AppInfo> result = queryFilterAppInfo(2);
        act.runOnUiThread(() -> act.setData(result));
    }

    // 根据查询条件，查询特定的ApplicationInfo
    private List<AppInfo> queryFilterAppInfo(int filter) {
        pm = act.getPackageManager();
        // 查询所有已经安装的应用程序
        List<ApplicationInfo> listAppcations = act.getPackageManager()
                .getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        Collections.sort(listAppcations,
                new ApplicationInfo.DisplayNameComparator(act.getPackageManager()));// 排序
        List<AppInfo> appInfos = new ArrayList<AppInfo>(); // 保存过滤查到的AppInfo
        // 根据条件来过滤
        switch (filter) {
            case 0: // 所有应用程序
                appInfos.clear();
                for (ApplicationInfo app : listAppcations) {
                    appInfos.add(getAppInfo(app));
                }
                return appInfos;
            case 1: // 系统程序
                appInfos.clear();
                for (ApplicationInfo app : listAppcations) {
                    if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                        appInfos.add(getAppInfo(app));
                    }
                }
                return appInfos;
            case 2: // 第三方应用程序
                appInfos.clear();
                for (ApplicationInfo app : listAppcations) {
                    if ((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                        appInfos.add(getAppInfo(app));
                    }
                }
                break;
            case 3: // 安装在SDCard的应用程序
                appInfos.clear();
                for (ApplicationInfo app : listAppcations) {
                    if ((app.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
                        appInfos.add(getAppInfo(app));
                    }
                }
                return appInfos;
            default:
                return null;
        }
        return appInfos;
    }

    // 构造一个AppInfo对象 ，并赋值
    public AppInfo getAppInfo(ApplicationInfo app) {
        if (app == null) {
            return null;
        }
        AppInfo appInfo = new AppInfo();
        appInfo.appLabel = ((String) app.loadLabel(pm));
        appInfo.appIcon = (app.loadIcon(pm));
        appInfo.pkgName = (app.packageName);
        appInfo.intent = (pm.getLaunchIntentForPackage(app.packageName));
        try {
            //获取应用数据大小
            long length = new File(app.sourceDir).length();
            //转换为 M
            float size = length * 1f / 1024 / 1024;
            appInfo.size = (get2Decimal(size) + "M");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appInfo;
    }

    public static String get2Decimal(float f) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(f);
    }
}
