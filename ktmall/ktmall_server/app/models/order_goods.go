package models

import (
	"ktmall/common/serializer"
)

const (
	OrderGoodsTableName = "order_goods"
)

// 订单商品
type OrderGoods struct {
	BaseModel
	GoodsId    uint
	GoodsDesc  string
	GoodsIcon  string
	GoodsPrice string
	GoodsCount uint
	GoodsSku   string

	OrderId uint
}

func (OrderGoods) TableName() string {
	return OrderGoodsTableName
}

func (o *OrderGoods) Serialize() serializer.Data {
	return serializer.Data{
		"id":         o.ID,
		"goodsId":    o.GoodsId,
		"goodsDesc":  o.GoodsDesc,
		"goodsIcon":  o.GoodsIcon,
		"goodsPrice": o.GoodsPrice,
		"goodsCount": o.GoodsCount,
		"goodsSku":   o.GoodsSku,
		"orderId":    o.OrderId,
	}
}
