package net.away0x.lib_base.rx

class BaseException(
    val status: Int,
    val msg: String
): Throwable() {
}