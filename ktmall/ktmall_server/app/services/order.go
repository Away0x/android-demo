package services

import (
	"ktmall/app/models"
	"ktmall/common/serializer"

	"github.com/jinzhu/gorm"
)

type OrderService struct {
	DB *gorm.DB
}

// 以后优化
func (o OrderService) OrderList(status models.OrderStatus, userId uint) []map[string]interface{} {
	var (
		err       error
		result    = make([]map[string]interface{}, 0)
		orderList = make([]*models.OrderInfo, 0)
	)

	// 获取订单信息
	if status == 0 {
		err = o.DB.Where("user_id = ?", userId).Find(&orderList).Error
	} else {
		err = o.DB.Where("user_id = ? AND order_status = ?", userId, status).Find(&orderList).Error
	}
	if err != nil {
		return result
	}

	for _, order := range orderList {
		v := order.Serialize()

		// 获取地址
		addresses := make([]*models.ShipAddress, 0)
		o.DB.Where("id = ?", order.ShipId).Find(&addresses)
		// 获取 order goods
		goods := make([]*models.OrderGoods, 0)
		o.DB.Where("order_id = ?", order.ID).Find(&goods)

		v["shipAddress"] = serializer.Serialize(addresses)
		v["orderGoodsList"] = serializer.Serialize(goods)

		result = append(result, v)
	}

	return result
}
