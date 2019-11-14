package server

import (
	"encoding/json"
	"io/ioutil"
	"ktmall/bootstrap/app"
	"ktmall/config"
	"ktmall/routes"
	"strings"

	"github.com/labstack/echo/v4"
)

func RunServer() {
	e := setupServer()
	app.Setup(&app.Application{Engine: e})

	e.Logger.Fatal(e.Start(config.String("APP.PORT")))
}

func setupServer() *echo.Echo {
	e := echo.New()
	e.Debug = config.IsDev()
	e.HideBanner = true

	routes.Register(e)
	printRoutes(e, config.String("APP.TEMP_DIR")+"/routes.json")

	return e
}

// 输出路由配置
func printRoutes(e *echo.Echo, filename string) {
	routes := make([]*echo.Route, 0)
	for _, item := range e.Routes() {
		if strings.HasPrefix(item.Name, "github.com") {
			continue
		}

		routes = append(routes, item)
	}

	routesStr, _ := json.MarshalIndent(struct {
		Count  int           `json:"count"`
		Routes []*echo.Route `json:"routes"`
	}{
		Count:  len(routes),
		Routes: routes,
	}, "", "  ")
	ioutil.WriteFile(filename, routesStr, 0644)
}
