package controllers

import (
	"ktmall/app/context"
	"ktmall/app/models"
	"ktmall/app/response"
	"ktmall/common/utils"
)

/// 收货地址相关接口

type (
	AddShipAddressReq struct {
		UserName   string `json:"user_name" example:"xiaoming"` // 用户名
		UserMobile string `json:"user_mobile"`                  // 手机号
		Address    string `json:"address"`                      // 地址
		IsDefault  uint   `json:"is_default"`                   // 是否为默认地址
	}

	ModifyShipAddressReq struct {
		ID         uint   `json:"id"`
		UserName   string `json:"user_name"`
		UserMobile string `json:"user_mobile"`
		Address    string `json:"address"`    // 地址
		IsDefault  uint   `json:"is_default"` // 是否为默认地址
	}
)

// AddressAdd 添加收货地址
// @Summary 添加收货地址
// @Tags address
// @Accept  json
// @Produce  json
// @Security ApiKeyAuth
// @Param json body controllers.AddShipAddressReq true "收货地址"
// @Success 200 {object} response.CommonResponse
// @Router /address/add [post]
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

	count := 0
	c.DB().Model(&models.ShipAddress{}).Count(&count)

	// 设置默认地址
	if count == 0 {
		address.ShipIsDefault = models.TrueTinyint
	} else {
		address.ShipIsDefault = utils.UnitTernaryOp(address.ShipIsDefault == models.TrueTinyint,
			models.TrueTinyint, models.FalseTinyint)
	}

	if err = c.DB().Create(address).Error; err != nil {
		return c.ErrorResp(response.ResultCodeDatabaseError, "创建失败")
	}

	return c.SuccessResp(address)
}

// AddressDelete 删除收货地址
// @Summary 删除收货地址
// @Tags address
// @Produce  json
// @Security ApiKeyAuth
// @Param id path int true "收货地址 id"
// @Success 200 {object} response.CommonResponse
// @Router /address/delete/{id} [post]
func AddressDelete(c *context.AppContext, u *models.UserInfo, s string) (err error) {
	id, err := c.UintParam("id")
	if err != nil {
		return c.ErrorResp(response.ResultCodeReqError, "参数错误")
	}

	address := new(models.ShipAddress)
	if err = c.DB().First(id, address).Error; err != nil {
		return c.ErrorResp(response.ResultCodeResourceError, "获取数据失败")
	}

	if err = c.DB().Delete(address).Error; err != nil {
		return c.ErrorResp(response.ResultCodeResourceError, "删除失败")
	}

	// 取消默认地址
	if models.TinyBool(address.ShipIsDefault) {
		address.ShipIsDefault = models.FalseTinyint
		c.DB().Save(address)
	}

	return c.SuccessResp(nil)
}

// AddressModify 修改收货地址
// @Summary 修改收货地址
// @Tags address
// @Accept  json
// @Produce  json
// @Security ApiKeyAuth
// @Param json body controllers.ModifyShipAddressReq true "收货地址"
// @Success 200 {object} response.CommonResponse
// @Router /address/modify [post]
func AddressModify(c *context.AppContext, u *models.UserInfo, s string) (err error) {
	req := new(ModifyShipAddressReq)
	if err = c.BindReq(req); err != nil {
		return err
	}

	address := new(models.ShipAddress)
	if err = c.DB().First(req.ID, address).Error; err != nil {
		return c.ErrorResp(response.ResultCodeResourceError, "获取数据失败")
	}

	address.ShipUserName = req.UserName
	address.ShipUserMobile = req.UserMobile
	address.ShipAddress = req.Address
	address.ShipIsDefault = req.IsDefault

	// 设置默认地址
	if models.TinyBool(address.ShipIsDefault) {
		if err = c.DB().Model(models.ShipAddress{}).Updates(models.ShipAddress{
			ShipIsDefault: models.FalseTinyint,
		}).Error; err != nil {
			return c.ErrorResp(response.ResultCodeResourceError, "修改失败")
		}
	}

	if err = c.DB().Save(address).Error; err != nil {
		return c.ErrorResp(response.ResultCodeResourceError, "修改失败")
	}

	return c.SuccessResp(address)
}

// AddressList 查询收货地址列表
// @Summary 查询收货地址列表
// @Tags address
// @Produce  json
// @Security ApiKeyAuth
// @Success 200 {object} response.CommonResponse
// @Router /address/list [get]
func AddressList(c *context.AppContext, u *models.UserInfo, s string) (err error) {
	list := new(models.ShipAddress)
	if err = c.DB().Where("user_id = ?", u.ID).Find(list).Error; err != nil {
		return c.SuccessResp(list)
	}

	return c.SuccessResp(list)
}
