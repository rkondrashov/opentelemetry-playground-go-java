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

@SpringBootApplication
public class ExampleApplication implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(ExampleApplication.class);

	private RestTemplate restTemplate;

	public ExampleApplication(RestTemplateBuilder builder) {
		restTemplate = builder
				.setConnectTimeout(Duration.ofSeconds(1))
				.setReadTimeout(Duration.ofSeconds(1))
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(ExampleApplication.class, args);
	}

	@Override
	public void run(String... args) {
		LOG.info("Calling service");
		IntStream.range(0, 10).forEach(i -> restTemplate.getForObject("http://localhost:8080", Void.class));
		await().pollDelay(Duration.ofSeconds(2)).until(() -> true);
		LOG.info("Waiting for few seconds to export spans");
	}

}
