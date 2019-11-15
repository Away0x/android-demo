# 初始化
```bash
# 开发模式需要安装 fresh
go get -v -u github.com/Away0x/fresh
# 创建数据库 ktmall_development

cd ktmall_server
make install
# 初始化数据
make mock
```

# API 文档
> host:port/apidoc/index.html

```bash
# 开发
make api-doc
```

# 启动
```bash
cd ktmall_server
make dev
```
