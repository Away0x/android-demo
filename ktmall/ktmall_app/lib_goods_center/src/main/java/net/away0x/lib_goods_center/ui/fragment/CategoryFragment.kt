package net.away0x.lib_goods_center.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kennyc.view.MultiStateView
import kotlinx.android.synthetic.main.fragment_category.*
import net.away0x.lib_base.ext.startLoading
import net.away0x.lib_base.ui.adapter.BaseRecyclerViewAdapter
import net.away0x.lib_base.ui.fragment.BaseMvpFragment

import net.away0x.lib_goods_center.R
import net.away0x.lib_goods_center.common.GoodsConstant
import net.away0x.lib_goods_center.data.protocol.Category
import net.away0x.lib_goods_center.injection.component.DaggerCategoryComponent
import net.away0x.lib_goods_center.injection.module.CategoryModule
import net.away0x.lib_goods_center.presenter.CategoryPresenter
import net.away0x.lib_goods_center.presenter.view.CategoryView
import net.away0x.lib_goods_center.ui.activity.GoodsActivity
import net.away0x.lib_goods_center.ui.adapter.SecondCategoryAdapter
import net.away0x.lib_goods_center.ui.adapter.TopCategoryAdapter
import org.jetbrains.anko.support.v4.startActivity

/** 商品分类 Fragment */
class CategoryFragment : BaseMvpFragment<CategoryPresenter>(), CategoryView {

    // 一级分类 Adapter
    lateinit var topAdapter: TopCategoryAdapter

    // 二级分类 Adapter
    lateinit var secondAdapter: SecondCategoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        loadData()
    }

    /* Dagger 注册 */
    override fun injectComponent() {
        DaggerCategoryComponent.builder()
            .activityComponent(activityComponent)
            .categoryModule(CategoryModule())
            .build()
            .inject(this)

        mPresenter.mView = this
    }

    override fun onGetCategoryResult(result: MutableList<Category>?) {
        if (result != null && result.size > 0) {
            if (result[0].parentId == 0) {
                result[0].isSelected = true
                topAdapter.setData(result)
                mPresenter.getCategory(result[0].id)
            } else {
                secondAdapter.setData(result)
                mTopCategoryIv.visibility = View.VISIBLE
                mCategoryTitleTv.visibility = View.VISIBLE
                mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
            }
        } else {
            // 没有数据
            mTopCategoryIv.visibility = View.GONE
            mCategoryTitleTv.visibility = View.GONE
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    /* 初始化视图 */
    private fun initView() {
        mTopCategoryRv.layoutManager = LinearLayoutManager(context)
        topAdapter = TopCategoryAdapter(context!!)
        mTopCategoryRv.adapter = topAdapter
        // 单项点击事件
        topAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Category> {
            override fun onItemClick(item: Category, position: Int) {
                for (category in topAdapter.dataList) {
                    category.isSelected = item.id == category.id
                }
                topAdapter.notifyDataSetChanged()

                loadData(item.id)
            }
        })

        mSecondCategoryRv.layoutManager = GridLayoutManager(context, 3)
        secondAdapter = SecondCategoryAdapter(context!!)
        mSecondCategoryRv.adapter = secondAdapter
        secondAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Category> {
            override fun onItemClick(item: Category, position: Int) {
                startActivity<GoodsActivity>(GoodsConstant.KEY_CATEGORY_ID to item.id)
            }
        })
    }

    /* 加载数据 */
    private fun loadData(parentId: Int = 0) {
        if (parentId != 0) {
            mMultiStateView.startLoading()
        }

        mPresenter.getCategory(parentId)
    }

}
