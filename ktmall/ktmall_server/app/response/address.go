package response

import (
	"ktmall/app/models"
)

type AddressListResp []models.AddressSerializer

func BuildAddressListResp(ms []*models.ShipAddress) (list AddressListResp) {
	list = make(AddressListResp, len(ms))
	for i, o := range ms {
		list[i] = o.Serialize()
	}
	return
}
