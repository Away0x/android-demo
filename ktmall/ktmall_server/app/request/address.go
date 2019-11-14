package request

// 添加收货地址
type AddShipAddressReq struct {
	UserName   string `json:"user_name" example:"xiaoming"` // 用户名
	UserMobile string `json:"user_mobile"`                  // 手机号
	Address    string `json:"address"`                      // 地址
	// default: swagger default value
	// enums: swagger enums
	// 是否为默认地址 0-非默认
	IsDefault uint `json:"is_default" default:"0" enums:"0,1"`
}

// 修改收货地址
type ModifyShipAddressReq struct {
	ID         uint   `json:"id"`
	UserName   string `json:"user_name"`
	UserMobile string `json:"user_mobile"`
	Address    string `json:"address"`                            // 地址
	IsDefault  uint   `json:"is_default" default:"0" enums:"0,1"` // 是否为默认地址 0-非默认
}
