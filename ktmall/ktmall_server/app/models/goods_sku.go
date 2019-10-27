package models

import (
	"ktmall/common/serializer"
	"strings"
)

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

func (g *GoodsSku) Serialize() serializer.Data {
	return serializer.Data{
		"id":      g.ID,
		"title":   g.Title,
		"content": strings.Split(g.Content, ","),
	}
}
