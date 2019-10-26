package net.away0x.lib_goods_center.service.impl

import io.reactivex.Observable
import net.away0x.lib_base.rx.baseFunc
import net.away0x.lib_goods_center.data.protocol.Category
import net.away0x.lib_goods_center.data.repository.CategoryRepository
import net.away0x.lib_goods_center.service.CategoryService
import javax.inject.Inject

/* 商品分类 业务层 实现类 */
class CategoryServiceImpl @Inject constructor(): CategoryService {

    @Inject
    lateinit var repository: CategoryRepository

    /* 获取商品分类列表 */
    override fun getCategory(parentId: Int): Observable<MutableList<Category>?> {
        return repository.getCategory(parentId)
            .flatMap { baseFunc(it) }
    }

}