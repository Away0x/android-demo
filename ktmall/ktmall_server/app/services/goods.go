package services

import (
	"ktmall/app/models"
	"ktmall/common/serializer"

	"github.com/jinzhu/gorm"
)

type GoodsService struct {
	DB *gorm.DB
}

func (g GoodsService) GoodsList(categoryId int) ([]serializer.Data, error) {
	list := make([]*models.GoodsInfo, 0)

	query := g.DB.Where("category_id = ?", categoryId).
		Offset(0).
		Limit(6).
		Find(&list)

	if err := query.Error; err != nil {
		return nil, err
	}

	var (
		ids     = make([]uint, len(list))
		results = make([]serializer.Data, len(list))
	)

	for i, v := range list {
		ids[i] = v.ID
		results[i] = v.Serialize()
	}

	skus := make([]*models.GoodsSku, 0)
	skuquery := g.DB.Where("goods_id IN (?)", ids).Find(&skus)
	if err := skuquery.Error; err != nil {
		return nil, err
	}

	for i, v := range list {
		ss := make([]serializer.Data, 0)
		for _, kv := range skus {
			if v.ID == kv.GoodsId {
				ss = append(ss, kv.Serialize())
			}
		}
		results[i]["goodsSku"] = ss
	}

	return results, nil
}
