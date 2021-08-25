# crudapi

### build
```bash
mvn clean install -Dmaven.test.skip=true -s settings.xml
```

### downloadSources
```bash
mvn dependency:sources -DdownloadSources=true -DdownloadJavadocs=true -s settings.xml
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