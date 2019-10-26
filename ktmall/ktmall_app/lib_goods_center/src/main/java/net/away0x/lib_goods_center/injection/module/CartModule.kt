package net.away0x.lib_goods_center.injection.module

import dagger.Module
import dagger.Provides
import net.away0x.lib_goods_center.service.CartService
import net.away0x.lib_goods_center.service.impl.CartServiceImpl

/* 购物车 Module */
@Module
class CartModule {

    @Provides
    fun provideCartservice(cartService: CartServiceImpl): CartService {
        return cartService
    }

}
