package models

import (
	"ktmall/common/serializer"
)

const (
	OrderInfoTableName = "order_info"
)

// 订单信息
type OrderInfo struct {
	BaseModel
	UserId      uint
	PayType     uint
	ShipId      uint
	TotalPrice  int
	OrderStatus int
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
