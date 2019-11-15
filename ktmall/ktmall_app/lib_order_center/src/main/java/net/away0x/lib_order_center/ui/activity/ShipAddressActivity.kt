package net.away0x.lib_order_center.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.eightbitlab.rxbus.Bus
import com.kennyc.view.MultiStateView
import kotlinx.android.synthetic.main.activity_ship_address.*
import net.away0x.lib_base.ext.onClick
import net.away0x.lib_base.ext.startLoading
import net.away0x.lib_base.ui.activity.BaseMvpActivity
import net.away0x.lib_base.ui.adapter.BaseRecyclerViewAdapter
import net.away0x.lib_order_center.R
import net.away0x.lib_order_center.common.OrderConstant
import net.away0x.lib_order_center.data.protocol.ShipAddress
import net.away0x.lib_order_center.event.SelectAddressEvent
import net.away0x.lib_order_center.injection.component.DaggerShipAddressComponent
import net.away0x.lib_order_center.injection.module.ShipAddressModule
import net.away0x.lib_order_center.presenter.ShipAddressPresenter
import net.away0x.lib_order_center.presenter.view.ShipAddressView
import net.away0x.lib_order_center.ui.adapter.ShipAddressAdapter
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/*
    收货人信息列表页
 */
class ShipAddressActivity: BaseMvpActivity<ShipAddressPresenter>(), ShipAddressView {

    private lateinit var mAdapter: ShipAddressAdapter

    /*
        Dagger注册
     */
    override fun injectComponent() {
        DaggerShipAddressComponent
            .builder()
            .activityComponent(activityComponent)
            .shipAddressModule(ShipAddressModule())
            .build()
            .inject(this)

        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ship_address)

        initView()
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    /*
        初始化视图
     */
    private fun initView() {
        mAddressRv.layoutManager = LinearLayoutManager(this)
        mAdapter = ShipAddressAdapter(this)
        mAddressRv.adapter = mAdapter

        //设置操作事件
        mAdapter.mOptClickListener = object : ShipAddressAdapter.OnOptClickListener{
            override fun onSetDefault(address: ShipAddress) {
                mPresenter.setDefaultShipAddress(address)
            }

            override fun onEdit(address: ShipAddress) {
                startActivity<ShipAddressEditActivity>(OrderConstant.KEY_SHIP_ADDRESS to address)

            }

            override fun onDelete(address: ShipAddress) {
                AlertView("删除", "确定删除该地址？", "取消", null, arrayOf("确定"),
                    this@ShipAddressActivity, AlertView.Style.Alert,
                    OnItemClickListener { o, position ->
                    if (position == 0){
                        mPresenter.deleteShipAddress(address.id)
                    }
                }

                ).show()
            }
        }

        //单项点击事件
        mAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<ShipAddress>{
            override fun onItemClick(item: ShipAddress, position: Int) {
                Bus.send(SelectAddressEvent(item))
                finish()
            }
        })


        mAddAddressBtn.onClick {
            startActivity<ShipAddressEditActivity>()
        }
    }

    /*
        加载数据
     */
    private fun loadData() {
        mMultiStateView.startLoading()
        mPresenter.getShipAddressList()
    }

    /*
        获取收货人信息回调
     */
    override fun onGetShipAddressResult(result: MutableList<ShipAddress>?) {
        if (result != null && result.size > 0) {
            mAdapter.setData(result)
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_CONTENT
        } else {
            mMultiStateView.viewState = MultiStateView.VIEW_STATE_EMPTY
        }
    }

    /*
        设置默认收货人回调
     */
    override fun onSetDefaultResult(result: Boolean) {
        toast("设置默认成功")
        loadData()
    }

    /*
        删除收货人回调
     */
    override fun onDeleteResult(result: Boolean) {
        toast("删除成功")
        loadData()
    }
}