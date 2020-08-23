package com.away0x.hi.library.log;

import androidx.annotation.NonNull;

import com.away0x.hi.library.log.printer.HiLogPrinter;
import com.away0x.hi.library.log.utils.HiStackTraceUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Tips
 * 1. 打印堆栈信息
 * 2. File 输出
 * 3. 模拟控制台
 */
public class HiLog {

    private static final String HI_LOG_PACKAGE;

    static {
        String className = HiLog.class.getName();
        HI_LOG_PACKAGE = className.substring(0, className.lastIndexOf('.') + 1);
    }

    public static void v(Object... contents) {
        log(HiLogType.V, contents);
    }

    public static void vt(String tag, Object... contents) {
        log(HiLogType.V, tag, contents);
    }

    public static void d(Object... contents) {
        log(HiLogType.D, contents);
    }

    public static void dt(String tag, Object... contents) {
        log(HiLogType.D, tag, contents);
    }

    public static void i(Object... contents) {
        log(HiLogType.I, contents);
    }

    public static void it(String tag, Object... contents) {
        log(HiLogType.I, tag, contents);
    }

    public static void w(Object... contents) {
        log(HiLogType.W, contents);
    }

    public static void wt(String tag, Object... contents) {
        log(HiLogType.W, tag, contents);
    }

    public static void e(Object... contents) {
        log(HiLogType.E, contents);
    }

    public static void et(String tag, Object... contents) {
        log(HiLogType.E, tag, contents);
    }

    public static void a(Object... contents) {
        log(HiLogType.A, contents);
    }

    public static void at(String tag, Object... contents) {
        log(HiLogType.A, tag, contents);
    }

    public static void log(@HiLogType.TYPE int type, Object... contents) {
        log(type, HiLogManager.getInstance().getConfig().getGlobalTag(), contents);
    }

    public static void log(@HiLogType.TYPE int type, @NonNull String tag, Object... contents) {
        log(HiLogManager.getInstance().getConfig(), type, tag, contents);
    }

    public static void log(@NonNull HiLogConfig config, @HiLogType.TYPE int type, @NonNull String tag, Object... contents) {
        if (!config.enable()) {
            return;
        }

        StringBuilder sb = new StringBuilder();

        // 是否要包含线程信息
        if (config.includeThread()) {
            String threadInfo = HiLogConfig.HI_THREAD_FORMATTER.format(Thread.currentThread());
            sb.append(threadInfo).append("\n");
        }

        // 是否要包含堆栈信息
        if (config.stackTraceDepth() > 0) {
            String stackTrace = HiLogConfig.HI_STACK_TRACE_FORMATTER.format(
                    HiStackTraceUtil.getCroppedRealStackTrack(
                            new Throwable().getStackTrace(),
                            HI_LOG_PACKAGE,
                            config.stackTraceDepth()
                    )
            );
            sb.append(stackTrace).append("\n");
        }

        String body = parseBody(contents, config);
        sb.append(body);

        // 调用打印器
        List<HiLogPrinter> printers = config.printers() != null ? Arrays.asList(config.printers()) : HiLogManager.getInstance().getPrinters();
        if (printers == null) {
            return;
        }
        for (HiLogPrinter printer : printers) {
            printer.print(config, type, tag, sb.toString());
        }
    }

    private static String parseBody(@NonNull Object[] contents, @NonNull HiLogConfig config) {
        // 是否有自定义的 json 序列化器
        if (config.injectJsonParser() != null) {
            return config.injectJsonParser().toJson(contents);
        }

        StringBuilder sb = new StringBuilder();

        for (Object o : contents) {
            sb.append(o.toString()).append(";");
        }

        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

}
