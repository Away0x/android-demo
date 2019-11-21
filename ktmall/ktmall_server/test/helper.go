package test

import (
	"ktmall/bootstrap"
	"os"
)

func ControllerTestInit() {
	os.Chdir("./../../..")
	bootstrap.SetupConfig("config.yaml", "yaml")
}
