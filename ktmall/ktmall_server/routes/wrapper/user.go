package wrapper

import (
	"ktmall/app/auth/token"
	"ktmall/app/context"
	"ktmall/app/models"
	"ktmall/app/response"
)

func GetToken(handler func(*context.AppContext, string) error) context.AppHandlerFunc {
	return func(c *context.AppContext) error {
		tokenStr, err := token.GetToken(c)
		if err != nil {
			return c.ErrorResp(response.ResultCodeTokenError, err.Error())
		}

		return handler(c, tokenStr)
	}
}

// GetTokenAndUser : handler 中注入 token string 和 user
func GetTokenAndUser(handler func(*context.AppContext, *models.UserInfo, string) error) context.AppHandlerFunc {
	return func(c *context.AppContext) error {
		tokenStr, curUser, err := token.GetTokenAndUser(c)
		if err != nil {
			return c.ErrorResp(response.ResultCodeTokenError, err.Error())
		}

		return handler(c, curUser, tokenStr)
	}
}
