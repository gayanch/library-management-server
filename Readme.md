# Library Management Service

## Requirements
- Java 17
- Maven
- MongoDB 8

## Build
```shell
mvn clean package
```

## Run
1. Update the `application-{profile}.yaml` file to match your development setup:
    - Update the MongoDB URI.
2. Copy the configuration YAML files to the `target` directory, or provide the paths to the config files using the command-line argument:  
   `--spring.config.location=<path1,path2,...>`
3. Start the application:
```shell
cd target
java -jar library-management-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

## Project Structure
- This project uses a spec-first approach, with the OpenAPI schema acting as the specification. All endpoints, requests, and response definitions are defined in the spec.
- The project utilizes OpenAPI code generation (through a Maven plugin) to generate requests, responses, endpoints, and API documentation. This includes an interactive Swagger UI, which can be used to test REST endpoints.
- Any updates to the REST endpoints and related objects must be made in the OpenAPI spec first. Please refer to the spec file: `openapi-spec-v3.yaml`.

## Testing
- Start the application.
- Open the embedded Swagger UI, which documents the available APIs and provides an interactive API test client:  
  [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)