package database

import (
	"fmt"
	"ktmall/config"
	"log"

	"github.com/jinzhu/gorm"
)

var (
	dbConnection *gorm.DB
	models       []interface{}
)

func GetConnection() *gorm.DB {
	if dbConnection == nil {
		dbConnection = newConnection()
	}
	return dbConnection
}

func Close() {
	if dbConnection != nil {
		dbConnection.Close()
		dbConnection = nil
	}
}

func RegisterModels(ms ...interface{}) {
	for _, m := range ms {
		models = append(models, m)
	}
}

func RegisterModel(model interface{}) {
	models = append(models, model)
}

func Migrate() {
	db := GetConnection()
	for _, model := range models {
		db.AutoMigrate(model)
	}
}

func newConnection() *gorm.DB {
	connection := config.String("DB.CONNECTION")
	db, err := gorm.Open(connection, buildConnectionOptions(connection))
	if err != nil {
		panic(err)
	}

	db.LogMode(config.IsDev())
	db.DB().SetMaxOpenConns(config.Int("DB.MAX_OPEN_CONNECTIONS"))
	db.DB().SetMaxIdleConns(config.Int("DB.MAX_IDLE_CONNECTIONS"))

	return db
}

func buildConnectionOptions(connection string) string {
	dbname := getDBName()

	switch connection {
	case "mysql":
		return fmt.Sprintf(
			"%s:%s@(%s:%s)/%s?%s",
			config.String("DB.USERNAME"),
			config.String("DB.PASSWORD"),
			config.String("DB.HOST"),
			config.String("DB.PORT"),
			dbname,
			config.String("DB.OPTIONS"),
		)
	case "postgres":
		return fmt.Sprintf(
			"host=%s port=%s user=%s dbname=%s password=%s options='%s'",
			config.String("DB.HOST"),
			config.String("DB.PORT"),
			config.String("DB.USERNAME"),
			dbname,
			config.String("DB.PASSWORD"),
			config.String("DB.OPTIONS"),
		)
	case "sqlite3":
		return dbname
	case "mssql":
		return fmt.Sprintf(
			"sqlserver://%s:%s@%s:%s?database=%s&%s",
			config.String("DB.USERNAME"),
			config.String("DB.PASSWORD"),
			config.String("DB.HOST"),
			config.String("DB.PORT"),
			dbname,
			config.String("DB.OPTIONS"),
		)
	}

	log.Panicf("DB Connection %s not supported", connection)
	return ""
}

func getDBName() string {
	return config.String("DB.DATABASE") + "_" + string(config.AppRunMode())
}
