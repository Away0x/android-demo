package models

const (
	GoodsSkuTableName = "goods_sku"
)

// 商品 sku
type GoodsSku struct {
	BaseModel
	GoodsId uint
	Title   string
	Content string
}

func (GoodsSku) TableName() string {
	return GoodsSkuTableName
}
