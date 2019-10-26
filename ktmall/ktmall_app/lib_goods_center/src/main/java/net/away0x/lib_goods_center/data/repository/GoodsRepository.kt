package net.away0x.lib_goods_center.data.repository

import io.reactivex.Observable
import net.away0x.lib_base.data.net.RetrofitFactory
import net.away0x.lib_base.data.protocol.BaseResp
import net.away0x.lib_goods_center.data.api.GoodsApi
import net.away0x.lib_goods_center.data.protocol.GetGoodsDetailReq
import net.away0x.lib_goods_center.data.protocol.GetGoodsListByKeywordReq
import net.away0x.lib_goods_center.data.protocol.GetGoodsListReq
import net.away0x.lib_goods_center.data.protocol.Goods
import javax.inject.Inject

/* 商品数据层 */
class GoodsRepository @Inject constructor() {

    /*
        根据分类搜索商品
     */
    fun getGoodsList(categoryId: Int, pageNo: Int): Observable<BaseResp<MutableList<Goods>?>> {
        return RetrofitFactory.instance.create(GoodsApi::class.java).getGoodsList(GetGoodsListReq(categoryId, pageNo))
    }

    /*
        根据关键字搜索商品
     */
    fun getGoodsListByKeyword(keyword: String, pageNo: Int): Observable<BaseResp<MutableList<Goods>?>> {
        return RetrofitFactory.instance.create(GoodsApi::class.java).getGoodsListByKeyword(
            GetGoodsListByKeywordReq(keyword, pageNo)
        )
    }

    /*
        商品详情
     */
    fun getGoodsDetail(goodsId: Int): Observable<BaseResp<Goods>> {
        return RetrofitFactory.instance.create(GoodsApi::class.java).getGoodsDetail(
            GetGoodsDetailReq(goodsId)
        )
    }
}