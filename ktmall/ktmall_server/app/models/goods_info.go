package models

import (
	"ktmall/common/serializer"
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

func (GoodsInfo) TableName() string {
	return GoodsInfoTableName
}

func (g *GoodsInfo) Serialize() serializer.Data {
	//val maxPage:Int//最大页码

	return serializer.Data{
		"id":           g.ID,
		"categoryId":   g.CategoryId,
		"desc":         g.Desc,
		"defaultIcon":  g.DefaultIcon,
		"defaultPrice": g.DefaultPrice,
		"banner":       strings.Split(g.Banner, ","),
		"detailOne":    g.DetailOne,
		"detailTwo":    g.DetailTwo,
		"salesCount":   g.SalesCount,
		"stockCount":   g.StockCount,
		"code":         g.Code,
		"defaultSku":   g.DefaultSku,
	}
}
