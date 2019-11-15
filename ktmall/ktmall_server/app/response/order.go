package response

import (
	"ktmall/app/models"
)

type OrderDetailResp struct {
	models.OrderInfoSerialize
	// 存储指针，无值时为就 nil 就不会序列化为一个都为空值的 struct
	ShipAddress    *models.AddressSerializer     `json:"shipAddress"`
	OrderGoodsList []models.OrderGoodsSerializer `json:"orderGoodsList"`
}

type OrderListResp []OrderDetailResp
