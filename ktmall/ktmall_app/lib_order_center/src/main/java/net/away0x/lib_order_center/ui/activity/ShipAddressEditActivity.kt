package net.away0x.lib_order_center.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_ship_address_edit.*
import net.away0x.lib_base.ext.onClick
import net.away0x.lib_base.ui.activity.BaseMvpActivity
import net.away0x.lib_order_center.R
import net.away0x.lib_order_center.common.OrderConstant
import net.away0x.lib_order_center.data.protocol.ShipAddress
import net.away0x.lib_order_center.injection.component.DaggerShipAddressComponent
import net.away0x.lib_order_center.injection.module.ShipAddressModule
import net.away0x.lib_order_center.presenter.EditShipAddressPresenter
import net.away0x.lib_order_center.presenter.view.EditShipAddressView
import org.jetbrains.anko.toast

/*
    收货人编辑页面
 */
class ShipAddressEditActivity : BaseMvpActivity<EditShipAddressPresenter>(), EditShipAddressView {

    private var mAddress: ShipAddress? = null

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
        setContentView(R.layout.activity_ship_address_edit)

        initView()
        initData()
    }

    /*
        初始化视图
     */
    private fun initView() {

        mSaveBtn.onClick {
            if (mShipNameEt.text.isNullOrEmpty()){
                toast("名称不能为空")
                return@onClick
            }
            if (mShipMobileEt.text.isNullOrEmpty()){
                toast("电话不能为空")
                return@onClick
            }
            if (mShipAddressEt.text.isNullOrEmpty()){
                toast("地址不能为空")
                return@onClick
            }
            if (mAddress == null) {
                mPresenter.addShipAddress(mShipNameEt.text.toString(),
                    mShipMobileEt.text.toString(),
                    mShipAddressEt.text.toString())
            }else{
                mAddress!!.shipUserName = mShipNameEt.text.toString()
                mAddress!!.shipUserMobile = mShipMobileEt.text.toString()
                mAddress!!.shipAddress = mShipAddressEt.text.toString()
                mPresenter.editShipAddress(mAddress!!)
            }
        }
    }

    /*
        初始化数据
     */
    private fun initData() {
        mAddress = intent.getParcelableExtra(OrderConstant.KEY_SHIP_ADDRESS)
        mAddress?.let {
            mShipNameEt.setText(it.shipUserName)
            mShipMobileEt.setText(it.shipUserMobile)
            mShipAddressEt.setText(it.shipAddress)
        }

    }

    /*
        添加收货人信息回调
     */
    override fun onAddShipAddressResult(result: Boolean) {
        toast("添加地址成功")
        finish()
    }

    /*
        修改收货人信息回调
     */
    override fun onEditShipAddressResult(result: Boolean) {
        toast("修改地址成功")
        finish()
    }

}
