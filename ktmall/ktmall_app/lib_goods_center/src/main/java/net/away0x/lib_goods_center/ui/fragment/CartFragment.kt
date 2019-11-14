package net.away0x.lib_goods_center.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import com.kennyc.view.MultiStateView
import kotlinx.android.synthetic.main.fragment_cart.*
import net.away0x.lib_base.common.AuthManager
import net.away0x.lib_base.common.ProviderConstant
import net.away0x.lib_base.common.RouterPath
import net.away0x.lib_base.ext.onClick
import net.away0x.lib_base.ext.setVisible
import net.away0x.lib_base.ext.startLoading
import net.away0x.lib_base.ui.fragment.BaseMvpFragment
import net.away0x.lib_base.utils.AppPrefsUtils
import net.away0x.lib_base.utils.YuanFenConverter

import net.away0x.lib_goods_center.R
import net.away0x.lib_goods_center.common.GoodsConstant
import net.away0x.lib_goods_center.data.protocol.CartGoods
import net.away0x.lib_goods_center.event.CartAllCheckedEvent
import net.away0x.lib_goods_center.event.UpdateCartSizeEvent
import net.away0x.lib_goods_center.event.UpdateTotalPriceEvent
import net.away0x.lib_goods_center.injection.component.DaggerCartComponent
import net.away0x.lib_goods_center.injection.module.CartModule
import net.away0x.lib_goods_center.presenter.CartListPresenter
import net.away0x.lib_goods_center.presenter.view.CartListView
import net.away0x.lib_goods_center.ui.adapter.CartGoodsAdapter
import org.jetbrains.anko.support.v4.toast

/* 购物车 Fragment */
class CartFragment : BaseMvpFragment<CartListPresenter>(), CartListView {

    private lateinit var mAdapter: CartGoodsAdapter

    private var mTotalPrice: Long = 0

    /* Dagger注册 */
    override fun injectComponent() {
        DaggerCartComponent
            .builder()
            .activityComponent(activityComponent)
            .cartModule(CartModule())
            .build()
            .inject(this)

        mPresenter.mView = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserve()
    }

    /* 加载数据 */
    override fun onStart() {
        super.onStart()
        loadData()
    }

    /* 初始化视图和事件 */
    private fun initView() {
        mCartGoodsRv.layoutManager = LinearLayoutManager(context)
        mAdapter = CartGoodsAdapter(context!!)
        mCartGoodsRv.adapter = mAdapter

        mHeaderBar.getRightView().onClick {
            refreshEditStatus()
        }

        //全选按钮事件
        mAllCheckedCb.onClick {
            for (item in mAdapter.dataList) {
                item.isSelected = mAllCheckedCb.isChecked
            }
            mAdapter.notifyDataSetChanged()
            updateTotalPrice()
        }

        //删除按钮事件
        mDeleteBtn.onClick {
            val cartIdList: MutableList<Int> = arrayListOf()
            mAdapter.dataList.filter { it.isSelected }
                .mapTo(cartIdList) { it.id }
            if (cartIdList.size == 0) {
                toast("请选择需要删除的数据")
            }else {
                mPresenter.deleteCartList(cartIdList)
            }
        }

        //结算按钮事件
        mSettleAccountsBtn.onClick {
            val cartGoodsList: MutableList<CartGoods> = arrayListOf()
            mAdapter.dataList.filter { it.isSelected }
                .mapTo(cartGoodsList){it}
            if (cartGoodsList.size == 0) {
                toast("请选择需要提交的数据")
            }else {
                mPresenter.submitCart(cartGoodsList,mTotalPrice)
            }
        }
    }

    /* 刷新是否为编辑状态 */
    private fun refreshEditStatus() {
        val isEditStatus = getString(R.string.common_edit) == mHeaderBar.getRightText()
        mTotalPriceTv.setVisible(isEditStatus.not())
        mSettleAccountsBtn.setVisible(isEditStatus.not())
        mDeleteBtn.setVisible(isEditStatus)

        mHeaderBar.getRightView().text = if (isEditStatus) getString(R.string.common_complete) else getString(R.string.common_edit)


    }

    /* 加载数据 */
    private fun loadData() {
        if (AuthManager.instance.isLogined()) {
            mMultiStateView.startLoading()
            mPresenter.getCartList()
        } else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    /* 获取购物车列表回调 */
    override fun onGetCartListResult(result: MutableList<CartGoods>?) {
        if (result != null && result.size > 0) {
            mAdapter.setData(result)
            mHeaderBar.getRightView().setVisible(true)
            mAllCheckedCb.isChecked = false
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        } else {
            mHeaderBar.getRightView().setVisible(false)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }

        //本地存储并发送事件刷新UI
        AppPrefsUtils.putInt(GoodsConstant.SP_CART_SIZE,result?.size?:0)
        Bus.send(UpdateCartSizeEvent())
        //更新总价
        updateTotalPrice()
    }

    /* 注册监听 */
    private fun initObserve() {
        Bus.observe<CartAllCheckedEvent>().subscribe { t: CartAllCheckedEvent ->
            run {
                mAllCheckedCb.isChecked = t.isAllChecked
                updateTotalPrice()
            }
        }
            .registerInBus(this)

        Bus.observe<UpdateTotalPriceEvent>().subscribe {
            updateTotalPrice()
        }
            .registerInBus(this)

    }

    /* 取消监听 */
    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

    /* 更新总价 */
    private fun updateTotalPrice() {
        mTotalPrice = mAdapter.dataList
            .filter { it.isSelected }
            .map { it.goodsCount * it.goodsPrice }
            .sum()

        mTotalPriceTv.text = "合计:${YuanFenConverter.changeF2YWithUnit(mTotalPrice)}"
    }

    /* 删除购物车回调 */
    override fun onDeleteCartListResult(result: Boolean) {
        toast("删除成功")
        refreshEditStatus()
        loadData()
    }

    /* 提交购物车回调 */
    override fun onSubmitCartListResult(result: Int) {
        ARouter.getInstance().build(RouterPath.OrderCenter.PATH_ORDER_CONFIRM)
            .withInt(ProviderConstant.KEY_ORDER_ID, result)
            .navigation()
    }

    /* 设置Back是否可见 */
    fun setBackVisible(isVisible:Boolean){
        mHeaderBar.getLeftView().setVisible(isVisible)
    }
}
