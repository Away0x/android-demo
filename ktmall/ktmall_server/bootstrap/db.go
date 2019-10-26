package bootstrap

import (
	"ktmall/database"

	"github.com/jinzhu/gorm"
)

func SetupDB() (*gorm.DB, error) {
	database.SetupDB()
	db := database.DBManager()

	// db.AutoMigrate()

	return db, nil
}
