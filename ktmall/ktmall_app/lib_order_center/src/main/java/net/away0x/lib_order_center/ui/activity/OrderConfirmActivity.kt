package net.away0x.lib_order_center.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.eightbitlab.rxbus.Bus
import com.eightbitlab.rxbus.registerInBus
import kotlinx.android.synthetic.main.activity_order_confirm.*
import net.away0x.lib_base.common.ProviderConstant
import net.away0x.lib_base.common.RouterPath
import net.away0x.lib_base.ext.onClick
import net.away0x.lib_base.ext.setVisible
import net.away0x.lib_base.ui.activity.BaseMvpActivity
import net.away0x.lib_base.utils.YuanFenConverter
import net.away0x.lib_order_center.R
import net.away0x.lib_order_center.data.protocol.Order
import net.away0x.lib_order_center.event.SelectAddressEvent
import net.away0x.lib_order_center.injection.component.DaggerOrderComponent
import net.away0x.lib_order_center.injection.module.OrderModule
import net.away0x.lib_order_center.presenter.OrderConfirmPresenter
import net.away0x.lib_order_center.presenter.view.OrderConfirmView
import net.away0x.lib_order_center.ui.adapter.OrderGoodsAdapter
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/* 订单确认页 */
@Route(path = RouterPath.OrderCenter.PATH_ORDER_CONFIRM)
class OrderConfirmActivity: BaseMvpActivity<OrderConfirmPresenter>(), OrderConfirmView {

    @JvmField
    @Autowired(name = ProviderConstant.KEY_ORDER_ID)
    var mOrderId: Int = 0

    private lateinit var mAdapter: OrderGoodsAdapter
    private var mCurrentOrder: Order? = null

    /* Dagger注册 */
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
        setContentView(R.layout.activity_order_confirm)

        initView()
        initObserve()
        loadData()
    }

    /* 初始化视图 */
    private fun initView() {
        mShipView.onClick {
            startActivity<ShipAddressActivity>()
        }
        mSelectShipTv.onClick {
            startActivity<ShipAddressActivity>()
        }

        mSubmitOrderBtn.onClick {
            mCurrentOrder?.let {
                mPresenter.submitOrder(it)
            }
        }

        // 订单中商品列表
        mOrderGoodsRv.layoutManager = LinearLayoutManager(this)
        mAdapter = OrderGoodsAdapter(this)
        mOrderGoodsRv.adapter = mAdapter
    }

    /* 初始化选择收货人事件监听 */
    private fun initObserve() {
        Bus.observe<SelectAddressEvent>()
            .subscribe{
                    t: SelectAddressEvent ->
                run {
                    mCurrentOrder?.let {
                        it.shipAddress = t.address
                    }
                    updateAddressView()
                }
            }
            .registerInBus(this)
    }

    /* 加载订单数据 */
    private fun loadData() {
        mPresenter.getOrderById(mOrderId)
    }

    /* 获取订单回调 */
    override fun onGetOrderByIdResult(result: Order) {
        mCurrentOrder = result
        mAdapter.setData(result.orderGoodsList)
        mTotalPriceTv.text = "合计：${YuanFenConverter.changeF2YWithUnit(result.totalPrice)}"

        updateAddressView()
    }

    /* 取消事件监听 */
    override fun onDestroy() {
        super.onDestroy()
        Bus.unregister(this)
    }

    /*
        根据是否有收货人信息，更新视图
     */
    private fun updateAddressView() {
        mCurrentOrder?.let {
            if (it.shipAddress == null){
                mSelectShipTv.setVisible(true)
                mShipView.setVisible(false)
            }else{
                mSelectShipTv.setVisible(false)
                mShipView.setVisible(true)

                mShipNameTv.text = it.shipAddress!!.shipUserName + "  "+
                        it.shipAddress!!.shipUserMobile
                mShipAddressTv.text = it.shipAddress!!.shipAddress
            }
        }
    }

    /* 提交订单回调 */
    override fun onSubmitOrderResult(result: Boolean) {
        toast("订单提交成功")
        ARouter.getInstance().build(RouterPath.PaySDK.PATH_PAY)
            .withInt(ProviderConstant.KEY_ORDER_ID,mCurrentOrder!!.id)
            .withLong(ProviderConstant.KEY_ORDER_PRICE,mCurrentOrder!!.totalPrice)
            .navigation()

        finish()
    }

}
