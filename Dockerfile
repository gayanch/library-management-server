FROM docker.io/amazoncorretto:17-alpine

# Activate the dev profile by default
ENV SPRING_PROFILES_ACTIVE=dev

WORKDIR app

COPY target/library-management-server-0.0.1-SNAPSHOT.jar ./library-management-server.jar

# Copy the defualt config file
COPY src/main/resources/application.yaml .

# Copy profile specific config files
COPY src/main/resources/application-*.yaml .

EXPOSE 8080

CMD ["java", "-Xmx1G", "-jar", "library-management-server.jar"]
