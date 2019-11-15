package api

import (
	"ktmall/app/context"
	"ktmall/app/models"
	"ktmall/app/request"
	"ktmall/app/response"
)

/// 商品分类相关接口

// CategoryList 获取商品分类列表
// @Summary 获取商品分类列表
// @Tags category
// @Produce  json
// @Param parentId query int true "大分类 id"
// @Success 200 {object} response.CategoryListResp
// @Router /category/list [get]
func CategoryList(c *context.AppContext) (err error) {
	req := new(request.CategoryListReq)
	if err := c.BindReq(req); err != nil {
		return c.ErrReq(err)
	}

	list := make([]*models.Category, 0)
	if err = c.DB().Where("parent_id = ?", req.ParentId).Find(&list).Error; err != nil {
		return c.SuccessResp(response.BuildCategoryListResp(list))
	}

	return c.SuccessResp(response.BuildCategoryListResp(list))
}
