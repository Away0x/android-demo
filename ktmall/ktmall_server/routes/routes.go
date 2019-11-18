package routes

import (
	"ktmall/app/context"
	"ktmall/config"
	"net/http"
	"strings"

	"github.com/labstack/echo/v4"
	"github.com/labstack/echo/v4/middleware"
)

const (
	staticPath = "/public"
)

func Register(e *echo.Echo) {
	e.Use(context.Middleware)
	e.Use(middleware.Recover())

	if config.IsDev() {
		e.Use(middleware.LoggerWithConfig(middleware.LoggerConfig{
			Format: `${status}   ${method}   ${latency_human}               ${uri}`,
		}))
	}

	e.Pre(middleware.MethodOverrideWithConfig(middleware.MethodOverrideConfig{
		Getter: middleware.MethodFromForm("_method"),
	}))
	e.Pre(middleware.RemoveTrailingSlashWithConfig(middleware.TrailingSlashConfig{
		RedirectCode: http.StatusMovedPermanently,
	}))

	if config.Bool("APP.GZIP") {
		e.Use(middleware.GzipWithConfig(middleware.GzipConfig{
			Skipper: func(c echo.Context) bool {
				return !strings.HasPrefix(c.Request().URL.Path, staticPath) // 只 gzip 静态文件
			},
		}))
	}

	// 静态文件路由
	e.Static(staticPath, config.String("APP.PUBLIC_DIR"))
	e.File("/favicon.ico", config.String("APP.PUBLIC_DIR")+"/favicon.ico")

	// cors
	e.Use(middleware.CORS())

	registerError(e)
	registerWeb(e)
	registerAPI(e)
}
