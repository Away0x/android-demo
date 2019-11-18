package api

import (
	"ktmall/app/context"
	"ktmall/app/models"
	"ktmall/app/request"
	"ktmall/app/response"
	"strconv"
)

/// 购物车相关接口

// CartList godoc
// @Summary 获取购物车列表
// @Tags cart
// @Produce  json
// @Security ApiKeyAuth
// @Success 200 {object} response.CartListResp
// @Router /cart/list [get]
func CartList(c *context.AppContext, u *models.UserInfo, t string) (err error) {
	list := make([]*models.CartGoods, 0)
	c.DB().Where("user_id = ?", u.ID).Find(&list)
	return c.SuccessResp(response.BuildCartListResp(list))
}

// CartAdd 添加商品到购物车
// @Summary 添加收货地址
// @Tags cart
// @Accept  json
// @Produce  json
// @Security ApiKeyAuth
// @Param json body request.AddCartReq true "商品信息"
// @Success 200 {int} int
// @Router /cart/add [post]
func CartAdd(c *context.AppContext, u *models.UserInfo, t string) (err error) {
	req := new(request.AddCartReq)
	if err = c.BindReq(req); err != nil {
		return c.ErrReq(err)
	}

	cart := &models.CartGoods{
		GoodsId:    req.GoodsId,
		GoodsDesc:  req.GoodsDesc,
		GoodsIcon:  req.GoodsIcon,
		GoodsPrice: strconv.Itoa(req.GoodsPrice),
		GoodsCount: req.GoodsCount,
		GoodsSku:   req.GoodsSku,
		UserId:     u.ID,
	}
	if err = c.DB().Create(cart).Error; err != nil {
		return c.ErrMsgResource(err, "添加失败")
	}
	count := 0
	if err = c.DB().Model(&models.CartGoods{}).Where("user_id = ?", u.ID).Count(&count).Error; err != nil {
		return c.ErrMsgResource(err, "count error")
	}

	return c.SuccessResp(count)
}

// CartDelete 删除购物车商品
// @Summary 添加收货地址
// @Tags cart
// @Accept  json
// @Produce  json
// @Security ApiKeyAuth
// @Param json body request.DeleteCartReq true "购物车商品的 id list"
// @Success 200 {object} common.CommonResponse
// @Router /cart/delete [post]
func CartDelete(c *context.AppContext, u *models.UserInfo, t string) (err error) {
	req := new(request.DeleteCartReq)
	if err = c.BindReq(req); err != nil {
		return c.ErrReq(err)
	}

	if err = c.DB().Where("id in (?)", req.CartIdList).Delete(&models.CartGoods{}).Error; err != nil {
		return c.ErrMsgResource(err, "删除失败")
	}

	return c.SuccessResp(nil)
}

// CartSubmit 提交购物车商品
// @Summary 添加收货地址
// @Tags cart
// @Accept  json
// @Produce  json
// @Security ApiKeyAuth
// @Param json body request.SubmitCartReq true "购物车商品信息"
// @Success 200 {int} int "订单 id"
// @Router /cart/submit [post]
func CartSubmit(c *context.AppContext, u *models.UserInfo, t string) (err error) {
	req := new(request.SubmitCartReq)
	if err = c.BindReq(req); err != nil {
		return c.ErrReq(err)
	}

	order := new(models.OrderInfo)
	order.UserId = u.ID
	order.TotalPrice = req.TotalPrice

	// 设置默认地址
	addresses := make([]*models.ShipAddress, 0)
	if err = c.DB().Where("user_id = ?", u.ID).Find(&addresses).Error; err != nil || len(addresses) == 0 {
		order.ShipId = 0
	} else {
		for _, a := range addresses {
			if models.TinyBool(a.ShipIsDefault) {
				order.ShipId = a.ID
			}
		}
	}

	order.OrderStatus = models.OrderStatusWaitPay
	order.PayType = models.OrderPayTypeWait

	// 创建 order
	if err = c.DB().Create(order).Error; err != nil {
		return c.ErrMsgDatabase(err, "创建 Order 失败")
	}

	// 创建 OrderGoods
	for _, item := range req.GoodsList {
		og := new(models.OrderGoods)
		og.GoodsId = item.GoodsId
		og.GoodsDesc = item.GoodsDesc
		og.GoodsIcon = item.GoodsIcon
		og.GoodsCount = item.GoodsCount
		og.GoodsSku = item.GoodsSku
		og.OrderId = order.ID
		og.GoodsPrice = strconv.Itoa(item.GoodsPrice)
		if err = c.DB().Create(&og).Error; err != nil {
			return c.ErrMsgDatabase(err, "创建 OrderGoods 失败")
		}
	}

	return c.SuccessResp(order.ID)
}
