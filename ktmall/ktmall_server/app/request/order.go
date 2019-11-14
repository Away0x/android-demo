package request

import (
	"ktmall/app/models"
)

type OrderListReq struct {
	OrderStatus models.OrderStatus `query:"orderStatus"`
}
