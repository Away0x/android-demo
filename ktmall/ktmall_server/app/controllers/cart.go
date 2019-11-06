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

	DeleteCartReq struct {
		CartIdList []uint `json:"cartIdList"`
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
	req := new(DeleteCartReq)
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
	return c.SuccessResp(nil)
}
