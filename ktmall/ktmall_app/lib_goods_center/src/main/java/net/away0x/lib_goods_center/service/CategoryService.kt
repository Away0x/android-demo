package net.away0x.lib_goods_center.service

import io.reactivex.Observable
import net.away0x.lib_goods_center.data.protocol.Category

/* 商品分类 业务层 接口 */
interface CategoryService {

    /* 获取分类 */
    fun getCategory(parentId: Int): Observable<MutableList<Category>?>

}
