package com.away0x.latte.ec.icon

import com.joanzapata.iconify.IconFontDescriptor

class FontEcModule : IconFontDescriptor {
    override fun ttfFileName(): String {
        return "iconfont.ttf"
    }

    override fun characters(): Array<EcIcons> {
        return EcIcons.values()
    }

}