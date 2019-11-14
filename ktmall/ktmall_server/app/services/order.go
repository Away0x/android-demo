package services

import (
	"ktmall/app/models"
	"ktmall/app/response"

	"github.com/jinzhu/gorm"
)

type OrderService struct {
	DB *gorm.DB
}

// 以后优化
func (o OrderService) OrderList(status models.OrderStatus, userId uint) response.OrderListResp {
	var (
		err       error
		result    = make(response.OrderListResp, 0)
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
		v := response.OrderDetailResp{
			OrderInfoSerialize: order.Serialize(),
		}

		// 获取地址
		address := new(models.ShipAddress)
		o.DB.Where("id = ?", order.ShipId).First(&address)
		// 获取 order goods
		goods := make([]*models.OrderGoods, 0)
		o.DB.Where("order_id = ?", order.ID).Find(&goods)

		v.ShipAddress = address.Serialize()

		ogs := make([]models.OrderGoodsSerializer, len(goods))
		for i, og := range goods {
			ogs[i] = og.Serialize()
		}
		v.OrderGoodsList = ogs

		result = append(result, v)
	}

	return result
}
