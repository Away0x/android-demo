package handler

import (
	"ktmall/app/context"
	"ktmall/app/models"
	"ktmall/common"
	"strconv"
)

/// 购物车相关接口

type (
	AddCartReq struct {
		GoodsId    uint   `json:"goodsId"`
		GoodsDesc  string `json:"goodsDesc"`
		GoodsIcon  string `json:"goodsIcon"`
		GoodsPrice int    `json:"goodsPrice"`
		GoodsCount uint   `json:"goodsCount"`
		GoodsSku   string `json:"goodsSku"`
	}

	SubmitCartReq struct {
		TotalPrice int `json:"totalPrice"`
		GoodsList  []struct {
			GoodsId    uint
			GoodsDesc  string
			GoodsIcon  string
			GoodsCount uint
			GoodsSku   string
			GoodsPrice int
		} `json:"goodsList"`
	}
)

// 获取购物车列表
func CartList(c *context.AppContext, u *models.UserInfo, t string) (err error) {
	list := new([]*models.CartGoods)
	c.DB().Where("user_id = ?", u.ID).Find(list)
	return c.SuccessResp(list)
}

// 添加商品到购物车
func CartAdd(c *context.AppContext, u *models.UserInfo, t string) (err error) {
	req := new(AddCartReq)
	if err = c.BindReq(req); err != nil {
		return err
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
		return c.ErrorResp(common.ResultCodeDatabaseError, "添加失败")
	}
	count := 0
	if err = c.DB().Model(&models.CartGoods{}).Where("user_id = ?", u.ID).Count(&count).Error; err != nil {
		return c.ErrorResp(common.ResultCodeDatabaseError, "count error")
	}

	return c.SuccessResp(count)
}

// 删除购物车商品
func CartDelete(c *context.AppContext, u *models.UserInfo, t string) (err error) {
	req := &struct {
		CartIdList []uint `json:"cartIdList"`
	}{}
	if err = c.BindReq(req); err != nil {
		return err
	}

	if err = c.DB().Where("id in (?)", req.CartIdList).Delete(&models.CartGoods{}).Error; err != nil {
		return c.ErrorResp(common.ResultCodeDatabaseError, "删除失败")
	}

	return c.SuccessResp(nil)
}

// 提交购物车商品
func CartSubmit(c *context.AppContext, u *models.UserInfo, t string) (err error) {
	req := new(SubmitCartReq)
	if err = c.BindReq(req); err != nil {
		return err
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
		return c.ErrorResp(common.ResultCodeDatabaseError, "创建 Order 失败")
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
			return c.ErrorResp(common.ResultCodeDatabaseError, "创建 OrderGoods 失败")
		}
	}

	return c.SuccessResp(order.ID)
}
