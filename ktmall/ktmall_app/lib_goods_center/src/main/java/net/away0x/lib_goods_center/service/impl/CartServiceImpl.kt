package net.away0x.lib_goods_center.service.impl

import io.reactivex.Observable
import net.away0x.lib_base.rx.baseFunc
import net.away0x.lib_base.rx.baseFuncBoolean
import net.away0x.lib_goods_center.data.protocol.CartGoods
import net.away0x.lib_goods_center.data.repository.CartRepository
import net.away0x.lib_goods_center.service.CartService
import javax.inject.Inject

/* 购物车 业务层实现类 */
class CartServiceImpl @Inject constructor(): CartService {

    @Inject
    lateinit var repository: CartRepository

    /* 加入购物车  */
    override fun addCart(goodsId: Int, goodsDesc: String, goodsIcon: String, goodsPrice: Long, goodsCount: Int, goodsSku: String): Observable<Int> {
        return repository.addCart(goodsId,goodsDesc,goodsIcon,goodsPrice,
            goodsCount,goodsSku)
            .flatMap { baseFunc(it) }
    }

    /* 获取购物车列表 */
    override fun getCartList(): Observable<MutableList<CartGoods>?> {
        return repository.getCartList()
            .flatMap { baseFunc(it) }
    }

    /* 删除购物车商品 */
    override fun deleteCartList(list: List<Int>): Observable<Boolean> {
        return repository.deleteCartList(list)
            .flatMap(::baseFuncBoolean)
    }

    /* 提交购物车商品 */
    override fun submitCart(list: MutableList<CartGoods>, totalPrice: Long): Observable<Int> {
        return repository.submitCart(list,totalPrice)
            .flatMap { baseFunc(it) }
    }

}