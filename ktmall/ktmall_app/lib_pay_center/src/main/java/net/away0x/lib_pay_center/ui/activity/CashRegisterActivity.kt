package net.away0x.lib_pay_center.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.alipay.sdk.app.EnvUtils
import com.alipay.sdk.app.PayTask
import kotlinx.android.synthetic.main.activity_cash_register.*
import net.away0x.lib_base.common.ProviderConstant
import net.away0x.lib_base.common.RouterPath
import net.away0x.lib_base.ext.onClick
import net.away0x.lib_base.ui.activity.BaseMvpActivity
import net.away0x.lib_base.utils.YuanFenConverter
import net.away0x.lib_pay_center.R
import net.away0x.lib_pay_center.injection.component.DaggerPayComponent
import net.away0x.lib_pay_center.injection.module.PayModule
import net.away0x.lib_pay_center.presenter.PayPresenter
import net.away0x.lib_pay_center.presenter.view.PayView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

/*
    收银台界面
 */
@Route(path = RouterPath.PaySDK.PATH_PAY)
class CashRegisterActivity: BaseMvpActivity<PayPresenter>(), PayView, View.OnClickListener {
    /*
        Dagger注册
     */
    override fun injectComponent() {
        DaggerPayComponent
            .builder()
            .activityComponent(activityComponent)
            .payModule(PayModule())
            .build()
            .inject(this)

        mPresenter.mView = this
    }

    //订单号
    var mOrderId:Int = 0
    //订单总价格
    var mTotalPrice:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash_register)

        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX)

        ARouter.getInstance().inject(this)

        initView()
        initData()
    }

    /*
        初始化数据
     */
    private fun initData() {
        mOrderId = intent.getIntExtra(ProviderConstant.KEY_ORDER_ID,-1)
        mTotalPrice = intent.getLongExtra(ProviderConstant.KEY_ORDER_PRICE,-1)

        mTotalPriceTv.text = YuanFenConverter.changeF2YWithUnit(mTotalPrice)
    }

    /*
        初始化视图
     */
    private fun initView() {
        mAlipayTypeTv.isSelected = true
        mAlipayTypeTv.onClick(this)
        mWeixinTypeTv.onClick(this)
        mBankCardTypeTv.onClick(this)
        mPayBtn.onClick(this)
    }

    /*
        获取支付签名回调
     */
    override fun onGetSignResult(result: String) {
        doAsync {
            val resultMap:Map<String,String> = PayTask(this@CashRegisterActivity).payV2(result,true)
            uiThread {
                if (resultMap["resultStatus"].equals("9000")){
                    mPresenter.payOrder(mOrderId)
                }else{
                    toast("支付失败: ${resultMap["memo"]}")
                }
            }

        }
    }

    /*
        支付订单回调
     */
    override fun onPayOrderResult(result: Boolean) {
        toast("支付成功")
        finish()
    }

    /*
        点击事件
     */
    override fun onClick(v: View) {
        when(v.id){
            R.id.mAlipayTypeTv -> {updatePayType(true,false,false)}
            R.id.mWeixinTypeTv -> {updatePayType(false,true,false)}
            R.id.mBankCardTypeTv -> {updatePayType(false,false,true)}
            R.id.mPayBtn -> {
                mPresenter.getPaySign(mOrderId,mTotalPrice)
            }
        }
    }

    /*
        选择支付类型，UI变化
     */
    private fun updatePayType(isAliPay:Boolean,isWeixinPay:Boolean,isBankCardPay:Boolean){
        mAlipayTypeTv.isSelected = isAliPay
        mWeixinTypeTv.isSelected = isWeixinPay
        mBankCardTypeTv.isSelected = isBankCardPay
    }
}
