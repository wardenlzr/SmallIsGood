package com.warden.pkg;


import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.warden.pkg.bean.AppInfo;
import com.warden.pkg.task.QueryApp;
import com.warden.lib.base.BaseAct;
import com.warden.lib.base.BaseListAdapter;

import java.util.List;
/**
 * Created by yubin 2020/10/21 0021  11:23
 */
public class MainAct extends BaseAct {

    private ProgressBar pb;//loading
    private ListView lv;//列表
    private BaseListAdapter adapter;//适配器
    private int clickPos = 0;//点击的position

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        setTitle("App包名查看管理");
        lv = findViewById(R.id.lv);
        pb = findViewById(R.id.pb);
//        list.addAll(DataUtils.testData(10, AppInfo.class));
        lv.setAdapter(adapter = new BaseListAdapter<AppInfo>(null, R.layout.item_appinfo) {
            @Override
            public void convert(ViewHolder helper, final AppInfo item, final int i) {
                ImageView ivIcon = helper.getView(R.id.ivIcon);
                ivIcon.setImageDrawable(item.appIcon);
                helper.setText(R.id.tv_label, "应用名：" + item.appLabel)
                        .setText(R.id.tv_size, "数据大小：" + item.size)
                        .setText(R.id.tv_pkg, "包名：" + item.pkgName);
                helper.getView(R.id.iv_play).setOnClickListener(v -> {
                    Intent intent = item.intent;
                    if (intent == null) {
                        toast("抱歉，此应用无法打开");
                        return;
                    }
                    startActivity(item.intent);
                });
                helper.getConvertView().setOnClickListener(v -> {
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    assert cm != null;
                    cm.setText(item.pkgName);
                    toast("包名已复制到粘贴板");
                });
                helper.getView(R.id.iv_delete).setOnClickListener(v -> new AlertDialog.Builder(activity)
                        .setTitle("确认卸载？卸载后此应用数据也会清空")
                        .setPositiveButton("取消", (dialog, which) -> dialog.dismiss())
                        .setNegativeButton("确定", (dialog, which) -> {
                            clickPos = i;
                            dialog.dismiss();
                            try {
                                Uri packageURI = Uri.parse("package:".concat(item.pkgName));
                                Intent intent = new Intent(Intent.ACTION_DELETE);
                                intent.setData(packageURI);
                                startActivityForResult(intent, 99);
                            } catch (Exception e) {
                                e.printStackTrace();
                                toast("抱歉，此应用无法卸载");
                            }
                        })
                        .show());

            }
        });
        new QueryApp(this).start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //resultCode 回调回来还是0
        if (requestCode == 99) {
            adapter.remove(clickPos);
        }
    }

    public void setData(List<AppInfo> list) {
        pb.setVisibility(View.GONE);
        adapter.setData(list);
    }
}
