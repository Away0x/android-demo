package config

import (
	"fmt"
	"strings"

	"github.com/fsnotify/fsnotify"
	"github.com/lexkong/log"
	"github.com/spf13/viper"
)

type (
	Config struct{}
)

const (
	// RunmodeProduction 生产环境
	RunmodeProduction = "production"
	// RunmodeStaging 准生产环境
	RunmodeStaging = "staging"
	// RunmodeDevelopment 调试、开发环境
	RunmodeDevelopment = "development"
	// RunmodeTest 测试环境
	RunmodeTest = "test"
)

var (
	G_Config *Config
)

func SetupConfig(configFilePath, configFileType string) {
	// 初始化 viper 配置
	viper.SetConfigFile(configFilePath)
	viper.SetConfigType(configFileType)

	if err := viper.ReadInConfig(); err != nil {
		panic(fmt.Sprintf("读取配置文件失败，请检查 %s 配置文件是否存在: %v", configFilePath, err))
	}

	// 设置配置默认值
	setupDefaultConfig()

	// 环境变量 (设置环境变量: export APPNAME_APP_RUNMODE=development)
	viper.AutomaticEnv()
	viper.SetEnvPrefix(String("APP.NAME")) // 环境变量前缀
	viper.SetEnvKeyReplacer(strings.NewReplacer(".", "_"))

	// 初始化日志配置
	initLog()

	// 监听配置文件变化
	watchConfig()

	G_Config = &Config{}
}

func WriteConfig(filename string) {
	viper.WriteConfigAs(filename)
}

// 监控配置文件变化
func watchConfig() {
	viper.WatchConfig()
	viper.OnConfigChange(func(ev fsnotify.Event) {
		log.Infof("Config file changed: %s", ev.Name)
	})
}

func String(key string) string {
	return viper.GetString(key)
}

func DefaultString(key string, defaultVal string) string {
	v := viper.GetString(key)
	if v == "" {
		return defaultVal
	}

	return v
}

func Int(key string) int {
	return viper.GetInt(key)
}

func DefaultInt(key string, defaultVal int) int {
	v := viper.GetInt(key)
	if v == 0 {
		return defaultVal
	}

	return v
}

func Bool(key string) bool {
	return viper.GetBool(key)
}

// IsDev 是否为开发模式
func IsDev() bool {
	return String("APP.RUNMODE") == RunmodeDevelopment
}

func (c *Config) String(key string) string {
	return String(key)
}

func (c *Config) DefaultString(key string, defaultVal string) string {
	return DefaultString(key, defaultVal)
}

func (c *Config) Int(key string) int {
	return Int(key)
}

func (c *Config) DefaultInt(key string, defaultVal int) int {
	return DefaultInt(key, defaultVal)
}

func (c *Config) Bool(key string) bool {
	return Bool(key)
}
