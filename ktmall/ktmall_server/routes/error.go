package routes

import (
	"ktmall/app/context"
	"ktmall/common/errno"
	"log"
	"net/http"

	"github.com/labstack/echo/v4"
)

// 注册通用错误处理
func registerError(e *echo.Echo) {
	e.HTTPErrorHandler = func(err error, c echo.Context) {
		errnoData := transformErrorType(err)

		// Send response
		if !c.Response().Committed {
			if c.Request().Method == http.MethodHead {
				err = c.NoContent(http.StatusOK)
			} else {
				// 响应错误的处理
				cc := context.NewAppContext(c)
				err = cc.ErrorResp(errnoData)
			}
			if err != nil {
				log.Printf("routes/error#HTTPErrorHandler: %s", err)
			}
		}
	}
}

func transformErrorType(err error) *errno.Errno {
	switch typed := err.(type) {
	// 请求参数错误
	case *errno.Errno:
		return typed
		// 其他 error
	default:
		return errno.UnknownErr.WithErr(typed).(*errno.Errno)
	}
}
