package handler

import (
	"ktmall/app/context"
	"ktmall/app/models"
	"ktmall/common"
)

/// 收货地址相关接口

type (
	AddShipAddressReq struct {
		UserName   string `json:"user_name"`
		UserMobile string `json:"user_mobile"`
		Address    string `json:"address"`
		IsDefault  int    `json:"is_default"`
	}

	ModifyShipAddressReq struct {
		ID         uint   `json:"id"`
		UserName   string `json:"user_name"`
		UserMobile string `json:"user_mobile"`
		Address    string `json:"address"`
		IsDefault  int    `json:"is_default"`
	}
)

// 添加收货地址
func AddressAdd(c *context.AppContext, u *models.UserInfo, s string) (err error) {
	req := new(AddShipAddressReq)
	if err = c.BindReq(req); err != nil {
		return err
	}

	address := &models.ShipAddress{
		ShipUserName:   req.UserName,
		ShipUserMobile: req.UserMobile,
		ShipAddress:    req.Address,
		ShipIsDefault:  req.IsDefault,
		UserId:         u.ID,
	}
	if err = c.DB().Create(address).Error; err != nil {
		return c.ErrorResp(common.ResultCodeDatabaseError, "创建失败")
	}

	return c.SuccessResp(address)
}

// 删除收货地址
func AddressDelete(c *context.AppContext, u *models.UserInfo, s string) (err error) {
	id, err := c.UintParam("id")
	if err != nil {
		return c.ErrorResp(common.ResultCodeReqError, "参数错误")
	}

	address := new(models.ShipAddress)
	if err = c.DB().First(id, address).Error; err != nil {
		return c.ErrorResp(common.ResultCodeResourceError, "获取数据失败")
	}

	if err = c.DB().Delete(address).Error; err != nil {
		return c.ErrorResp(common.ResultCodeResourceError, "删除失败")
	}

	return c.SuccessResp(nil)
}

// 修改收货地址
func AddressModify(c *context.AppContext, u *models.UserInfo, s string) (err error) {
	req := new(ModifyShipAddressReq)
	if err = c.BindReq(req); err != nil {
		return err
	}

	address := new(models.ShipAddress)
	if err = c.DB().First(req.ID, address).Error; err != nil {
		return c.ErrorResp(common.ResultCodeResourceError, "获取数据失败")
	}

	address.ShipUserName = req.UserName
	address.ShipUserMobile = req.UserMobile
	address.ShipAddress = req.Address
	address.ShipIsDefault = req.IsDefault
	if err = c.DB().Save(address).Error; err != nil {
		return c.ErrorResp(common.ResultCodeResourceError, "修改失败")
	}

	return c.SuccessResp(address)
}

// 查询收货地址列表
func AddressList(c *context.AppContext, u *models.UserInfo, s string) (err error) {
	list := new(models.ShipAddress)
	if err = c.DB().Where("user_id = ?", u.ID).Find(list).Error; err != nil {
		return c.SuccessResp(list)
	}

	return c.SuccessResp(list)
}
