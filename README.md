# jhipster-microservice-common
用于Jhipster创建的微服务应用的公用common组件。  

## 用途
用于Jhipster创建的微服务应用的公用common组件，简化模块间接口调用流程。  
1. 将feign接口提取为公用，解决各模块间接口的使用及维护问题。
2. 将@AuthorizedFeignClient和@AuthorizedUserFeignClient注解合并为@AuthorizedFeignClient，不再区分不同认证方式下的接口调用问题。  

## 使用说明
项目结构说明
![使用方法](./src/test/resources/pic/projectStructure.png)

## 注意事项
此项目主要是提供个思路供参考，若实际使用还需在其中加入对应的业务feign接口。

