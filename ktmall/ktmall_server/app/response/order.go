package response

import (
	"ktmall/app/models"
)

type OrderDetailResp struct {
	models.OrderInfoSerialize
	ShipAddress    models.AddressSerializer      `json:"shipAddress"`
	OrderGoodsList []models.OrderGoodsSerializer `json:"orderGoodsList"`
}

type OrderListResp []OrderDetailResp
