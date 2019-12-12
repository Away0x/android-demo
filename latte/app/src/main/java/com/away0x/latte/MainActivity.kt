package com.away0x.latte

import com.away0x.latte.core.activities.ProxyActivity
import com.away0x.latte.core.delegates.LatteDelegate

class MainActivity : ProxyActivity() {

    override fun setRootDelegate(): LatteDelegate {
        return MainDelegate()
    }

}
