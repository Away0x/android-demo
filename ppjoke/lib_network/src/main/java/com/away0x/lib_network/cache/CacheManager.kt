package com.away0x.lib_network.cache

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object CacheManager {

    /**
     * 反序列，把二进制转成 java object 对象
     */
    private fun toObject(data: ByteArray): Any? {
        ObjectInputStream(ByteArrayInputStream(data)).use {
            return it.readObject()
        }
    }

    /**
     * 序列化存储，将数据转为二进制
     */
    fun <T> toByteArray(body: T): ByteArray {
        val byte = ByteArrayOutputStream()
        ObjectOutputStream(byte).use {
            it.writeObject(body)
            it.flush()
            return byte.toByteArray()
        }
    }

    /**
     * 缓存数据
     */
    fun <T> save(key: String, body: T) {
        val cache = Cache(key = key, data = toByteArray(body))
        CacheDataBase.get().cacheDao().save(cache)
    }

    /**
     * 获取指定 key 的缓存
     */
    fun get(key: String): Any? {
        val cache = CacheDataBase.get().cacheDao().getCache(key)
        return toObject(cache.data)
    }

}