package com.away0x.hi.ui.tab.common;

import androidx.annotation.NonNull;
import androidx.annotation.Px;

public interface IHiTab<D> extends IHiTabLayout.OnTabSelectedListener<D> {

    void setHiTabInfo(@NonNull D data);

    /**
     * 动态修改某个 item 的大小
     */
    void resetHeight(@Px int height);

}
