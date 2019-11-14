package models

type (
	// 订单状态
	OrderStatus uint
	// 支付状态
	OrderPayType uint
)

const (
	OrderInfoTableName = "order_info"
)

const (
	OrderStatusWaitPay     OrderStatus = 1 // 待支付
	OrderStatusWaitConfirm OrderStatus = 2 // 待收货
	OrderStatusCompleted   OrderStatus = 3 // 已完成
	OrderStatusCanceled    OrderStatus = 4 // 已取消
)

const (
	OrderPayTypeWait OrderPayType = 0 // 待支付
	OrderPayTypeOver OrderPayType = 1 // 已支付
)

// 订单信息
type OrderInfo struct {
	BaseModel
	UserId      uint
	ShipId      uint
	TotalPrice  int
	PayType     OrderPayType
	OrderStatus OrderStatus
}

type OrderInfoSerialize struct {
	ID          uint         `json:"id"`
	TotalPrice  int          `json:"totalPrice"`
	PayType     OrderPayType `json:"payType"`
	OrderStatus OrderStatus  `json:"orderStatus"`
}

func (OrderInfo) TableName() string {
	return OrderInfoTableName
}

func (m *OrderInfo) Serialize() OrderInfoSerialize {
	return OrderInfoSerialize{
		ID:          m.ID,
		TotalPrice:  m.TotalPrice,
		PayType:     m.PayType,
		OrderStatus: m.OrderStatus,
	}
}
