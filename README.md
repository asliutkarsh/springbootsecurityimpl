# springbootsecurityimpl

## Run using maven

### Configuration

```yaml
datasource:
    url: ${JDBC_DATABASE_URL}
    username: ${JDBC_DATABASE_USERNAME}
    password: ${JDBC_DATABASE_PASSWORD}
```

```yaml
security:
    oauth2:
        client:
            registration:
                google:
                    client-id: ${GOOGLE_CLIENT_ID}
                    client-secret: ${GOOGLE_CLIENT_SECRET}
                github:
                    client-id: ${GITHUB_CLIENT_ID}
                    client-secret: ${GITHUB_CLIENT_SECRET}

```

### Running the application

To run the application using Maven, use the following command:

```sh
mvn spring-boot:run
```

## Run using Docker

To run the application using Docker, use the following commands:

```sh
docker build -t springbootsecurityimpl .
```

```sh
docker run -p 8080:8080 springbootsecurityimpl -e JDBC_DATABASE_URL=jdbc:mysql://localhost:5432/springbootsecurityimpl -e JDBC_DATABASE_USERNAME=root -e JDBC_DATABASE_PASSWORD=123456 -e GOOGLE_CLIENT_ID=google-client-id -e GOOGLE_CLIENT_SECRET=google-client-secret -e GITHUB_CLIENT_ID=github-client-id -e GITHUB_CLIENT_SECRET=github-client-secret
```

## Run using Docker Compose

Create .env file with the following content:

```sh
SPRING_PROFILES_ACTIVE=dev
MYSQL_DATABASE=springboot_security_jwt
MYSQL_PASSWORD=123456
MYSQL_ROOT_PASSWORD=123456
GOOGLE_CLIENT_ID=
GOOGLE_CLIENT_SECRET=
GITHUB_CLIENT_ID=
GITHUB_CLIENT_SECRET=
```

Run the following command:

```sh
docker-compose up
```
