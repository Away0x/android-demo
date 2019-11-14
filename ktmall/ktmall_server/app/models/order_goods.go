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

	OrderId uint
}

type OrderGoodsSerializer struct {
	ID         uint   `json:"id"`
	GoodsId    uint   `json:"goodsId"`
	GoodsDesc  string `json:"goodsDesc"`
	GoodsIcon  string `json:"goodsIcon"`
	GoodsPrice string `json:"goodsPrice"`
	GoodsCount uint   `json:"goodsCount"`
	GoodsSku   string `json:"goodsSku"`
	OrderId    uint   `json:"orderId"`
}

func (OrderGoods) TableName() string {
	return OrderGoodsTableName
}

func (m *OrderGoods) Serialize() OrderGoodsSerializer {
	return OrderGoodsSerializer{
		ID:         m.ID,
		GoodsId:    m.GoodsId,
		GoodsDesc:  m.GoodsDesc,
		GoodsIcon:  m.GoodsIcon,
		GoodsPrice: m.GoodsPrice,
		GoodsCount: m.GoodsCount,
		GoodsSku:   m.GoodsSku,
		OrderId:    m.OrderId,
	}
}
