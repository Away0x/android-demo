package net.away0x.lib_order_center.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import net.away0x.lib_base.common.ProviderConstant
import net.away0x.lib_base.common.RouterPath
import net.away0x.lib_order_center.R

@Route(path = RouterPath.OrderCenter.PATH_ORDER_CONFIRM)
class OrderConfirmActivity : AppCompatActivity() {

    @Autowired(name = ProviderConstant.KEY_ORDER_ID)
    @JvmField
    var mOrderId:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirm)
    }
}
