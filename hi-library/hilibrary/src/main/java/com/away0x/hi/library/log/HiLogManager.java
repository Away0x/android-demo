package com.away0x.hi.library.log;

import androidx.annotation.NonNull;

import com.away0x.hi.library.log.printer.HiLogPrinter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * HiLog 管理
 */
public class HiLogManager {

    private HiLogConfig config;

    private static HiLogManager instance;

    // 保存打印器
    private List<HiLogPrinter> printers = new ArrayList();

    private HiLogManager(HiLogConfig config, HiLogPrinter[] printers) {
        this.config = config;
        this.printers.addAll(Arrays.asList(printers));
    }

    public static HiLogManager getInstance() {
        return instance;
    }

    public static void init(@NonNull HiLogConfig config, HiLogPrinter... printers) {
        instance = new HiLogManager(config, printers);
    }

    public HiLogConfig getConfig() {
        return config;
    }

    public List<HiLogPrinter> getPrinters() {
        return printers;
    }

    public void addPrinter(HiLogPrinter printer) {
        printers.add(printer);
    }

    public void removePrinter(HiLogPrinter printer) {
        if (printer != null) {
            printers.remove(printer);
        }
    }

}
