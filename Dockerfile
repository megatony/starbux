FROM maven:3.5-jdk-8-alpine as builder

WORKDIR /Users/starbux
COPY pom.xml .
COPY src ./src

RUN mvn clean install spring-boot:repackage -DskipTests

FROM openjdk:8-jre-alpine

COPY --from=builder /Users/starbux/target/starbux-0.0.1.jar /starbux.jar

CMD ["java","-Dserver.port=8080", "-jar","/starbux.jar"]
EXPOSE 8080