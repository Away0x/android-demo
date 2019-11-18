package net.away0x.lib_order_center.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_order_detail.*
import net.away0x.lib_base.common.ProviderConstant
import net.away0x.lib_base.common.RouterPath
import net.away0x.lib_base.ui.activity.BaseMvpActivity
import net.away0x.lib_base.utils.YuanFenConverter
import net.away0x.lib_order_center.R
import net.away0x.lib_order_center.data.protocol.Order
import net.away0x.lib_order_center.injection.component.DaggerOrderComponent
import net.away0x.lib_order_center.injection.module.OrderModule
import net.away0x.lib_order_center.presenter.OrderDetailPresenter
import net.away0x.lib_order_center.presenter.view.OrderDetailView
import net.away0x.lib_order_center.ui.adapter.OrderGoodsAdapter

/*
    订单详情
 */
@Route(path = RouterPath.MessageCenter.PATH_MESSAGE_ORDER)
class OrderDetailActivity : BaseMvpActivity<OrderDetailPresenter>(), OrderDetailView {

    private lateinit var mAdapter: OrderGoodsAdapter

    /*
        Dagger注册
     */
    override fun injectComponent() {
        DaggerOrderComponent
            .builder()
            .activityComponent(activityComponent)
            .orderModule(OrderModule())
            .build()
            .inject(this)

        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)

        initView()
        loadData()
    }

    /*
        初始化视图
     */
    private fun initView() {
        mOrderGoodsRv.layoutManager = LinearLayoutManager(this)
        mAdapter = OrderGoodsAdapter(this)
        mOrderGoodsRv.adapter = mAdapter
    }

    /*
        加载数据
     */
    private fun loadData() {
        mPresenter.getOrderById(intent.getIntExtra(ProviderConstant.KEY_ORDER_ID,-1))
    }

    /*
        获取订单回调
     */
    override fun onGetOrderByIdResult(result: Order) {
        if (result.shipAddress != null) {
            mShipNameTv.setContentText(result.shipAddress?.shipUserName ?: "")
            mShipMobileTv.setContentText(result.shipAddress?.shipUserMobile ?: "")
            mShipAddressTv.setContentText(result.shipAddress?.shipAddress ?: "")
            mTotalPriceTv.setContentText(YuanFenConverter.changeF2YWithUnit(result.totalPrice ?: 0))
        }

        mAdapter.setData(result.orderGoodsList)
    }

}