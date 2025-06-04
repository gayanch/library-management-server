FROM docker.io/amazoncorretto:17-alpine

WORKDIR app

COPY target/library-management-server-0.0.1-SNAPSHOT.jar ./library-management-server.jar
COPY src/main/resources/application-dev.yaml ./application.yaml

EXPOSE 8080

CMD ["java", "-Xmx1G", "-jar", "library-management-server.jar"]
