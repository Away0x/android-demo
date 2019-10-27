package models

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
	OrderId    uint
}

func (OrderGoods) TableName() string {
	return OrderGoodsTableName
}
