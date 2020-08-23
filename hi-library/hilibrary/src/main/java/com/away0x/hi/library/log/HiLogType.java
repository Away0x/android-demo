package com.away0x.hi.library.log;

import android.util.Log;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class HiLogType {

    @IntDef({V, D, I, W, E, A}) // 注解接收这几种 Int 类型
    @Retention(RetentionPolicy.SOURCE) // 注解保留时间: 源码级别
    public @interface TYPE{}

    public final static int V = Log.VERBOSE;
    public final static int D = Log.DEBUG;
    public final static int I = Log.INFO;
    public final static int W = Log.WARN;
    public final static int E = Log.ERROR;
    public final static int A = Log.ASSERT;

}
