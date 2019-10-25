package net.away0x.lib_user_center.injection.module

import dagger.Module
import dagger.Provides
import net.away0x.lib_user_center.service.UserService
import net.away0x.lib_user_center.service.impl.UserServiceImpl

@Module
class UserModule {

    @Provides
    fun providesUserService(service: UserServiceImpl): UserService {
        return service
    }

}