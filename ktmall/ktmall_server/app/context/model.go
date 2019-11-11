package context

import (
	"github.com/jinzhu/gorm"
)

func (c *AppContext) DBTX(cb func(*gorm.DB) error) (err error) {
	tx := c.DB().Begin()
	defer func() {
		if r := recover(); r != nil {
			tx.Rollback()
		}
	}()

	err = cb(tx)

	if err != nil {
		tx.Rollback()
	} else {
		err = tx.Commit().Error
	}

	return err
}
