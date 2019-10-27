package models

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
	UserId     uint
	GoodsSku   string
}

func (CartGoods) TableName() string {
	return CartGoodsTableName
}
