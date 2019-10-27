package handler

import (
	"ktmall/app/context"
	"ktmall/app/models"
	"ktmall/common"
)

/// 商品相关接口

// 获取商品列表
func GoodsList(c *context.AppContext) error {
	// req := &struct {
	// }{}

	return nil
}

// 根据关键字获取商品列表
func GoodsListByKeyword(c *context.AppContext) error {
	return nil
}

// 获取商品详情
func GoodsDetail(c *context.AppContext) (err error) {
	id, err := c.IntParam("id")
	if err != nil {
		return c.ErrorResp(common.ResultCodeReqError, "参数错误")
	}

	good := new(models.GoodsInfo)
	if err = c.DB().Where("id = ?", id).First(good).Error; err != nil {
		return c.ErrorResp(common.ResultCodeResourceError, "商品不存在")
	}
	gs := good.Serialize()

	// 获取 goods sku
	skus := make([]*models.GoodsSku, 0)
	c.DB().Where("goods_id = ?", good.ID).Find(&skus)
	gs.Include("goodsSku", skus)

	return c.SuccessResp(gs)
}
