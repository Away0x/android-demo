package net.away0x.lib_goods_center.injection.module

import dagger.Module
import dagger.Provides
import net.away0x.lib_goods_center.service.CategoryService
import net.away0x.lib_goods_center.service.impl.CategoryServiceImpl

/* 商品分类 Module */
@Module
class CategoryModule {

    @Provides
    fun provideCategoryService(categoryService: CategoryServiceImpl): CategoryService {
        return categoryService
    }

}
