package otel.example.service;

import java.time.Duration;
import java.util.function.Supplier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ExampleApplication {


	@Bean
	@Profile("default")
	public Supplier<ClientHttpRequestFactory> simpleClientHttpRequestFactory() {
		return SimpleClientHttpRequestFactory::new;
	}

	@Bean
	@Profile("okhttp")
	public Supplier<ClientHttpRequestFactory> okHttp3ClientHttpRequestFactory() {
		return OkHttp3ClientHttpRequestFactory::new;
	}

	@Bean
	@Profile("apache")
	public Supplier<ClientHttpRequestFactory> httpComponentsClientHttpRequestFactory() {
		return HttpComponentsClientHttpRequestFactory::new;
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder, Supplier<ClientHttpRequestFactory> requestFactory) {
		return builder
				.requestFactory(requestFactory)
				.setConnectTimeout(Duration.ofSeconds(1))
				.setReadTimeout(Duration.ofSeconds(1))
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(ExampleApplication.class, args);
	}

}
