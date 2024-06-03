FROM maven:3-eclipse-temurin-17-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

FROM  eclipse-temurin:17-jdk-alpine
COPY --from=build /target/*.jar inkmelo.jar
EXPOSE 8080
ENTRYPOINT [ "java","-jar","inkmelo.jar" ]
