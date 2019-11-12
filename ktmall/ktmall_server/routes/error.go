package routes

import (
	"ktmall/app/context"
	"ktmall/app/response"
	"log"
	"net/http"

	"github.com/Away0x/validate"
	"github.com/labstack/echo/v4"
)

func registerError(e *echo.Echo) {
	e.HTTPErrorHandler = func(err error, c echo.Context) {
		data := transformErrorType(err)

		// Send response
		if !c.Response().Committed {
			if c.Request().Method == http.MethodHead {
				err = c.NoContent(http.StatusOK)
			} else {
				// 响应错误的处理
				cc := context.NewAppContext(c)
				err = cc.ErrorResp(data.Status, data.Message)
			}
			if err != nil {
				log.Printf("errno.HTTPErrorHandler: %s", err)
			}
		}
	}
}

func transformErrorType(err error) *response.CommonResponse {
	switch typed := err.(type) {
	// 请求参数错误
	case validate.Messages:
		msg := "参数错误"
		for _, v := range typed {
			msg = v[0] // 显示第一个错误信息
		}

		return response.NewCommonResponse(
			response.ResultCodeReqError,
			msg,
			nil,
		)
	// 其他 error
	default:
		return response.NewCommonResponse(
			response.ResultCodeError,
			typed.Error(),
			nil,
		)
	}
}
