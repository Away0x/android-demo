package net.away0x.lib_order_center.injection.module

import net.away0x.lib_order_center.service.OrderService
import net.away0x.lib_order_center.service.impl.OrderServiceImpl
import dagger.Module
import dagger.Provides

/*
    订单Module
 */
@Module
class OrderModule {

    @Provides
    fun provideOrderservice(orderService: OrderServiceImpl): OrderService{
        return orderService
    }

}
