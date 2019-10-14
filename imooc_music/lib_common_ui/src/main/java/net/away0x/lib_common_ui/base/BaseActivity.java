package net.away0x.lib_common_ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import net.away0x.lib_common_ui.utils.StatusBarUtil;

public class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.statusBarLightMode(this); // 设置 status bar 样式 (设置状态栏透明)
    }
}
