package handler

import (
	"ktmall/app/context"
	"ktmall/app/models"
	"ktmall/app/services"
	"ktmall/common"
	"ktmall/common/serializer"

	"github.com/jinzhu/gorm"
)

/// 订单支付相关接口

// 获取支付宝支付签名
func OrderGetPaySign(c *context.AppContext) (err error) {
	return nil
}

// 刷新订单状态，已支付
func OrderPay(c *context.AppContext, u *models.UserInfo, t string, order *models.OrderInfo) (err error) {
	order.OrderStatus = models.OrderStatusWaitConfirm

	if err = c.DB().Save(order).Error; err != nil {
		return c.ErrorResp(common.ResultCodeDatabaseError, "操作失败")
	}

	return c.SuccessResp(nil)
}

// 取消订单
func OrderCancel(c *context.AppContext, u *models.UserInfo, t string, order *models.OrderInfo) (err error) {
	order.OrderStatus = models.OrderStatusCanceled

	if err = c.DB().Save(order).Error; err != nil {
		return c.ErrorResp(common.ResultCodeDatabaseError, "删除失败")
	}

	return c.SuccessResp(nil)
}

// 确认订单
func OrderConfirm(c *context.AppContext, u *models.UserInfo, t string, order *models.OrderInfo) (err error) {
	order.OrderStatus = models.OrderStatusCompleted

	if err = c.DB().Save(order).Error; err != nil {
		return c.ErrorResp(common.ResultCodeDatabaseError, "确认失败")
	}

	return c.SuccessResp(nil)
}

// 根据 ID 获取订单
func OrderDetail(c *context.AppContext, u *models.UserInfo, t string, order *models.OrderInfo) (err error) {
	v := order.Serialize()

	// 获取地址
	addresses := make([]*models.ShipAddress, 0)
	c.DB().Where("id = ?", order.ShipId).Find(&addresses)
	// 获取 order goods
	goods := make([]*models.OrderGoods, 0)
	c.DB().Where("order_id = ?", order.ID).Find(&goods)

	v["shipAddress"] = serializer.Serialize(addresses)
	v["orderGoodsList"] = serializer.Serialize(goods)

	return c.SuccessResp(v)
}

// 根据订单状态查询查询订单列表
func OrderList(c *context.AppContext, u *models.UserInfo, t string) (err error) {
	req := &struct {
		OrderStatus models.OrderStatus `json:"orderStatus"`
	}{}
	if err = c.BindReq(req); err != nil {
		return err
	}

	service := services.OrderService{DB: c.DB()}
	result := service.OrderList(req.OrderStatus, u.ID)

	return c.SuccessResp(result)
}

// 提交订单
func OrderSubmit(c *context.AppContext, u *models.UserInfo, t string, order *models.OrderInfo) (err error) {
	err = c.DBTX(func(db *gorm.DB) (e error) {
		order.OrderStatus = models.OrderStatusWaitPay
		if e = db.Save(order).Error; e != nil {
			return e
		}

		orderGoods := make([]*models.OrderGoods, 0)
		if e = db.Where("order_id = ?", order.ID).Find(&orderGoods).Error; e != nil {
			return e
		}

		goodsIds := make([]uint, len(orderGoods))
		for i, o := range orderGoods {
			goodsIds[i] = o.GoodsId
		}

		// 清空购物车数据
		if e = db.Where("goods_id IN (?)", goodsIds).Delete(&models.CartGoods{}).Error; e != nil {
			return e
		}

		return nil
	})

	if err != nil {
		return c.ErrorResp(common.ResultCodeDatabaseError, "提交订单失败")
	}

	return c.SuccessResp(nil)
}
