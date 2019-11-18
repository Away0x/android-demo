package bootstrap

import (
	"ktmall/config"
)

func SetupConfig(configFilePath, configFileType string) {
	config.SetupConfig(configFilePath, configFileType)

	config.WriteConfig(config.String("APP.TEMP_DIR") + "/config.json")
}
