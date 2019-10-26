package database

import (
	"fmt"
	"ktmall/bootstrap/config"
	"log"

	_ "github.com/go-sql-driver/mysql"
	"github.com/jinzhu/gorm"
)

var dbClient *gorm.DB

func SetupDB() {
	url := createDBURL(
		config.String("DB.USERNAME"),
		config.String("DB.PASSWORD"),
		config.String("DB.HOST"),
		config.String("DB.PORT"),
		dbname(),
	)
	db, err := gorm.Open(config.String("DB.CONNECTION"), url)
	if err != nil {
		log.Fatal("Database connection failed. Database url: "+url+" error: ", err)
	}

	db.LogMode(config.IsDev())
	dbClient = db
}

func DBManager() *gorm.DB {
	if dbClient == nil {
		panic("请先初始化 SetupDB")
	}
	return dbClient
}

func createDBURL(uname string, pwd string, host string, port string, dbname string) string {
	return fmt.Sprintf("%s:%s@tcp(%s:%s)/%s?charset=utf8&parseTime=%t&loc=%s",
		uname, pwd,
		host, port,
		dbname, true, "Local")
}

func dbname() string {
	return config.String("DB.DATABASE") + "_" + config.String("APP.RUNMODE")
}
