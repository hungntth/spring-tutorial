# spring-tutorial hungnt

- Chưa cập nhật ...
- Chưa cập nhật ...
- Chưa cập nhật ...

# Yêu cầu ?

1. JDK - 21: [Oracle Java Download JDK 21](https://www.oracle.com/java/technologies/downloads/#java21)
2. Docker
3. MYSQL:8.0.36-debian
```
docker pull mysql:8.0.36-debian
docker run --name mysql-8.0.36 -p 3307:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:8.0.36-debian
```
4. Build docker
```
docker build -t hello-spring .
docker run -d -p 5454:5454 hello-spring

```