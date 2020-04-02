package models

import (
	"ktmall/common/cache"
	"ktmall/common/utils"
	"strconv"

	"github.com/pkg/errors"

	"github.com/jinzhu/gorm"
)

const (
	UserInfoTableName = "user_info"
	UserModelCacheKey = "user_info_"
)

// 用户表
type UserInfo struct {
	BaseModel
	Name         string `sql:"comment:'用户名（登录名）'"`
	Pwd          string `sql:"comment:'登录密码'"`
	Mobile       string `sql:"comment:'手机号'" gorm:"unique_index:mobile_idx"`
	Icon         string `sql:"comment:'头像'"`
	RealName     string `sql:"comment:'真实姓名'"`
	IdentityCard string `sql:"comment:'身份证'"`
	NickName     string `sql:"comment:'昵称'"`
	Gender       string `sql:"comment:'性别'"`
	Birthday     string `sql:"comment:'生日'"`
	Address      string `sql:"comment:'居住地）'"`
	Sign         string `sql:"comment:'个性签名'"`
	PushId       string `sql:"comment:'推送 id'"`
}

type UserSerializer struct {
	ID     string `json:"id"`
	Icon   string `json:"icon"`
	Name   string `json:"name"`
	Gender string `json:"gender"`
	Mobile string `json:"mobile"`
	Sign   string `json:"sign"`
}

func (m *UserInfo) Serialize() UserSerializer {
	return UserSerializer{
		ID:     m.IDString(),
		Icon:   m.Icon,
		Name:   m.Name,
		Gender: m.Gender,
		Mobile: m.Mobile,
	}
}

func (UserInfo) TableName() string {
	return UserInfoTableName
}

func (u *UserInfo) BeforeCreate(scope *gorm.Scope) (err error) {
	if u.Pwd != "" {
		if !passwordEncrypted(u.Pwd) {
			pwd, err := u.Encrypt()
			if err != nil {
				return errors.New("User Model 创建失败: passwordEncrypted")
			}
			scope.SetColumn("pwd", pwd)
		}
	}

	return err
}

func (u *UserInfo) BeforeUpdate(scope *gorm.Scope) (err error) {
	if !passwordEncrypted(u.Pwd) {
		pwd, err := u.Encrypt()
		if err != nil {
			return errors.New("UserInfo Model 更新失败")
		}
		scope.SetColumn("pwd", pwd)
	}

	return
}

// 判断密码是否加密过了
func passwordEncrypted(pwd string) (status bool) {
	return len(pwd) == 60 // 长度等于 60 说明加密过了
}

// Encrypt 对密码进行加密
func (u *UserInfo) Encrypt() (pwd string, err error) {
	pwd, err = utils.Encrypt(u.Pwd)
	u.Pwd = pwd
	return
}

// Compare 验证用户密码
func (u *UserInfo) Compare(pwd string) (err error) {
	err = utils.Compare(u.Pwd, pwd)
	return
}

// cache -------
func getUserModelCacheKey(id uint) string {
	return UserModelCacheKey + strconv.Itoa(int(id))
}

func putUserModelCache(u *UserInfo) {
	cache.Put(getUserModelCacheKey(u.ID), u, cache.DefaultExpiration)
}

func getUserModelFromCache(id uint) (*UserInfo, bool) {
	key := getUserModelCacheKey(id)
	cacheUser, ok := cache.Get(key)
	if !ok {
		return nil, false
	}

	if u, ok := cacheUser.(*UserInfo); ok {
		return u, true
	}

	return nil, false
}

// utils
func GetUserInfo(id uint) (*UserInfo, error) {
	if cacheUser, ok := getUserModelFromCache(id); ok {
		return cacheUser, nil
	}

	user := new(UserInfo)
	if err := DB().First(user, id).Error; err != nil {
		return nil, err
	}

	putUserModelCache(user)
	return user, nil
}

func DeleteUser(id uint) error {
	u := new(UserInfo)
	u.BaseModel.ID = id

	if err := DB().Delete(u).Error; err != nil {
		return err
	}

	cache.Del(getUserModelCacheKey(id))
	return nil
}

func (u *UserInfo) Create() error {
	if err := DB().Create(u).Error; err != nil {
		return err
	}

	putUserModelCache(u)
	return nil
}

func (u *UserInfo) Update() error {
	if err := DB().Save(u).Error; err != nil {
		return err
	}

	putUserModelCache(u)
	return nil
}
