FROM maven:3.9.3-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:11.0.7-jdk-slim
COPY --from=build /target/inkmelo-0.0.1-SNAPSHOT.jar inkmelo.jar
EXPOSE 8080
ENTRYPOINT [ "java","-jar","inkmelo.jar" ]