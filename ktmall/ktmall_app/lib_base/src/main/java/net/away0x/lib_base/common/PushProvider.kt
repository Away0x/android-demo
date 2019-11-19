package net.away0x.lib_base.common

import com.alibaba.android.arouter.facade.template.IProvider

/*
    跨模块接口调用 接口定义
 */
interface PushProvider: IProvider {
    fun getPushId():String
}
