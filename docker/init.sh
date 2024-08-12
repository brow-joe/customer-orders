#!/bin/bash

CONTAINER_ID="coade"
MYSQL_USER="root"
MYSQL_DATABASE="customer-orders"
MYSQL_PASSWORD="Vr*SJ3<@~2"

docker exec -i $CONTAINER_ID mysql -u $MYSQL_USER -p"$MYSQL_PASSWORD" -e "CREATE DATABASE IF NOT EXISTS $MYSQL_DATABASE;"
docker exec -i $CONTAINER_ID mysql -u $MYSQL_USER -p"$MYSQL_PASSWORD" -e "UPDATE mysql.user SET host='%' WHERE user='$MYSQL_USER';"
docker exec -i $CONTAINER_ID mysql -u $MYSQL_USER -p"$MYSQL_PASSWORD" -e "FLUSH PRIVILEGES;"
