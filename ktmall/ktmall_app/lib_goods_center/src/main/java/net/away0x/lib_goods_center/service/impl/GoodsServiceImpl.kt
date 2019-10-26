package net.away0x.lib_goods_center.service.impl

import io.reactivex.Observable
import net.away0x.lib_base.rx.baseFunc
import net.away0x.lib_goods_center.data.protocol.Goods
import net.away0x.lib_goods_center.data.repository.GoodsRepository
import net.away0x.lib_goods_center.service.GoodsService
import javax.inject.Inject

/* 商品 业务层 实现类 */
class GoodsServiceImpl @Inject constructor(): GoodsService {

    @Inject
    lateinit var repository: GoodsRepository

    /* 获取商品列表 */
    override fun getGoodsList(categoryId: Int, pageNo: Int): Observable<MutableList<Goods>?> {
        return repository.getGoodsList(categoryId,pageNo)
            .flatMap { baseFunc(it) }
    }

    /* 根据关键字查询商品 */
    override fun getGoodsListByKeyword(keyword: String, pageNo: Int): Observable<MutableList<Goods>?> {
        return repository.getGoodsListByKeyword(keyword,pageNo)
            .flatMap { baseFunc(it) }
    }

    /* 获取商品详情 */
    override fun getGoodsDetail(goodsId: Int): Observable<Goods> {
        return repository.getGoodsDetail(goodsId)
            .flatMap { baseFunc(it) }
    }
}