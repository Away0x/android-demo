package com.away0x.hi.library.log.printer;

import androidx.annotation.NonNull;

import com.away0x.hi.library.log.HiLogConfig;

/**
 * 日志打印接口
 */
public interface HiLogPrinter {

    void print(@NonNull HiLogConfig config, int level, String tag, @NonNull String printString);

}
