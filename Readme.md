# Library Management Service

### Requirements
* Java 17
* Maven
* MongoDB 8

### Build
```shell
mvn clean package
```

### Run
* Update `application-{profile}.yaml` to match your developer setup.
    * Update the mongo URI
```shell
cd target
java -jar library-management-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

### Project Structure
* This project uses the spec first approach with OpenAPI schema acting as the spec. All the endpoints, request, and response
definitions are defined in the spec first.
* The project utilizes OpenAPI codegen (through a `meven` plugin) to generate Request, Response, Endpoints, and API documentation. This includes the interactive swagger UI which can be used to test REST endpoints

### Testing
* Start the app
* Open the embedded Swagger UI which documents the available APIs and provides an interactive API test client.
`http://localhost:8080/swagger-ui/index.html`