package controllers

import (
	"ktmall/app/context"
	"ktmall/app/models"
	"ktmall/app/request"
	"ktmall/app/response"
	"ktmall/app/services"

	"github.com/jinzhu/gorm"
)

/// 订单支付相关接口

// OrderGetPaySign 获取支付宝支付签名
// @Summary 获取支付宝支付签名
// @Tags order
// @Accept  json
// @Produce  json
// @Security ApiKeyAuth
// @Router /order/get_pay_sign [post]
func OrderGetPaySign(c *context.AppContext) (err error) {
	return nil
}

// OrderPay 刷新订单状态，已支付
// @Summary 刷新订单状态，已支付
// @Tags order
// @Accept  json
// @Produce  json
// @Security ApiKeyAuth
// @Param id path int true "订单 id"
// @Success 200 {string} string
// @Router /order/pay/{id} [post]
func OrderPay(c *context.AppContext, u *models.UserInfo, t string, order *models.OrderInfo) (err error) {
	order.OrderStatus = models.OrderStatusWaitConfirm

	if err = c.DB().Save(order).Error; err != nil {
		return c.ErrorResp(response.ResultCodeDatabaseError, "操作失败")
	}

	return c.SuccessResp(nil)
}

// OrderCancel 取消订单
// @Summary 取消订单
// @Tags order
// @Accept  json
// @Produce  json
// @Security ApiKeyAuth
// @Param id path int true "订单 id"
// @Success 200 {string} string
// @Router /order/cancel/{id} [post]
func OrderCancel(c *context.AppContext, u *models.UserInfo, t string, order *models.OrderInfo) (err error) {
	order.OrderStatus = models.OrderStatusCanceled

	if err = c.DB().Save(order).Error; err != nil {
		return c.ErrorResp(response.ResultCodeDatabaseError, "删除失败")
	}

	return c.SuccessResp(nil)
}

// OrderConfirm 确认订单
// @Summary 确认订单
// @Tags order
// @Accept  json
// @Produce  json
// @Security ApiKeyAuth
// @Param id path int true "订单 id"
// @Success 200 {string} string
// @Router /order/confirm/{id} [post]
func OrderConfirm(c *context.AppContext, u *models.UserInfo, t string, order *models.OrderInfo) (err error) {
	order.OrderStatus = models.OrderStatusCompleted

	if err = c.DB().Save(order).Error; err != nil {
		return c.ErrorResp(response.ResultCodeDatabaseError, "确认失败")
	}

	return c.SuccessResp(nil)
}

// OrderDetail 根据 ID 获取订单
// @Summary 根据 ID 获取订单
// @Tags order
// @Accept  json
// @Produce  json
// @Security ApiKeyAuth
// @Param id path int true "订单 id"
// @Success 200 {string} string
// @Router /order/detail/{id} [get]
func OrderDetail(c *context.AppContext, u *models.UserInfo, t string, order *models.OrderInfo) (err error) {
	v := response.OrderDetailResp{
		OrderInfoSerialize: order.Serialize(),
	}

	// 获取地址
	address := new(models.ShipAddress)
	if err = c.DB().Where("id = ?", order.ShipId).First(&address).Error; err == nil {
		v.ShipAddress = address.Serialize()
	}
	// 获取 order goods
	goods := make([]*models.OrderGoods, 0)
	c.DB().Where("order_id = ?", order.ID).Find(&goods)
	gs := make([]models.OrderGoodsSerializer, len(goods))
	for i, g := range goods {
		gs[i] = g.Serialize()
	}
	v.OrderGoodsList = gs

	return c.SuccessResp(v)
}

// OrderList 根据订单状态查询查询订单列表
// @Summary 根据订单状态查询查询订单列表
// @Tags order
// @Accept  json
// @Produce  json
// @Security ApiKeyAuth
// @Param orderStatus query int false "订单状态 0-全部 1-待支付 2-待收货 3-已完成 4-已取消" default(0) enums(0,1,2,3,4)
// @Success 200 {object} response.OrderListResp
// @Router /order/list [get]
func OrderList(c *context.AppContext, u *models.UserInfo, t string) (err error) {
	req := new(request.OrderListReq)
	if err = c.BindReq(req); err != nil {
		return err
	}

	service := services.OrderService{DB: c.DB()}
	result := service.OrderList(req.OrderStatus, u.ID)

	return c.SuccessResp(result)
}

// OrderSubmit 提交订单
// @Summary 提交订单
// @Tags order
// @Accept  json
// @Produce  json
// @Security ApiKeyAuth
// @Param id path int true "订单 id"
// @Success 200 {string} string
// @Router /order/submit/{id} [post]
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
		return c.ErrorResp(response.ResultCodeDatabaseError, "提交订单失败")
	}

	return c.SuccessResp(nil)
}
