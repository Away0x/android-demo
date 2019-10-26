package net.away0x.lib_goods_center.data.protocol

/*
    按关键字搜索商品
 */
data class GetGoodsListByKeywordReq(
        val keyword: String,
        val pageNo: Int
)
