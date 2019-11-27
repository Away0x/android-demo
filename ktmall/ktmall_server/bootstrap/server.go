package bootstrap

import (
	"encoding/json"
	"fmt"
	"io/ioutil"
	"ktmall/config"
	"ktmall/routes"
	"strings"

	"github.com/labstack/echo/v4"
)

func RunServer() {
	e := SetupServer()

	e.Logger.Fatal(e.Start(config.String("APP.PORT")))
}

func SetupServer() *echo.Echo {
	e := echo.New()
	e.Debug = config.IsDev()
	e.HideBanner = true

	routes.Register(e)
	PrintRoutes(e, config.String("APP.TEMP_DIR")+"/routes.json")

	SetupServerRender(e)

	fmt.Printf("\n\napp runmode is %s\n\n", config.AppRunMode())
	config.SetupApp(&config.Application{Engine: e})

	return e
}

// 输出路由配置
func PrintRoutes(e *echo.Echo, filename string) {
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
