# 使用官方的 OpenJDK 作为基础镜像
FROM openjdk:21-jdk

# 设置应用的工作目录
WORKDIR /app

ENV TZ=Asia/shanghai
EXPOSE 9003

# 将项目的 jar 文件复制到容器中
COPY target/User-1.0-SNAPSHOT.jar /app/User.jar

# 运行 Spring Boot 应用
ENTRYPOINT ["java", "-jar", "/app/User.jar"]