package factory

import (
	"fmt"
	"ktmall/bootstrap"
	"ktmall/database"
)

func dropAndCreateTable(table interface{}) {
	database.DBManager().DropTable(table)
	database.DBManager().CreateTable(table)
}

func Run() {
	db, _ := bootstrap.SetupDB()
	defer db.Close()

	fmt.Println("database.factory runing")
}
