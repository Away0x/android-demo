package net.away0x.lib_message_center.presenter

import net.away0x.lib_base.ext.execute
import net.away0x.lib_base.presenter.BasePresenter
import net.away0x.lib_base.rx.BaseException
import net.away0x.lib_message_center.presenter.view.MessageView
import net.away0x.lib_message_center.service.MessageService
import javax.inject.Inject

/* 消息列表 Presenter */
class MessagePresenter @Inject constructor() : BasePresenter<MessageView>() {

    @Inject
    lateinit var messageService: MessageService

    /* 获取消息列表 */
    fun getMessageList() {
        if (!checkNetWork()) {
            return
        }
        mView.showLoading()

        messageService.getMessageList().execute(lifecycleProvider, {
            mView.hideLoading()
            mView.onGetMessageResult(it)
        }, {
            if (it is BaseException) {
                mView.onError(it.msg)
            }
            mView.hideLoading()
        })

    }


}