package models

const (
	CategoryTableName = "category"
)

// 购物车
type Category struct {
	BaseModel
	Name     string
	Icon     string
	ParentId uint
}

func (Category) TableName() string {
	return CategoryTableName
}
