package wrapper

import (
	"ktmall/app/auth/token"
	"ktmall/app/context"
	"ktmall/app/models"
	"ktmall/common"
)

type (
	getOrderHandlerType func(*context.AppContext, *models.UserInfo, string, *models.OrderInfo) error
)

func GetOrder(handler getOrderHandlerType) context.AppHandlerFunc {
	return func(c *context.AppContext) error {
		tokenStr, curUser, err := token.GetTokenAndUser(c)
		if err != nil {
			return err
		}

		id, err := c.UintParam("id")
		if err != nil {
			return c.ErrorResp(common.ResultCodeReqError, "参数错误")
		}

		order := new(models.OrderInfo)
		if err = c.DB().Where("id = ?", id).First(order).Error; err != nil {
			return c.ErrorResp(common.ResultCodeResourceError, "获取数据失败")
		}

		return handler(c, curUser, tokenStr, order)
	}
}
