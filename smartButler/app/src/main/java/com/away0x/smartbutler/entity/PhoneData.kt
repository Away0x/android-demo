package com.away0x.smartbutler.entity

enum class CompanyType {
    Mobile, // 移动
    Unicom, // 联通
    Telecom // 电信
}

data class PhoneData(
    val type: CompanyType,
    val data: String
)