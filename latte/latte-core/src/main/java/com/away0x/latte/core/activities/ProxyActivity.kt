package com.away0x.latte.core.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.widget.ContentFrameLayout
import com.away0x.latte.core.R
import com.away0x.latte.core.delegates.LatteDelegate
import me.yokeyword.fragmentation.SupportActivity

abstract class ProxyActivity : SupportActivity() {

    // 设置根 delegate(fragment)
    abstract fun setRootDelegate(): LatteDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContainer(savedInstanceState)
    }

    @SuppressLint("RestrictedApi")
    private fun initContainer(savedInstanceState: Bundle?) {
        val container = ContentFrameLayout(this)
        container.id = R.id.delegate_container
        setContentView(container)

        if (savedInstanceState == null) {
            loadRootFragment(R.id.delegate_container, setRootDelegate())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        System.gc()
        System.runFinalization()
    }

}