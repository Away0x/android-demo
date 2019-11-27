package cmd

import (
	"fmt"
	"ktmall/bootstrap"
	"os"

	"github.com/spf13/cobra"
)

const (
	// 默认配置文件路径
	defaultConfigFilePath = "config.yaml"
	// 配置文件格式
	configFileType = "yaml"
)

var configFilePath string

var rootCmd = &cobra.Command{
	Use: "ktmall",
}

func Execute() {
	if err := rootCmd.Execute(); err != nil {
		fmt.Println(err)
		os.Exit(1)
	}
}

func init() {
	cobra.OnInitialize(initConfig)

	// 配置文件路径: --cofig config_path
	rootCmd.PersistentFlags().
		StringVarP(&configFilePath, "config", "c", defaultConfigFilePath, "config file")
}

func initConfig() {
	// setup config
	if configFilePath == "" {
		configFilePath = defaultConfigFilePath
	}

	bootstrap.SetupConfig(configFilePath, configFileType)
}
