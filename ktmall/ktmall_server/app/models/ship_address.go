package models

const (
	ShipAddressTableName = "ship_address"
)

// 地址管理
type ShipAddress struct {
	BaseModel
	ShipUserName   string
	ShipUserMobile string
	ShipAddress    string
	ShipIsDefault  int
	UserId         uint
}

func (ShipAddress) TableName() string {
	return ShipAddressTableName
}
