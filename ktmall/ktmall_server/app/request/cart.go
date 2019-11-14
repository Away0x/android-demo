package request

// 购物车添加商品
type AddCartReq struct {
	GoodsId    uint   `json:"goodsId"`
	GoodsDesc  string `json:"goodsDesc"`
	GoodsIcon  string `json:"goodsIcon"`
	GoodsPrice int    `json:"goodsPrice"`
	GoodsCount uint   `json:"goodsCount"`
	GoodsSku   string `json:"goodsSku"`
}

// 提交购物车
type SubmitCartReq struct {
	TotalPrice int `json:"totalPrice"`
	GoodsList  []struct {
		GoodsId    uint
		GoodsDesc  string
		GoodsIcon  string
		GoodsCount uint
		GoodsSku   string
		GoodsPrice int
	} `json:"goodsList"`
}

// 购物车删除商品
type DeleteCartReq struct {
	CartIdList []uint `json:"cartIdList"`
}
