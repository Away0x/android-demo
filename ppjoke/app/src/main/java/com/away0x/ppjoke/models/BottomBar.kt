package com.away0x.ppjoke.models

/**
 * 底部 tab
 */
data class BottomBar(
    // 被选中的颜色
    val activeColor: String,
    // 默认颜色
    val inActiveColor: String,
    // 默认被选中的 tab id
    val selectTab: Int,
    val tabs: List<Tab>
)

data class Tab(
    val enable: Boolean,
    val index: Int,
    val pageUrl: String,
    val size: Int,
    val tintColor: String?,
    val title: String
)