package com.away0x.ppjoke.models

/**
 * 页面 nav destination 注解信息
 */
data class Destination(
    // 是否为首页
    val asStarter: Boolean,
    val className: String,
    val id: Int,
    val isFragment: Boolean,
    val needLogin: Boolean,
    val pageUrl: String
)