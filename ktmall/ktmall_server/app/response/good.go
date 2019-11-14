package response

import (
	"ktmall/app/models"
)

type GoodListItemResp struct {
	models.GoodSerializer
	GoodsSku []models.GoodsSkuSerializer `json:"goodsSku"`
	MaxPage  uint                        `json:"maxPage"`
	AllCount uint                        `json:"allCount"`
}

type GoodListResp []GoodListItemResp

type GoodsDetailResp struct {
	models.GoodSerializer
	GoodsSku []models.GoodsSkuSerializer `json:"goodsSku"`
}
