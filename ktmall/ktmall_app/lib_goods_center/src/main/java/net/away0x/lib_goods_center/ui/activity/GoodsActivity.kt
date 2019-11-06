package net.away0x.lib_goods_center.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.kennyc.view.MultiStateView
import kotlinx.android.synthetic.main.activity_goods.*
import net.away0x.lib_base.ext.startLoading
import net.away0x.lib_base.ui.activity.BaseMvpActivity
import net.away0x.lib_base.ui.adapter.BaseRecyclerViewAdapter
import net.away0x.lib_goods_center.R
import net.away0x.lib_goods_center.common.GoodsConstant
import net.away0x.lib_goods_center.data.protocol.Goods
import net.away0x.lib_goods_center.injection.component.DaggerGoodsComponent
import net.away0x.lib_goods_center.injection.module.GoodsModule
import net.away0x.lib_goods_center.presenter.GoodsListPresenter
import net.away0x.lib_goods_center.presenter.view.GoodsListView
import net.away0x.lib_goods_center.ui.adapter.GoodsAdapter
import org.jetbrains.anko.startActivity

/** 商品列表 Activity */
class GoodsActivity : BaseMvpActivity<GoodsListPresenter>(), GoodsListView, BGARefreshLayout.BGARefreshLayoutDelegate {

    private lateinit var mGoodsAdapter: GoodsAdapter
    private var mCurrentPage: Int = 1
    private var mMaxPage: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods)

        initView()
        initRefreshLayout()
        loadData()
    }

    override fun injectComponent() {
        DaggerGoodsComponent
            .builder()
            .activityComponent(activityComponent)
            .goodsModule(GoodsModule())
            .build()
            .inject(this)

        mPresenter.mView = this
    }

    /* 初始化视图 */
    private fun initView() {
        mGoodsRv.layoutManager = GridLayoutManager(this, 2)
        mGoodsAdapter = GoodsAdapter(this)
        mGoodsRv.adapter = mGoodsAdapter

        mGoodsAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Goods> {
            override fun onItemClick(item: Goods, position: Int) {
                startActivity<GoodsDetailActivity>(GoodsConstant.KEY_GOODS_ID to item.id)
            }
        })
    }

    /* 初始化刷新视图 */
    private fun initRefreshLayout() {
        mRefreshLayout.setDelegate(this)
        val viewHolder = BGANormalRefreshViewHolder(this, true)
        viewHolder.setLoadMoreBackgroundColorRes(R.color.common_bg)
        viewHolder.setRefreshViewBackgroundColorRes(R.color.common_bg)
        mRefreshLayout.setRefreshViewHolder(viewHolder)
    }

    /* 加载数据 */
    private fun loadData() {
        if (intent.getIntExtra(GoodsConstant.KEY_SEARCH_GOODS_TYPE, 0) != 0) {
            mMultiStateView.startLoading()
            mPresenter.getGoodsListByKeyword(intent.getStringExtra(GoodsConstant.KEY_GOODS_KEYWORD), mCurrentPage)
        } else {
            mMultiStateView.startLoading()
            mPresenter.getGoodsList(intent.getIntExtra(GoodsConstant.KEY_CATEGORY_ID, 1), mCurrentPage)
        }
    }

    /* 获取列表后回调 */
    override fun onGetGoodsListResult(result: MutableList<Goods>?) {
        mRefreshLayout.endLoadingMore()
        mRefreshLayout.endRefreshing()
        if (result != null && result.size > 0) {
            mMaxPage = result[0].maxPage
            if (mCurrentPage == 1) {
                mGoodsAdapter.setData(result)
            } else {
                mGoodsAdapter.dataList.addAll(result)
                mGoodsAdapter.notifyDataSetChanged()
            }
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        } else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    /* 上拉加载更多 */
    override fun onBGARefreshLayoutBeginLoadingMore(refreshLayout: BGARefreshLayout?): Boolean {
        return if (mCurrentPage < mMaxPage) {
            mCurrentPage++
            loadData()
            true
        } else {
            false
        }
    }

    /* 下拉加载第一页 */
    override fun onBGARefreshLayoutBeginRefreshing(refreshLayout: BGARefreshLayout?) {
        mCurrentPage = 1
        loadData()
    }

}
