package com.away0x.hi.library.log;

import com.away0x.hi.library.log.formatter.HiStackTraceFormatter;
import com.away0x.hi.library.log.formatter.HiThreadFormatter;
import com.away0x.hi.library.log.printer.HiLogPrinter;

/**
 * HiLog 配置
 */
public abstract class HiLogConfig {

    /**
     * 每一行可显示的最大长度
     */
    public static int MAX_LEN = 512;

    /**
     * 格式化器
     */
    public static HiStackTraceFormatter HI_STACK_TRACE_FORMATTER = new HiStackTraceFormatter();
    public static HiThreadFormatter HI_THREAD_FORMATTER = new HiThreadFormatter();

    /**
     * 允许用户注入自己的 json 序列化器
     */
    public JsonParser injectJsonParser() {
        return null;
    }

    /**
     * log 打印时，如不传递 tag，可使用这个全局的 tag
     */
    public String getGlobalTag() {
        return "HiLog";
    }

    /** 是否启用 log */
    public boolean enable() {
        return true;
    }

    /**
     * 日志中是否包含线程信息
     */
    public boolean includeThread() {
        return false;
    }

    /**
     * 堆栈信息的深度
     */
    public int stackTraceDepth() {
        return 5;
    }

    /**
     * 允许用户注册打印器
     */
    public HiLogPrinter[] printers() {
        return null;
    }

    public interface JsonParser {
        String toJson(Object src);
    }

}
