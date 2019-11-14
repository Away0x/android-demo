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
	ShipIsDefault  uint `gorm:"type:tinyint(1)"`

	UserId uint
}

type AddressSerializer struct {
	ID             uint   `json:"id" example:"1"` // 这里可写 swagger desc
	ShipUserName   string `json:"shipUserName" example:"swagger test"`
	ShipUserMobile string `json:"shipUserMobile"`
	ShipAddress    string `json:"shipAddress"`
	ShipIsDefault  uint   `json:"shipIsDefault"`
}

func (ShipAddress) TableName() string {
	return ShipAddressTableName
}

func (m *ShipAddress) Serialize() AddressSerializer {
	return AddressSerializer{
		ID:             m.ID,
		ShipUserName:   m.ShipUserName,
		ShipUserMobile: m.ShipUserMobile,
		ShipAddress:    m.ShipAddress,
		ShipIsDefault:  m.ShipIsDefault,
	}
}
