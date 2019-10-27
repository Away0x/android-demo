package bootstrap

import (
	"ktmall/app/models"
	"ktmall/database"

	"github.com/jinzhu/gorm"
)

func SetupDB() (*gorm.DB, error) {
	database.SetupDB()
	db := database.DBManager()

	db.AutoMigrate(
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

	return db, nil
}
