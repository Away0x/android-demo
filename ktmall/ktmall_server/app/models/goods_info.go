package models

const (
	GoodsInfoTableName = "goods_info"
)

// 商品信息
type GoodsInfo struct {
	BaseModel
	CategoryId   uint
	Desc         string
	DefaultIcon  string
	DefaultPrice string
	Banner       string `sql:"type:text"`
	DetailOne    string
	DetailTwo    string
	SalesCount   uint
	StockCount   uint
	Code         string
	DefaultSku   string
}

func (GoodsInfo) TableName() string {
	return GoodsInfoTableName
}
