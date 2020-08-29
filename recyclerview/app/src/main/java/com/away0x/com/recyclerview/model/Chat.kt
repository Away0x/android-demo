package com.away0x.com.recyclerview.model

object ItemType {
    val SEND = 0
    val RECEIVE = 1
}

data class Chat(
    val content: String, // 内容
    val type: Int // 类型
)