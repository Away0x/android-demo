package models

const (
	OrderInfoTableName = "order_info"
)

// 订单信息
type OrderInfo struct {
	BaseModel
	UserId      uint
	PayType     uint
	ShipId      uint
	TotalPrice  int
	OrderStatus int
}

func (OrderInfo) TableName() string {
	return OrderInfoTableName
}
