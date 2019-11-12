package middleware

import (
	"ktmall/app/auth/token"
	"ktmall/app/context"
	"ktmall/app/response"

	"github.com/labstack/echo/v4"
)

// token 存在并且用户已登录才可进入
func TokenAuth(next echo.HandlerFunc) echo.HandlerFunc {
	return func(c echo.Context) error {
		_, _, err := token.GetTokenAndUser(c)
		if err != nil {
			cc := context.NewAppContext(c)
			return cc.ErrorResp(response.ResultCodeTokenError, err.Error())
		}

		return next(c)
	}
}
