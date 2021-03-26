package otel.example.service;

import static org.awaitility.Awaitility.await;

import java.time.Duration;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ExampleApplication implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(ExampleApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ExampleApplication.class, args);
	}

	@Override
	public void run(String... args) {
		LOG.info("Calling service");
		IntStream.range(0, 10).forEach(i -> WebClient
				.create()
				.get()
				.uri("http://localhost:8080")
				.retrieve()
				.bodyToMono(Void.class)
				.share()
				.block());
		await().pollDelay(Duration.ofSeconds(2)).until(() -> true);
		LOG.info("Waiting for few seconds to export spans");
	}

}
