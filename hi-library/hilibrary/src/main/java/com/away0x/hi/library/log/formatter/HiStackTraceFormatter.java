package com.away0x.hi.library.log.formatter;

import com.away0x.hi.library.log.formatter.HiLogFormatter;

/**
 * 堆栈信息格式化实现类
 */
public class HiStackTraceFormatter implements HiLogFormatter<StackTraceElement[]> {

    @Override
    public String format(StackTraceElement[] stackTrace) {
        StringBuilder sb = new StringBuilder(128);

        if (stackTrace == null || stackTrace.length == 0) {
            return null;
        } else if (stackTrace.length == 1) {
            return "\t- " + stackTrace[0].toString();
        }

        for (int i = 0, len = stackTrace.length; i < len; i++) {
            if (i == 0) {
                sb.append("stackTrace: \n");
            }

            if (i != len - 1) {
                sb.append("\t├── ");
                sb.append(stackTrace[i].toString());
                sb.append("\n");
            } else {
                sb.append("\t└── ");
                sb.append(stackTrace[i].toString());
            }
        }

        return sb.toString();
    }
}
