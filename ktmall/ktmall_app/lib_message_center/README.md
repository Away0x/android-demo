1、主工程build.gradle需要配置JPUSH_PKGNAME: applicationId，否则 Manifest 找不到访 key
2、其它配置放到 message 中
3、使用 gradle 自动引包，不需要其它配置
4、自定义 Recevier 加项目配置。
5、主工程进行初始化
