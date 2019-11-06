package net.away0x.lib_goods_center.ui.activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_search_goods.*
import net.away0x.lib_base.ext.onClick
import net.away0x.lib_base.ext.setVisible
import net.away0x.lib_base.ui.activity.BaseActivity
import net.away0x.lib_base.ui.adapter.BaseRecyclerViewAdapter
import net.away0x.lib_base.utils.AppPrefsUtils
import net.away0x.lib_goods_center.R
import net.away0x.lib_goods_center.common.GoodsConstant
import net.away0x.lib_goods_center.ui.adapter.SearchHistoryAdapter
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/*
    关键字搜索商品页
 */
class SearchGoodsActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mAdapter: SearchHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_goods)
        initView()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    /* 初始化视图 */
    private fun initView() {

        mLeftIv.onClick(this)
        mSearchTv.onClick(this)
        mClearBtn.onClick(this)
        //RecyclerView适配器
        mAdapter = SearchHistoryAdapter(this)
        mSearchHistoryRv.layoutManager = LinearLayoutManager(this)
        mSearchHistoryRv.adapter = mAdapter
        //item点击事件
        mAdapter.mItemClickListener = object : BaseRecyclerViewAdapter.OnItemClickListener<String> {
            override fun onItemClick(item: String, position: Int) {
                enterGoodsList(item)
            }
        }
    }

    /* 从SP中加载数据 */
    private fun loadData() {
        val set = AppPrefsUtils.getStringSet(GoodsConstant.SP_SEARCH_HISTORY)
        mNoDataTv.setVisible(set.isEmpty())
        mDataView.setVisible(set.isNotEmpty())
        if (set.isNotEmpty()) {
            val list = mutableListOf<String>()
            list.addAll(set)
            mAdapter.setData(list)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mLeftIv -> finish()
            //执行搜索
            R.id.mSearchTv -> doSearch()
            //清除历史记录
            R.id.mClearBtn -> {
                AppPrefsUtils.remove(GoodsConstant.SP_SEARCH_HISTORY)
                loadData()
            }
        }
    }

    //搜索
    private fun doSearch() {
        if (mKeywordEt.text.isNullOrEmpty()) {
            toast("请输入需要搜索的关键字")
        } else {
            val inputValue = mKeywordEt.text.toString()
            AppPrefsUtils.putStringSet(GoodsConstant.SP_SEARCH_HISTORY, mutableSetOf(inputValue))
            enterGoodsList(inputValue)
        }
    }

    /* 进入商品列表界面 */
    private fun enterGoodsList(value: String) {
        //输入不为空，进入商品列表
        startActivity<GoodsActivity>(
            GoodsConstant.KEY_SEARCH_GOODS_TYPE to GoodsConstant.SEARCH_GOODS_TYPE_KEYWORD,
            GoodsConstant.KEY_GOODS_KEYWORD to value
        )

    }
}