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
	GoodsSku   string

	UserId uint
}

type CartGoodsSerializer struct {
	ID         uint   `json:"id"`
	GoodsId    uint   `json:"goodsId"`
	GoodsDesc  string `json:"goodsDesc"`
	GoodsIcon  string `json:"goodsIcon"`
	GoodsPrice string `json:"goodsPrice"`
	GoodsCount uint   `json:"goodsCount"`
	GoodsSku   string `json:"goodsSku"`
	IsSelected bool   `json:"isSelected"` // 默认 false
}

func (CartGoods) TableName() string {
	return CartGoodsTableName
}

func (m *CartGoods) Serialize() CartGoodsSerializer {
	return CartGoodsSerializer{
		ID:         m.ID,
		GoodsId:    m.GoodsId,
		GoodsDesc:  m.GoodsDesc,
		GoodsIcon:  m.GoodsIcon,
		GoodsPrice: m.GoodsPrice,
		GoodsCount: m.GoodsCount,
		GoodsSku:   m.GoodsSku,
		IsSelected: false,
	}
}
