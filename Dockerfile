# Stage 1: Build the application
FROM maven:3.9.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests=true

# Stage 2: Create the final image
FROM eclipse-temurin:17.0.12_7-jre-alpine
WORKDIR /app

ENV SPRING_PROFILES_ACTIVE=
ENV JDBC_DATABASE_URL=
ENV JDBC_DATABASE_PASSWORD=
ENV GOOGLE_CLIENT_ID=
ENV GOOGLE_CLIENT_SECRET=
ENV GITHUB_CLIENT_ID=
ENV GITHUB_CLIENT_SECRET=

COPY --from=build /app/target/springboot-security-impl-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]