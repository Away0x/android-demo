package factory

import (
	"fmt"
	"ktmall/bootstrap"
	"ktmall/database"
)

func dropAndCreateTable(table interface{}) {
	database.GetConnection().DropTable(table)
	database.GetConnection().CreateTable(table)
}

func Run() {
	bootstrap.SetupDB()
	defer bootstrap.CloseDB()

	fmt.Println("database.factory runing")

	categoryFactory()
	factoryGoodsInfo()
	factoryGoodsSku()
}
