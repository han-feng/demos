# Spring Boot 技术验证

- [ ] REST 服务接口文档生成工具
  - [ ] Spring REST Docs
  - [ ] Swagger
- [ ] Spring Boot 自动化集成测试
- [ ] Security 防护能力验证
  - [ ] SQL注入、SQL盲注
  - [ ] 跨站脚本注入
  - [ ] 使用HTTP动词篡改的认证旁路。复现方法：
  	```
  	curl -X BOGUS -v http://localhost:8080/test
  	```
- [x] log4j2 集成，并实现基于 application.yml 的配置
