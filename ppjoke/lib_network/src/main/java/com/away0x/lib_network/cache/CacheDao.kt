package com.away0x.lib_network.cache

import androidx.room.*

@Dao
interface CacheDao {

    /**
     * 插入一条数据，如果有冲突，按指定方式执行
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(cache: Cache): Long

    @Delete
    fun delete(cache: Cache)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(cache: Cache)

    @Query("select * from cache where `key` = :key")
    fun getCache(key: String): Cache

}