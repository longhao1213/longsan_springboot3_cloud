#### 这是一款基于springboot3+jdk21+kubernetes的微服务框架
框架在不停的更新之中。每一个负责模块都可以独立允许，并且内部有写好一份readMe文件方便阅读。
并且有写好对应的kubernetes相关的yaml文件，方便直接部署。
#### 项目结构
```
base:组件模块
 -- nacos：注册中心
 -- security6:权限校验
common：工具和公共模块
Order、User:用于测试用的web模块
```

项目里面注册中心和配置中心有两种实现，一种是nacos管理，一种是基于kubernetes方式

#### kubernetes 本地部署
本地采用了kubesphere来作为客户端
```html
http://127.0.0.1:30880/dashboard 
admin/LongH******
```
#### arthas 使用 User服务中有使用
1. 引入依赖 4.0.0及以上才支持springboot3
    ```xml
    <dependency>
        <groupId>com.taobao.arthas</groupId>
        <artifactId>arthas-spring-boot-starter</artifactId>
        <version>${arthas.version}</version> 
    </dependency>
    ```
2. 部署tunnel-server服务（可选）
    ```shell
   # tunnel-server服务是用来远程管理/连接多个 Agent，让我们可以用来访问到内网的Java环境，一般不要把这个服务端口暴露在公网
   # 下载地址  https://github.com/alibaba/arthas/releases
   java -jar  arthas-tunnel-server.jar
   # tunnel-server默认端口8080 arthas agent 连接的端口是7777
   #  http://127.0.0.1:8080/ 反问到web控制台，输入对应服务的agent-id来连接
    ```
3. 配置项目
    ```yaml
    arthas:
     #自定义服务在arthas中的名称 主要用来在tunnel-server确定唯一的标识
     agent-id: user-server-local
     # tunnel-server服务器的地址，前提是已经配置好了tunnel-server
     tunnel-server: ws://127.0.0.1:7777/ws
     # 当前服务名，如果不配置agent-id会自动生成服务名加下划线随机值的agent-id
     app-name: user-server-local
     # 默认telnet端口
     telnet-port: 3658
     # http端口
     http-port: 8563
    ```
4. 启动项目
    ```java
    web端默认地址 http://127.0.0.1:8563/
    telnet默认地址 telnet localhost 3658
    ```