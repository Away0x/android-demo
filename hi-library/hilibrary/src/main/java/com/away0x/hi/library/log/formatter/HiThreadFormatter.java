package com.away0x.hi.library.log.formatter;

import com.away0x.hi.library.log.formatter.HiLogFormatter;

/**
 * 线程格式化实现类
 */
public class HiThreadFormatter implements HiLogFormatter<Thread> {

    @Override
    public String format(Thread data) {
        return "Thread:" + data.getName();
    }
}
