#!/bin/bash
echo 'mvn clean package -Dmaven.test.skip=true'
mvn clean package -Dmaven.test.skip=true
echo 'Start '
cd target
java -Dspring.profiles.active=local -jar SaleProducer-0.0.1-SNAPSHOT.jar