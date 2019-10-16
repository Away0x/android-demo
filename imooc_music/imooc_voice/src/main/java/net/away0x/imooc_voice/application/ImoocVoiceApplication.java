package net.away0x.imooc_voice.application;

import android.app.Application;

import net.away0x.lib_audio.app.AudioHelper;

public class ImoocVoiceApplication extends Application {

    private static ImoocVoiceApplication mApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        //音频SDK初始化
        AudioHelper.init(this);
    }

    public static ImoocVoiceApplication getInstance() {
        return mApplication;
    }
}