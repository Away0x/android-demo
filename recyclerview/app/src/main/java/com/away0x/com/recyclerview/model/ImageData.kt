package com.away0x.com.recyclerview.model

data class ImageData(
    val status: Int,
    val msg: String,
    val data: List<DataBean>
)

data class DataBean(
    val id: Int,
    val name: String,
    val picSmall: String,
    val description: String,
    val learner: Int
)