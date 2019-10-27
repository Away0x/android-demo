package models

import (
	"ktmall/common/serializer"
)

const (
	CategoryTableName = "category"
)

// 商品分类
type Category struct {
	BaseModel
	Name     string
	Icon     string
	ParentId uint
}

func (Category) TableName() string {
	return CategoryTableName
}

func (c *Category) Serialize() serializer.Data {
	return serializer.Data{
		"id":         c.IDString(),
		"name":       c.Name,
		"icon":       c.Icon,
		"parentId":   c.ParentId,
		"isSelected": false,
	}
}
