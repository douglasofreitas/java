# spring-boot-docker

Spring Boot Application to run in Docker

Ref: https://spring.io/guides/gs/spring-boot-docker/


$ mvn install dockerfile:build
$ docker run -e "SPRING_PROFILES_ACTIVE=prod" -p 8080:8080 -t springio/gs-spring-boot-docker

Debug
$ docker run -e "JAVA_OPTS=-agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=n" -p 8080:8080 -p 5005:5005 -t springio/gs-spring-boot-docker