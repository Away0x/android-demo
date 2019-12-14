package com.away0x.smartbutler.entity

// 快递查询实体
data class CourierData(
    val datetime: String, // 时间
    val remark: String, // 状态
    val zone: String // 城市
)