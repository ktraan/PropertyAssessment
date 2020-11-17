#!/bin/sh
mvn clean package && docker build -t dmit2015.ktran36.assignment04/dmit2015-assignment04-ktran36 .
docker rm -f dmit2015-assignment04-ktran36 || true && docker run -d -p 9080:9080 -p 9443:9443 --name dmit2015-assignment04-ktran36 dmit2015.ktran36.assignment04/dmit2015-assignment04-ktran36