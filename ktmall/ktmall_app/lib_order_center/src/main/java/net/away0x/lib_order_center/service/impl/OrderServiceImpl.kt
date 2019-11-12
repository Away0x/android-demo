package net.away0x.lib_order_center.service.impl

import android.util.Log
import io.reactivex.Observable
import net.away0x.lib_base.rx.baseFunc
import net.away0x.lib_base.rx.baseFuncBoolean
import net.away0x.lib_order_center.data.protocol.Order
import net.away0x.lib_order_center.data.repository.OrderRepository
import net.away0x.lib_order_center.service.OrderService
import javax.inject.Inject

/*
    订单业务实现类
 */
class OrderServiceImpl @Inject constructor(): OrderService{

    @Inject
    lateinit var repository: OrderRepository

    /*
        根据ID查询订单
     */
    override fun getOrderById(orderId: Int): Observable<Order> {
        return repository.getOrderById(orderId).flatMap { baseFunc(it) }
    }

    /*
        订单确认，提交订单
     */
    override fun submitOrder(order: Order): Observable<Boolean> {
        return repository.submitOrder(order).flatMap(::baseFuncBoolean)
    }

    /*
        根据订单状态获取订单列表
     */
    override fun getOrderList(orderStatus: Int): Observable<MutableList<Order>?> {
        return repository.getOrderList(orderStatus).flatMap {
            Log.d("reeee", it.data.toString())
            baseFunc(it)
        }

    }

    /*
        取消订单
     */
    override fun cancelOrder(orderId: Int): Observable<Boolean> {
        return repository.cancelOrder(orderId).flatMap(::baseFuncBoolean)
    }

    /*
        订单确认收货
     */
    override fun confirmOrder(orderId: Int): Observable<Boolean> {
        return repository.confirmOrder(orderId).flatMap(::baseFuncBoolean)
    }
}
