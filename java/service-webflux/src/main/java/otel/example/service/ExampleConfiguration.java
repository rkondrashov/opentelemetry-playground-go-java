package otel.example.service;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Configuration
public class ExampleConfiguration {

    @Bean
    public RouterFunction<ServerResponse> routerFunctions() {
        return route()
                .GET("/", ExampleConfiguration::get)
                .POST("/", ExampleConfiguration::post)
                .build();
    }

    private static Mono<ServerResponse> get(ServerRequest request) {
        return ServerResponse.noContent().build();
    }

    private static Mono<ServerResponse> post(ServerRequest request) {
        return WebClient
                .create()
                .get()
                .uri(request.uri())
                .exchangeToMono(response -> ServerResponse.status(response.statusCode()).build());
    }

}
