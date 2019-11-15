package api

import (
	"ktmall/app/context"
	"ktmall/app/models"
	"ktmall/app/request"
	"ktmall/app/response"
	"ktmall/app/services"
)

/// 商品相关接口

// GoodsList 获取商品列表
// @Summary 获取商品列表
// @Tags goods
// @Accept  json
// @Produce  json
// @Param categoryId query int true "分类 id"
// @Param pageNo query int false "页码"
// @Param keyword query string false "关键字查询"
// @Success 200 {object} response.GoodListResp
// @Router /goods/list [post]
func GoodsList(c *context.AppContext) (err error) {
	req := new(request.GoodsListReq)
	if err = c.BindReq(req); err != nil {
		return err
	}

	if req.PageNo == 0 {
		req.PageNo = 1
	}

	service := services.GoodsService{DB: c.DB()}
	list, err := service.GoodsList(req.CategoryId, req.PageNo, req.Keyword)
	if err != nil {
		return err
	}

	return c.SuccessResp(list)
}

// GoodsDetail 获取商品详情
// @Summary 获取商品详情
// @Tags goods
// @Produce  json
// @Param id path int true "商品 id"
// @Success 200 {object} response.GoodsDetailResp
// @Router /goods/detail/{id} [get]
func GoodsDetail(c *context.AppContext) (err error) {
	id, err := c.IntParam("id")
	if err != nil {
		return c.ErrorResp(response.ResultCodeReqError, "参数错误")
	}

	good := new(models.GoodsInfo)
	if err = c.DB().Where("id = ?", id).First(good).Error; err != nil {
		return c.ErrorResp(response.ResultCodeResourceError, "商品不存在")
	}
	gs := response.GoodsDetailResp{
		GoodSerializer: good.Serialize(),
	}

	// 获取 goods sku
	skus := make([]*models.GoodsSku, 0)
	c.DB().Where("goods_id = ?", good.ID).Find(&skus)
	skss := make([]models.GoodsSkuSerializer, len(skus))
	for i, s := range skus {
		skss[i] = s.Serialize()
	}
	gs.GoodsSku = skss

	return c.SuccessResp(gs)
}
