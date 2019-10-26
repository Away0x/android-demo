package models

import (
	"ktmall/database"
	"time"

	"github.com/jinzhu/gorm"
)

const (
	// TrueTinyint true
	TrueTinyint = 1
	// FalseTinyint false
	FalseTinyint = 0
)

type BaseModel struct {
	ID uint `sql:"primary_key; auto_increment; not null"`
	// MySQL 的 DATE/DATATIME 类型可以对应 Golang的time.Time
	CreatedAt time.Time
	UpdatedAt time.Time
	// 有 DeletedAt (类型需要是 *time.Time) 即支持 gorm 软删除
	DeletedAt *time.Time `sql:"index"`
}

func DB() *gorm.DB {
	return database.DBManager()
}
