package cmd

import (
	"fmt"
	"ktmall/common"

	"github.com/spf13/cobra"
)

var versionCmd = &cobra.Command{
	Use:   "version",
	Short: "print version",
	Run: func(cmd *cobra.Command, args []string) {
		fmt.Printf("\napp version = %s\n", common.APP_VERSION)
	},
}

func init() {
	rootCmd.AddCommand(versionCmd)
}
