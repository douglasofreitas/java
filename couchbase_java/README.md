# Couchbase + Java API (Spring Boot)

Build Image Database: 
$ docker build -t couchbase-custom ./database

Build Image App: 
$ docker build -t spring-boot-custom ./app

Create containers:
$ docker-compose run -d --service-ports --name couchbase couchbase
$ docker-compose run -d --service-ports --name spring-boot spring-boot

REF: https://dzone.com/articles/using-docker-to-deploy-a-containerized-java-web-app

