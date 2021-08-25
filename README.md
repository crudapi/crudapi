# crudapi

### build
```bash
mvn clean install -Dmaven.test.skip=true -s settings.xml
```

### downloadSources
```bash
mvn dependency:sources -DdownloadSources=true -DdownloadJavadocs=true -s settings.xml
```

### mysql config
/etc/mysql/conf.d/mysql.cnf

```bash
[mysqld]
ft_min_word_len=1
innodb_ft_min_token_size=1
```

### Import database
./mysql/crudapi.sql

### Config MySql properties
src/main/resources/application.properties
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/crudapi?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=root
```

### run
```bash
java -jar ./target/crudapi-service-1.0.0.jar
```

### swagger
[http://127.0.0.1:8888/swagger-ui.html](http://127.0.0.1:8888/swagger-ui.html)

### user
```
superadmin
1234567890
```

### docker
```bash
docker build -t crudapi-service:latest .

docker run -v ~/crudapi-pro/maven:/crudapi/maven -ti --rm crudapi-service /bin/sh
```