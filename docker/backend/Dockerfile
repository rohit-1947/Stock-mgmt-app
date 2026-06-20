# Build stage
FROM maven:3.9.16-amazoncorretto-21 AS build
WORKDIR /build
COPY pom.xml .
#this build folder(.)
RUN mvn dependency:go-offline
COPY src ./src
#Current location(./)
RUN mvn clean package -DskipTests

#Runtime stage
FROM amazoncorretto:21 AS runtime
ARG PROFILE=dev
ARG APP_VERSION=1.0.0
#Define few things(workDir)

WORKDIR /app
COPY --from=build /build/target/saas-multi-tenant-app-*.jar /app/
#copying .jar file(present in target) to working directory(app)

EXPOSE 8080

ENV DB_URL=jdbc:postgresql://postgres-saas-db:5432/saas-app-db
ENV ACTIVE_PROFILE=${PROFILE}
ENV JAR_VERSION=${APP_VERSION}

CMD java -jar -Dspring.profiles.active=${ACTIVE_PROFILE} -Dspring.datasource.url=${DB_URL} saas-multi-tenant-app-${JAR_VERSION}.jar
