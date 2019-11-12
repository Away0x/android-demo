package controllers

import (
	"ktmall/app/context"
	"ktmall/app/models"
)

/// 消息相关接口

// 获取消息列表
func MessageList(c *context.AppContext, u *models.UserInfo, t string) error {
	list := make([]*models.MessageInfo, 0)
	c.DB().Where("user_id = ?", u.ID).Find(&list)

	return c.SuccessResp(list)
}
