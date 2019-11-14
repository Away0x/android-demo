package response

import (
	"ktmall/app/models"
)

type CategoryListResp []models.CategorySerializer

func BuildCategoryListResp(ms []*models.Category) (list CategoryListResp) {
	list = make(CategoryListResp, len(ms))
	for i, o := range ms {
		list[i] = o.Serialize()
	}
	return
}
