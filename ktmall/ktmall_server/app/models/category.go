package models

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

type CategorySerializer struct {
	ID         uint   `json:"id"`
	Name       string `json:"name"`
	Icon       string `json:"icon"`
	ParentId   uint   `json:"parentId"`
	IsSelected bool   `json:"isSelected"`
}

func (Category) TableName() string {
	return CategoryTableName
}

func (m *Category) Serialize() CategorySerializer {
	return CategorySerializer{
		ID:         m.ID,
		Name:       m.Name,
		Icon:       m.Icon,
		ParentId:   m.ParentId,
		IsSelected: false,
	}
}
