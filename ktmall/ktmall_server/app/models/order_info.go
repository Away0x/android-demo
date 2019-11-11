package models

import (
	"ktmall/common/serializer"
)

type (
	// 订单状态
	OrderStatus uint
	// 支付状态
	OrderPayType uint
)

const (
	OrderInfoTableName = "order_info"
)

const (
	OrderStatusWaitPay     OrderStatus = 1 // 待支付
	OrderStatusWaitConfirm OrderStatus = 2 // 待收货
	OrderStatusCompleted   OrderStatus = 3 // 已完成
	OrderStatusCanceled    OrderStatus = 4 // 已取消
)

const (
	OrderPayTypeWait OrderPayType = 0 // 待支付
	OrderPayTypeOver OrderPayType = 1 // 已支付
)

// 订单信息
type OrderInfo struct {
	BaseModel
	UserId      uint
	ShipId      uint
	TotalPrice  int
	PayType     OrderPayType
	OrderStatus OrderStatus
}

func (OrderInfo) TableName() string {
	return OrderInfoTableName
}

func (o *OrderInfo) Serialize() serializer.Data {
	// var shipAddress: ShipAddress?,
	// val orderGoodsList: MutableList<OrderGoods>

	return serializer.Data{
		"id":          o.ID,
		"payType":     o.PayType,
		"totalPrice":  o.TotalPrice,
		"orderStatus": o.OrderStatus,
	}
}
