package net.away0x.lib_goods_center.data.protocol

/*
    商品数据类
 */
data class Goods(
        val id: Int,//商品ID
        val categoryId: Int,//分类ID
        val desc: String,//商品描述
        val defaultIcon: String,//默认图标
        val defaultPrice: Long,//默认价格
        val detailOne: String,//商品详情一图
        val detailTwo: String,//商品详情二图
        val salesCount: Int,//商品销量
        val stockCount: Int,//商品剩余量
        val code: String,//商品编号
        val defaultSku: String,//默认SKU
        val banner: List<String>,//商品banner图
        val goodsSku:List<GoodsSku>,//商品SKU
        val maxPage:Int//最大页码
)
