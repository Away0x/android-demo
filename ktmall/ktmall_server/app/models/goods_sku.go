package models

import (
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

type GoodsSkuSerializer struct {
	ID      uint     `json:"id"`
	Title   string   `json:"title"`
	Content []string `json:"content"`
}

func (GoodsSku) TableName() string {
	return GoodsSkuTableName
}

func (m *GoodsSku) Serialize() GoodsSkuSerializer {
	return GoodsSkuSerializer{
		ID:      m.ID,
		Title:   m.Title,
		Content: strings.Split(m.Content, ","),
	}
}
