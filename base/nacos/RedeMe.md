#### 准备工作
1. 首先部署好mysql8
2. 创建数据库 nacos_config
3. 创建表 https://github.com/alibaba/nacos/blob/master/distribution/conf/mysql-schema.sql
#### docker安装nacos2.4
``` shell
docker run -d \
-e NACOS_AUTH_ENABLE=true \
-e PREFER_HOST_MODE=hostname \
-e NACOS_AUTH_TOKEN=SecretKey012345678901234567890123456789012345678901234567890123456789 \
-e MODE=standalone \
-e JVM_XMS=128m \
-e JVM_XMX=128m \
-e JVM_XMN=128m \
-p 8848:8848 \
-p 9848:9848 \
-e SPRING_DATASOURCE_PLATFORM=mysql \
-e MYSQL_SERVICE_HOST=mysql8 \
-e MYSQL_SERVICE_PORT=3306 \
-e MYSQL_SERVICE_USER=root \
-e MYSQL_SERVICE_PASSWORD=123456 \
-e MYSQL_SERVICE_DB_NAME=nacos_config \
-e MYSQL_SERVICE_DB_PARAM='characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useUnicode=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true' \
-e NACOS_AUTH_IDENTITY_KEY=nacos \
-e NACOS_AUTH_IDENTITY_VALUE=nacos \
-e MYSQL_DATABASE_NUM=1 \
--restart=always \
--privileged=true \
--link mysql8:mysql \
--name nacos2.4 \
nacos/nacos-server:v2.4.0.1-slim
```