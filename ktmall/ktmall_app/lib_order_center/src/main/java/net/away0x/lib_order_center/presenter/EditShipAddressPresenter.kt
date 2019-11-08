package net.away0x.lib_order_center.presenter

import net.away0x.lib_base.ext.execute
import net.away0x.lib_base.presenter.BasePresenter
import net.away0x.lib_base.rx.BaseException
import net.away0x.lib_order_center.data.protocol.ShipAddress
import net.away0x.lib_order_center.presenter.view.EditShipAddressView
import net.away0x.lib_order_center.service.ShipAddressService
import javax.inject.Inject

/*
    编辑收货人信息Presenter
 */
class EditShipAddressPresenter @Inject constructor() : BasePresenter<EditShipAddressView>() {

    @Inject
    lateinit var shipAddressService: ShipAddressService


    /*
        添加收货人信息
     */
    fun addShipAddress(shipUserName: String, shipUserMobile: String, shipAddress: String) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        shipAddressService.addShipAddress(shipUserName, shipUserMobile, shipAddress).execute(lifecycleProvider, {
            mView.hideLoading()
            mView.onAddShipAddressResult(it)
        }, {
            if (it is BaseException) {
                mView.onError(it.msg)
            }
            mView.hideLoading()
        })
    }

    /*
        修改收货人信息
     */
    fun editShipAddress(address:ShipAddress) {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()
        shipAddressService.editShipAddress(address).execute(lifecycleProvider, {
            mView.hideLoading()
            mView.onEditShipAddressResult(it)
        }, {
            if (it is BaseException) {
                mView.onError(it.msg)
            }
            mView.hideLoading()
        })

    }


}
