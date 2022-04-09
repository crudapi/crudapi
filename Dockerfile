FROM maven:3.8-openjdk-8 as builder

COPY settings.xml /crudapi/settings.xml
COPY pom.xml /crudapi/pom.xml
COPY crudapi-core/pom-proguard.xml /crudapi/crudapi-core/pom.xml
COPY crudapi-api/pom.xml /crudapi/crudapi-api/pom.xml
COPY crudapi-rest/pom.xml /crudapi/crudapi-rest/pom.xml
COPY crudapi-security/pom.xml /crudapi/crudapi-security/pom.xml
COPY crudapi-weixin/pom.xml /crudapi/crudapi-weixin/pom.xml
COPY crudapi-service/pom.xml /crudapi/crudapi-service/pom.xml

WORKDIR /crudapi
RUN mvn dependency:go-offline -s settings.xml > mvnlog.txt && \
    tail mvnlog.txt

COPY . /crudapi/

#pro and free
RUN mv /crudapi/crudapi-core/pom.xml /crudapi/crudapi-core/pom-noproguard.xml && \
    mv /crudapi/crudapi-core/pom-proguard.xml /crudapi/crudapi-core/pom.xml && \
    mvn package -Dmaven.test.skip=true -s settings.xml > mvnlog.txt && \
    tail mvnlog.txt && \
    rm -rf mvnlog.txt && \
    version=`awk '/<version>[^<]+<\/version>/{gsub(/<version>|<\/version>/,"",$1);print $1;exit;}' pom.xml` && \
    mkdir -p /crudapi/dist/crudapi/$version && \
    rm -rf /crudapi/crudapi-core/target/*proguard* && \
    cp /crudapi/crudapi-core/target/*.jar /crudapi/dist/crudapi/$version && \
    cp /crudapi/crudapi-api/target/*.jar /crudapi/dist/crudapi/$version && \
    cp /crudapi/crudapi-rest/target/*.jar /crudapi/dist/crudapi/$version && \
    cp /crudapi/crudapi-security/target/*.jar /crudapi/dist/crudapi/$version && \
    cp /crudapi/crudapi-weixin/target/*.jar /crudapi/dist/crudapi/$version && \
    cp /crudapi/crudapi-service/target/*.jar /crudapi

FROM openjdk:8-jdk-alpine

ENV TZ Asia/Shanghai

USER root
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories && \
    apk add --update --no-cache tzdata && \
    cp /usr/share/zoneinfo/${TZ} /etc/localtime && \
    apk del tzdata && \
    rm -rf /var/cache/apk/* && \
    mkdir -p /var/log/cn/crudapi/service && chmod 777 /var/log/cn/crudapi/service

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

WORKDIR /crudapi

ARG JAR_FILE=/crudapi/*.jar
COPY --from=builder ${JAR_FILE} crudapi-service.jar
COPY --from=builder /crudapi/dist /crudapi/dist/
COPY run.sh run.sh
COPY crudapi-service/src/main/resources/application-prod.properties application-prod.properties

CMD ["/crudapi/run.sh"]
