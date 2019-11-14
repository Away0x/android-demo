package request

type GoodsListReq struct {
	CategoryId int    `query:"categoryId"`
	PageNo     int    `query:"pageNo"`
	Keyword    string `query:"keyword"`
}
