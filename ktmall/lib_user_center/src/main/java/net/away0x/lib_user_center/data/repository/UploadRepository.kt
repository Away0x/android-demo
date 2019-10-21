package net.away0x.lib_user_center.data.repository

import io.reactivex.Observable
import net.away0x.lib_base.data.net.RetrofitFactory
import net.away0x.lib_base.data.protocol.BaseResp
import net.away0x.lib_user_center.data.api.UploadApi
import javax.inject.Inject

class UploadRepository @Inject constructor() {

    /* 获取七牛云上传凭证 */
    fun getUploadToken(): Observable<BaseResp<String>> {
        return RetrofitFactory.instance.create(UploadApi::class.java)
            .getUploadToken()
    }

}