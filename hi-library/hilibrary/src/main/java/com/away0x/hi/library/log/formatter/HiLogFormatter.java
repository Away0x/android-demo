package com.away0x.hi.library.log.formatter;

/**
 * 日志格式化接口
 */
public interface HiLogFormatter<T> {

    String format(T data);

}
