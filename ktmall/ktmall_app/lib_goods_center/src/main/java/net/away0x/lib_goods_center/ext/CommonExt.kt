package net.away0x.lib_goods_center.ext

import android.widget.EditText
import net.away0x.lib_goods_center.R
import org.jetbrains.anko.find
import ren.qinc.numberbutton.NumberButton


fun NumberButton.getEditText(): EditText {
    return find(R.id.text_count)
}
