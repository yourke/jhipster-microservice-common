# jhipster-microservice-common
用于Jhipster创建的微服务应用的公用common组件。  

## 用途
用于Jhipster创建的微服务应用的公用common组件，简化模块间接口调用流程。  
1. 将feign接口提取为公用，解决各模块间接口的使用及维护问题。
2. 将@AuthorizedFeignClient和@AuthorizedUserFeignClient注解合并为@AuthorizedFeignClient，不再区分不同认证方式下的接口调用问题。  

## 使用说明
1. 项目结构  
![项目结构](./src/test/resources/pic/projectStructure.png)

2. 使用方式  
在引入common包之后，当前模块能扫描到该common后即可使用。
参考测试类中的示例demo，和一般原生Fegin注解使用方式无异。
![使用方法](./src/test/resources/pic/useMethod.png)

## 实现原理
### 认证凭据传递
UAA认证方式主要有两种：用户调用、机器调用
1. 用户调用  
用户自身登录，调用权限接口。
![用户调用](./src/test/resources/pic/oauth2Password.png)

1. 机器调用
模块自身登录，调用公共接口。一般用于定时作业，无用户的情况。
![机器调用](./src/test/resources/pic/oauth2Client.png)

简单来说就是用用户的身份或模块的身份去登录，在跨模块调用时需要传递对应的认证信息token到目标模块中。  
在此common中对应以下代码：
![token传递](./src/test/resources/pic/relayToken.png)


### 内部调用校验
TODO 如何区分内部还是外部接口调用

## 注意事项
此项目主要是提供个思路供参考，若实际使用还需在其中加入对应的业务feign接口。

