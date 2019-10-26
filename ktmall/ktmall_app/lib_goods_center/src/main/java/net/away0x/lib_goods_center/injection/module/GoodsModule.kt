package net.away0x.lib_goods_center.injection.module

import dagger.Module
import dagger.Provides
import net.away0x.lib_goods_center.service.GoodsService
import net.away0x.lib_goods_center.service.impl.GoodsServiceImpl

/* 商品Module */
@Module
class GoodsModule {

    @Provides
    fun provideGoodservice(goodsService: GoodsServiceImpl): GoodsService {
        return goodsService
    }

}