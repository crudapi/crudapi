# crudapi

### LICENSE
[LICENSE](./LICENSE)

### build
```bash
mvn clean install -Dmaven.test.skip=true -s settings.xml
```

### downloadSources
```bash
mvn dependency:sources -DdownloadSources=true -DdownloadJavadocs=true -s settings.xml
```

## Mysql

### Import database to crudapi
./database/mysql/crudapi-mysql.sql

### Config MySql properties
src/crudapi-service/main/resources/application.properties
```bash
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/crudapi?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=root
```

## Postgresql
### Import database to crudapi.public

./database/postgresql/crudapi-pgsql.sql

### Config Postgresql properties
src/crudapi-service/main/resources/application.properties
```bash
spring.datasource.driverClassName=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/crudapi
spring.datasource.username=postgres
spring.datasource.password=postgres
```

## oralce
```sql
SELECT * FROM ALL_OBJECTS WHERE OWNER = 'CRUDAPI';
purge recyclebin;
```

### run
```bash
java -jar ./target/crudapi-service-1.6.0.jar
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
```