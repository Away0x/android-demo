package com.away0x.lib_network.cache

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.away0x.lib_common.global.AppGlobals

@Database(
    entities = [Cache::class],
    version = 1,
    exportSchema = true
)
// 数据读取存储时的格式转换器，写入时将 Date 转换 Long 存储，读取时把 Long 转换为 Date 返回
//@TypeConverters(DateConverter::class)
abstract class CacheDataBase : RoomDatabase() {

    companion object {

        private var database: CacheDataBase? = null
            get() {
                if (field == null) {
                    /**
                     * 创建一个内存数据库，只存在内存中，进程被杀后，数据随之丢失: Room.inMemoryDatabaseBuilder()
                     */
                    field = Room.databaseBuilder(
                        AppGlobals.getApplication(),
                        CacheDataBase::class.java,
                        "ppjoke_cache"
                    )
                        .allowMainThreadQueries()                  // 是否允许在主线程进行查询
                        // .addCallback()                          // 打开和创建的回调
                        // .setQueryExecutor()                     // 设置查询时的线程池
                        // .openHelperFactory()                    // 设置数据库工厂
                        // .setJournalMode()                       // 设置 room 的日志模式
                        // .fallbackToDestructiveMigration()       // 数据库升级异常后根据指定版本进行回滚
                        // .addMigrations(CacheDataBase.migration) // 向数据库添加迁移，每次迁移都要有开始和最后版本，Room 将迁移到最新版本 (如果没有迁移对象，则数据库会重新创建)
                        .build()
                }

                return field
            }

        @Synchronized
        fun get(): CacheDataBase {
            return database!!
        }

        /**
         * 数据库迁移对象: 可以对数据库进行必要的更改
         */
//        private val migration: Migration = object : Migration(1, 3) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("alter table teacher rename to student")
//                database.execSQL("alter table teacher add column teacher_age INTEGER NOT NULL default 0")
//            }
//        }
    }

    abstract fun cacheDao(): CacheDao

}