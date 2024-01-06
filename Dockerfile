FROM maven:3.9.6-amazoncorretto-17 AS build

LABEL org.opencontainers.image.source="https://github.com/dev-ops-fmi/travelly"

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM amazoncorretto:17-alpine3.18

COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]