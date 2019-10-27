package models

import (
	"ktmall/common/serializer"
)

const (
	CartGoodsTableName = "cart_goods"
)

// 购物车
type CartGoods struct {
	BaseModel
	GoodsId    uint
	GoodsDesc  string
	GoodsIcon  string
	GoodsPrice string
	GoodsCount uint
	GoodsSku   string

	UserId uint
}

func (CartGoods) TableName() string {
	return CartGoodsTableName
}

func (c *CartGoods) Serialize() serializer.Data {
	return serializer.Data{
		"id":         c.ID,
		"goodsId":    c.GoodsId,
		"goodsDesc":  c.GoodsDesc,
		"goodsIcon":  c.GoodsIcon,
		"goodsPrice": c.GoodsPrice,
		"goodsCount": c.GoodsCount,
		"goodsSku":   c.GoodsSku,
		"isSelected": false,
	}
}
