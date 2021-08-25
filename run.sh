#!/bin/sh
echo "DB_ACTION = $DB_ACTION"

cp -R /crudapi/dist/* /crudapi/maven

if [ "$DB_ACTION" == "INIT_DATA" ]; then
    echo "[dev]DB_ACTION INIT_DATA..."
    java -jar /crudapi/crudapi-service.jar --spring.profiles.active=dev
else
    echo "[prod]DB_ACTION NULL..."
    java -jar /crudapi/crudapi-service.jar --spring.profiles.active=prod
fi