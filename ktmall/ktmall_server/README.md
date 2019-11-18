# 初始化
```bash
# 开发模式需要安装 fresh
go get -v -u github.com/Away0x/fresh
# 创建数据库 ktmall_development

cd ktmall_server
# 下载依赖
make install
# 初始化数据
make mock
```

# API 文档
> host:port/apidoc/index.html

```bash
# 依赖 github.com/swaggo/swag/cmd/swag
# 生成 swagger 文档
make api-doc
```

# 启动
```bash
cd ktmall_server
make dev
```

# 部署
```bash
cd ktmall_server
make deploy
```

# 项目结构
<details>
<summary>展开查看</summary>
<pre><code>
├── app              项目核心逻辑代码
│    ├── context     控制器 context 封装
│    ├── controllers 控制器
│    ├── models      模型
│    ├── auth        用户、权限相关
│    │    └── token  jwt token
│    ├── requests    请求参数类型、参数验证
│    ├── response    响应类型
│    └── services    复杂查询
│
├── cmd              command
│
├── bootstrap        各组件初始化
│
├── config           配置中心
│
├── database         数据库
│    └── factory     数据 mock
│
├── common           项目一些依赖: 工具函数等
│
├── docs             swagger api doc
│
├── public           前端静态文件
│
├── resources        项目资源文件
│    └── views       view 模板文件
│
├── routes           路由
│    ├── middleware  中间件
│    ├── wrapper     路由 wrapper
│    ├── routes.go   路由注册
│    ├── api.go      api 路由注册
│    ├── error.go    路由的错误处理
│    └── web.go      页面路由注册
│
├── storage          存放日志等文件
│    └── config.json 输出项目运行中的配置
│    └── routes.json 输出项目的路由表
│
├── main.go          项目入口
│
├── bin              项目相关可执行文件
│    └── deploy      部署脚本
│
├── config.yaml      项目配置
│
├── fresh.conf       项目开发热更新的配置
│
└── Makefile         Makefile 文件
</code></pre>
</details>
