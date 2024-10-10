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
docker network create my_network
docker run --name mysql --network my_network -p 3307:3306  -e MYSQL_ROOT_PASSWORD=root -v E:\mysql_data:/var/lib/mysql -d mysql:8.0.36-debian 
```
4. Build docker
```
docker build -t hello-spring .
docker run --name hello-spring-app --network my_network -p 5454:5454 -e DBMS_CONNECTION=jdbc:mysql://mysql:3307/spring_tutorial hello-spring

```