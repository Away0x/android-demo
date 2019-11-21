package routes

import (
	"ktmall/app/context"
	. "ktmall/app/controllers/api"
	"ktmall/config"
	"ktmall/routes/wrapper"

	_ "ktmall/docs"

	"github.com/labstack/echo/v4"
	echoSwagger "github.com/swaggo/echo-swagger"
)

const (
	APIPrefix = "/api"
)

// @title KTMall Api
// @version 1.0
// @description KTMall api document

// @contact.name Away0x
// @contact.url https://github.com/Away0x
// @contact.email wutong0910@foxmail.com

// @host localhost:9999
// @BasePath /api

// @securityDefinitions.apiKey ApiKeyAuth
// @in header
// @name Authorization
func registerAPI(e *echo.Echo) {
	if config.IsDev() {
		e.GET("/apidoc/*", echoSwagger.WrapHandler).Name = "apidoc"
	}

	ee := e.Group(APIPrefix)

	context.RegisterHandler(ee.GET, "/upload_token", wrapper.GetTokenAndUser(CommonGetQiNiuUploadToken)).Name = "upload_token"

	user := ee.Group("/user")
	{
		context.RegisterHandler(user.POST, "/register", UserRegister).Name = "user.register"
		context.RegisterHandler(user.POST, "/login", UserLogin).Name = "user.login"
		context.RegisterHandler(user.POST, "/logout", wrapper.GetTokenAndUser(UserLogout)).Name = "user.logout"
		context.RegisterHandler(user.POST, "/refresh_token", wrapper.GetToken(UserRefreshToken)).Name = "user.refresh_token"
		context.RegisterHandler(user.POST, "/forget_pwd", UserForgetPwd).Name = "user.forget_pwd"
		context.RegisterHandler(user.POST, "/reset_pwd", wrapper.GetTokenAndUser(UserResetPwd)).Name = "user.reset_pwd"
		context.RegisterHandler(user.POST, "/edit", wrapper.GetTokenAndUser(UserEdit)).Name = "user.edit"
		context.RegisterHandler(user.GET, "/info", wrapper.GetTokenAndUser(UserInfo)).Name = "user.info"
	}

	address := ee.Group("/address")
	{
		context.RegisterHandler(address.POST, "/add", wrapper.GetTokenAndUser(AddressAdd)).Name = "address.add"
		context.RegisterHandler(address.POST, "/delete/:id", wrapper.GetTokenAndUser(AddressDelete)).Name = "address.delete"
		context.RegisterHandler(address.POST, "/modify", wrapper.GetTokenAndUser(AddressModify)).Name = "address.modify"
		context.RegisterHandler(address.GET, "/list", wrapper.GetTokenAndUser(AddressList)).Name = "address.list"
	}

	cart := ee.Group("/cart")
	{
		context.RegisterHandler(cart.GET, "/list", wrapper.GetTokenAndUser(CartList)).Name = "cart.list"
		context.RegisterHandler(cart.POST, "/add", wrapper.GetTokenAndUser(CartAdd)).Name = "cart.add"
		context.RegisterHandler(cart.POST, "/delete", wrapper.GetTokenAndUser(CartDelete)).Name = "cart.delete"
		context.RegisterHandler(cart.POST, "/submit", wrapper.GetTokenAndUser(CartSubmit)).Name = "cart.submit"
	}

	category := ee.Group("/category")
	{
		context.RegisterHandler(category.GET, "/list", CategoryList).Name = "category.list"
	}

	goods := ee.Group("/goods")
	{
		context.RegisterHandler(goods.GET, "/list", GoodsList).Name = "goods.list"
		context.RegisterHandler(goods.GET, "/detail/:id", GoodsDetail).Name = "goods.detail"
	}

	message := ee.Group("/message")
	{
		context.RegisterHandler(message.GET, "/list", wrapper.GetTokenAndUser(MessageList)).Name = "message.list"
	}

	order := ee.Group("/order")
	{
		context.RegisterHandler(order.POST, "/get_pay_sign", OrderGetPaySign).Name = "order.get_pay_sign"

		context.RegisterHandler(order.GET, "/list", wrapper.GetTokenAndUser(OrderList)).Name = "order.list"
		context.RegisterHandler(order.GET, "/detail/:id", wrapper.GetOrder(OrderDetail)).Name = "order.detail"
		context.RegisterHandler(order.POST, "/submit/:id", wrapper.GetOrder(OrderSubmit)).Name = "order.submit"

		context.RegisterHandler(order.POST, "/pay/:id", wrapper.GetOrder(OrderPay)).Name = "order.pay"
		context.RegisterHandler(order.POST, "/confirm/:id", wrapper.GetOrder(OrderConfirm)).Name = "order.confirm"
		context.RegisterHandler(order.POST, "/cancel/:id", wrapper.GetOrder(OrderCancel)).Name = "order.cancel"
	}
}
