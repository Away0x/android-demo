package handler

import (
	"ktmall/app/context"
)

/// 订单支付相关接口

// 获取支付宝支付签名
func OrderGetPaySign(c *context.AppContext) (err error) {
	return nil
}

// 刷新订单状态，已支付
func OrderPay(c *context.AppContext) (err error) {
	return nil
}

// 取消订单
func OrderCancel(c *context.AppContext) (err error) {
	return nil
}

// 确认订单
func OrderConfirm(c *context.AppContext) (err error) {
	return nil
}

// 根据 ID 获取订单
func OrderDetail(c *context.AppContext) (err error) {
	return nil
}

// 根据订单状态查询查询订单列表
func OrderList(c *context.AppContext) (err error) {
	return nil
}

// 提交订单
func OrderSubmit(c *context.AppContext) error {
	return nil
}
