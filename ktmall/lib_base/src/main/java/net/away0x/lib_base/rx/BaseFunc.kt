package net.away0x.lib_base.rx

import io.reactivex.Observable
import net.away0x.lib_base.common.ResultCode
import net.away0x.lib_base.data.protocol.BaseResp

fun <T> baseFunc(t: BaseResp<T>): Observable<T> {
    if (t.status != 0) {
        return Observable.error(BaseException(t.status, t.message))
    }
    return Observable.just(t.data)
}

fun <T> baseFuncBoolean(t: BaseResp<T>): Observable<Boolean> {
    if (t.status != ResultCode.SUCCESS) {
        return Observable.error(BaseException(t.status, t.message))
    }
    return Observable.just(true)
}