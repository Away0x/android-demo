package net.away0x.lib_pay_center.injection.module

import net.away0x.lib_pay_center.service.PayService
import net.away0x.lib_pay_center.service.impl.PayServiceImpl
import dagger.Module
import dagger.Provides

/*
    支付 Module
 */
@Module
class PayModule {

    @Provides
    fun providePayService(payService: PayServiceImpl): PayService {
        return payService
    }

}
