package net.away0x.lib_user_center.injection.module

import dagger.Module
import dagger.Provides
import net.away0x.lib_user_center.service.UploadService
import net.away0x.lib_user_center.service.impl.UploadServiceImpl

/* 上传 Module */
@Module
class UploadModule {

    @Provides
    fun provideUploadService(uploadService: UploadServiceImpl): UploadService {
        return uploadService
    }

}