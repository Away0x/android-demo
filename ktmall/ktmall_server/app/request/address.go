package request

// 添加收货地址
type AddShipAddressReq struct {
	UserName   string `json:"userName" example:"xiaoming"` // 用户名
	UserMobile string `json:"userMobile"`                  // 手机号
	Address    string `json:"address"`                     // 地址
}

// 修改收货地址
type ModifyShipAddressReq struct {
	ID         uint   `json:"id"`
	UserName   string `json:"userName"`
	UserMobile string `json:"userMobile"`
	Address    string `json:"address"` // 地址
	// default: swagger default value
	// enums: swagger enums
	// 是否为默认地址 0-非默认
	IsDefault uint `json:"isDefault" default:"0" enums:"0,1"`
}
