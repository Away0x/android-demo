package response

import (
	"ktmall/app/models"
)

type CartListResp []models.CartGoodsSerializer

func BuildCartListResp(ms []*models.CartGoods) (list CartListResp) {
	list = make(CartListResp, len(ms))
	for i, o := range ms {
		list[i] = o.Serialize()
	}
	return
}
