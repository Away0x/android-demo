package request

type CategoryListReq struct {
	ParentId int `query:"parentId"`
}
