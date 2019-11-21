package api

import (
	"ktmall/app/context"
	"ktmall/app/models"
	"ktmall/app/response"
)

/// 消息相关接口

// MessageList 获取消息列表
// @Summary 获取消息列表
// @Tags message
// @Produce  json
// @Security ApiKeyAuth
// @Success 200 {object} response.MessageListResp
// @Router /message/list [get]
func MessageList(c *context.AppContext, u *models.UserInfo, t string) error {
	list := make([]*models.MessageInfo, 0)
	c.DB().Where("user_id = ?", u.ID).Order("id desc").Find(&list)

	return c.SuccessResp(response.BuildMessageListResp(list))
}
