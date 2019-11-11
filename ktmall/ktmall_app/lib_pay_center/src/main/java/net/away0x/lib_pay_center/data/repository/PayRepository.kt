package net.away0x.lib_pay_center.data.repository


import io.reactivex.Observable
import net.away0x.lib_base.data.net.RetrofitFactory
import net.away0x.lib_base.data.protocol.BaseResp
import javax.inject.Inject

import net.away0x.lib_pay_center.data.protocol.GetPaySignReq
import net.away0x.lib_pay_center.data.protocol.PayOrderReq
import net.away0x.lib_pay_center.data.api.PayApi


/*
   支付数据层
 */
class PayRepository @Inject constructor() {

    /*
        获取支付宝支付签名
     */
    fun getPaySign(orderId: Int, totalPrice: Long): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(PayApi::class.java).getPaySign(GetPaySignReq(orderId, totalPrice))
    }

    /*
        刷新订单状态已支付
     */
    fun payOrder(orderId: Int): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(PayApi::class.java).payOrder(orderId)
    }


}
