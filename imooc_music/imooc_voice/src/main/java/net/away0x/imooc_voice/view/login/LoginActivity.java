package net.away0x.imooc_voice.view.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import net.away0x.imooc_voice.R;
import net.away0x.imooc_voice.api.MockData;
import net.away0x.imooc_voice.api.RequestCenter;
import net.away0x.imooc_voice.view.login.manager.UserManager;
import net.away0x.imooc_voice.view.login.user.LoginEvent;
import net.away0x.imooc_voice.view.login.user.User;
import net.away0x.lib_common_ui.base.BaseActivity;
import net.away0x.lib_network.okhttp.response.listener.DisposeDataListener;
import net.away0x.lib_network.okhttp.utils.ResponseEntityToModule;

import org.greenrobot.eventbus.EventBus;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity implements DisposeDataListener {

    // 外部用的启动方法
    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.login_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestCenter.login(LoginActivity.this);
            }
        });
    }

    @Override
    public void onSuccess(Object responseObj) {
        // 处理正常逻辑
        User user = (User) responseObj;
        UserManager.getInstance().saveUser(user); // 存储用户数据
        EventBus.getDefault().post(new LoginEvent()); // push event
        finish();
    }

    @Override
    public void onFailure(Object reasonObj) {
        // 登录失败逻辑
        onSuccess(ResponseEntityToModule.parseJsonToModule(
                MockData.LOGIN_DATA, User.class));
    }
}
