package bootstrap

import (
	"ktmall/bootstrap/server"
)

func Run() {
	// init db
	db, _ := SetupDB()
	defer db.Close()

	// run echo
	server.RunServer()
}
