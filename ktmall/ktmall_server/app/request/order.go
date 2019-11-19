package request

import (
	"ktmall/app/models"
)

type OrderListReq struct {
	OrderStatus models.OrderStatus `query:"orderStatus"`
}

type OrderGetPaySignReq struct {
	TotalPrice float64 `json:"totalPrice"`
	OrderId    uint    `json:"orderId"`
}
