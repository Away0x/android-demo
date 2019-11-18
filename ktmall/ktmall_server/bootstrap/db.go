package bootstrap

import (
	"ktmall/app/models"
	"ktmall/config"
	"ktmall/database"

	_ "github.com/jinzhu/gorm/dialects/mysql"
	// import _ "github.com/jinzhu/gorm/dialects/postgres"
	// import _ "github.com/jinzhu/gorm/dialects/sqlite"
	// import _ "github.com/jinzhu/gorm/dialects/mssql"
)

func SetupDB() {
	database.GetConnection()

	database.RegisterModels(
		&models.UserInfo{},
		&models.CartGoods{},
		&models.Category{},
		&models.GoodsInfo{},
		&models.GoodsSku{},
		&models.MessageInfo{},
		&models.OrderGoods{},
		&models.OrderInfo{},
		&models.ShipAddress{},
	)

	if config.Bool("DB.AUTO_MIGRATE") {
		database.Migrate()
	}
}

func CloseDB() {
	database.Close()
}
