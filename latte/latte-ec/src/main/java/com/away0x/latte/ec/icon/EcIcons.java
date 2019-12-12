package com.away0x.latte.ec.icon;

import com.joanzapata.iconify.Icon;

public enum EcIcons implements Icon {
    // 列举各个字体的字符
    icon_scan('\ue602'),
    icon_ali_pay('\ue606');

    private char character;

    EcIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
