# Opentelemetry playground for go and java components

## Getting started

1. Start Jaeger:
```bash
docker run -d --name jaeger \
   -p 16686:16686 \
   -p 14268:14268 \
   -p 14250:14250 \
   jaegertracing/all-in-one:latest
```
2. Java service and client
    1. Build java service and client with `mvn clean package`.
    2. Download Opentelemetry Java Agent
    ```bash
    wget https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent-all.jar`
    ```
    1. Start the service:
     ```bash
     java -javaagent:opentelemetry-javaagent-all.jar \
          -Dotel.traces.exporter="jaeger" \
          -Dotel.metrics.exporter="none" \
          -Dotel.resource.attributes="service.name=java-service"
          -jar target/service-1.0.0.jar
     ```
    2. Run client to perform some HTTP calls to the service.
     ```bash
     java -javaagent:opentelemetry-javaagent-all.jar \
          -Dotel.traces.exporter="jaeger" \
          -Dotel.metrics.exporter="none" \
          -Dotel.resource.attributes="service.name=java-client" \
          -jar client/target/client-1.0.0.jar
     ```
3. Go service and client
    1. Build go service and client with `make build`.
    2. Start the service `./dist/service`
    3. Run client to perform some HTTP calls to the service `./dist/service`.
4. Also you can start java service and perform calls using go client and vice versa.
5. Take a look at traces in jaeger UI - [http://localhost:16686](http://localhost:16686).
