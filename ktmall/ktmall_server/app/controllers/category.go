package controllers

import (
	"ktmall/app/context"
	"ktmall/app/models"
)

/// 商品分类相关接口

// 获取商品分类列表
func CategoryList(c *context.AppContext) (err error) {
	req := &struct {
		ParentId int `query:"parentId"`
	}{}
	if err := c.BindReq(req); err != nil {
		return err
	}

	list := make([]*models.Category, 0)
	if err = c.DB().Where("parent_id = ?", req.ParentId).Find(&list).Error; err != nil {
		return err
	}

	return c.SuccessResp(list)
}
