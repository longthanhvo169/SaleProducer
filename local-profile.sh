#!/bin/bash
echo 'mvn clean package'
mvn clean package
echo 'Start '
cd target
java -Dspring.profiles.active=local -jar SaleProducer-0.0.1-SNAPSHOT.jar