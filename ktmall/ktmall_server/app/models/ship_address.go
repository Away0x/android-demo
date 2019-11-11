package models

import (
	"ktmall/common/serializer"
)

const (
	ShipAddressTableName = "ship_address"
)

// 地址管理
type ShipAddress struct {
	BaseModel
	ShipUserName   string
	ShipUserMobile string
	ShipAddress    string
	ShipIsDefault  uint `gorm:"type:tinyint(1)"`

	UserId uint
}

func (ShipAddress) TableName() string {
	return ShipAddressTableName
}

func (s *ShipAddress) Serialize() serializer.Data {
	return serializer.Data{
		"id":             s.ID,
		"shipUserName":   s.ShipUserName,
		"shipUserMobile": s.ShipUserMobile,
		"shipAddress":    s.ShipAddress,
		"shipIsDefault":  s.ShipIsDefault,
	}
}
