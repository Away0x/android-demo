package net.away0x.lib_order_center.injection.module

import net.away0x.lib_order_center.service.ShipAddressService
import net.away0x.lib_order_center.service.impl.ShipAddressServiceImpl
import dagger.Module
import dagger.Provides

/*
    收货人信息Module
 */
@Module
class ShipAddressModule {

    @Provides
    fun provideShipAddressservice(shipAddressService: ShipAddressServiceImpl): ShipAddressService {
        return shipAddressService
    }

}
