package net.away0x.lib_pay_center.data.protocol

/*
    获取支付宝 支付签名
 */
data class GetPaySignReq(val orderId: Int, val totalPrice: Long)

