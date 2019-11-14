package cmd

import (
	"ktmall/bootstrap"

	"github.com/spf13/cobra"
)

var serverCmd = &cobra.Command{
	Use:   "server",
	Short: "run app server",
	Run: func(cmd *cobra.Command, args []string) {
		// init db
		db, _ := bootstrap.SetupDB()
		defer db.Close()

		// run echo
		bootstrap.RunServer()
	},
}

func init() {
	rootCmd.AddCommand(serverCmd)
}
