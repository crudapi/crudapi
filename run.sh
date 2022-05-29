#!/bin/sh
echo "DB_ACTION = $DB_ACTION"

cp -R /crudapi/dist/* /crudapi/maven

sed -i "s/server.port=.*$/server.port=$CRUDAPI_SERVER_PORT/g" application-prod.properties
sed -i "s/spring.datasource.password=.*$/spring.datasource.password=$CRUDAPI_MYSQL_PASSWORD/g" application-prod.properties
sed -i "s/spring.datasource.hikari.data-sources[0].mysql.password=.*$/spring.datasource.hikari.data-sources[0].mysql.password=$CRUDAPI_MYSQL_PASSWORD/g" application-prod.properties

if [ "$DB_ACTION" == "INIT_DATA" ]; then
    echo "[dev]DB_ACTION INIT_DATA..."
    java -jar /crudapi/crudapi-service.jar --spring.profiles.active=dev
else
    echo "[prod]DB_ACTION NULL..."
    java -jar /crudapi/crudapi-service.jar --spring.profiles.active=prod
fi