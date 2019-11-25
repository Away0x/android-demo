package bootstrap

import (
	"ktmall/common/tpl"
	"ktmall/common/tpl/tags"
	"ktmall/config"

	"github.com/flosch/pongo2"
	"github.com/labstack/echo/v4"
)

func SetupServerRender(e *echo.Echo) {
	render := tpl.NewRenderer()

	// template dir
	render.AddDirectory(config.String("APP.TEMPLATE_DIR"))

	// template global var
	globalVar := pongo2.Context{
		"APP_NAME":    config.String("APP.NAME"),
		"APP_RUNMODE": string(config.AppRunMode()),
		"APP_URL":     config.String("APP.URL"),
		"route":       config.G_Application.RoutePath,
	}

	render.UseContextProcessor(func(echoCtx echo.Context, pongoCtx pongo2.Context) {
		pongoCtx.Update(globalVar)

		tpldata := pongo2.Context{}

		pongoCtx.Update(tpldata)
	})
	e.Renderer = render

	// tags
	pongo2.RegisterTag("route", tags.RouteTag)
}
