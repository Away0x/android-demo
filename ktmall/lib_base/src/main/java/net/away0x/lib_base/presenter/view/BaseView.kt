package net.away0x.lib_base.presenter.view

interface BaseView {

    fun showLoading()
    fun hideLoading()
    fun onError(text: String)

}