package net.away0x.lib_pay_center.service.impl

import io.reactivex.Observable
import net.away0x.lib_base.rx.baseFunc
import net.away0x.lib_base.rx.baseFuncBoolean
import net.away0x.lib_pay_center.data.repository.PayRepository
import net.away0x.lib_pay_center.service.PayService
import javax.inject.Inject

/*
    支付 业务实现类
 */
class PayServiceImpl @Inject constructor(): PayService{

    @Inject
    lateinit var repository: PayRepository

    /*
        获取支付签名
     */
    override fun getPaySign(orderId: Int, totalPrice: Long): Observable<String> {
        return repository.getPaySign(orderId,totalPrice).flatMap { baseFunc(it) }
    }

    /*
        支付订单，同步订单状态
     */
    override fun payOrder(orderId: Int): Observable<Boolean> {
        return repository.payOrder(orderId).flatMap(::baseFuncBoolean)
    }
}
