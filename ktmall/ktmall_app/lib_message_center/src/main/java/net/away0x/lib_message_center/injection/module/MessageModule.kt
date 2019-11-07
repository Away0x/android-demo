package net.away0x.lib_message_center.injection.module

import dagger.Module
import dagger.Provides
import net.away0x.lib_message_center.service.MessageService
import net.away0x.lib_message_center.service.impl.MessageServiceImpl

/* 消息模块业务注入 */
@Module
class MessageModule {

    @Provides
    fun provideMessageService(messageService: MessageServiceImpl): MessageService {
        return  messageService
    }

}
