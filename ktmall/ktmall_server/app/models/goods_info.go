package models

import (
	"strings"
)

const (
	GoodsInfoTableName = "goods_info"
)

// 商品信息
type GoodsInfo struct {
	BaseModel
	Desc         string // 商品描述
	DefaultIcon  string // 默认图标
	DefaultPrice string // 默认价格
	Banner       string `sql:"type:text"` // 商品 banner 图
	DetailOne    string // 商品详情一图
	DetailTwo    string // 商品详情二图
	SalesCount   uint   // 商品销量
	StockCount   uint   // 商品剩余量
	Code         string // 商品编号
	DefaultSku   string // 默认 SKU

	CategoryId uint // 分类 ID
}

type GoodSerializer struct {
	ID           uint     `json:"id"`
	CategoryId   uint     `json:"categoryId"`
	Desc         string   `json:"desc"`
	DefaultIcon  string   `json:"defaultIcon"`
	DefaultPrice string   `json:"defaultPrice"`
	Banner       []string `json:"banner"`
	DetailOne    string   `json:"detailOne"`
	DetailTwo    string   `json:"detailTwo"`
	SalesCount   uint     `json:"salesCount"`
	StockCount   uint     `json:"stockCount"`
	Code         string   `json:"code"`
	DefaultSku   string   `json:"defaultSku"`
}

func (GoodsInfo) TableName() string {
	return GoodsInfoTableName
}

func (m *GoodsInfo) Serialize() GoodSerializer {
	return GoodSerializer{
		ID:           m.ID,
		CategoryId:   m.CategoryId,
		Desc:         m.Desc,
		DefaultIcon:  m.DefaultIcon,
		DefaultPrice: m.DefaultPrice,
		Banner:       strings.Split(m.Banner, ","),
		DetailOne:    m.DetailOne,
		DetailTwo:    m.DetailTwo,
		SalesCount:   m.SalesCount,
		StockCount:   m.StockCount,
		Code:         m.Code,
		DefaultSku:   m.DefaultSku,
	}
}
