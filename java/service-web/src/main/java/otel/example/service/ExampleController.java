package otel.example.service;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ExampleController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void get() {
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void post(HttpServletRequest request) {
        restTemplate.getForObject(request.getRequestURL().toString(), Void.class);
    }

    @GetMapping("/redirect")
    ResponseEntity<Void> redirect(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(request.getRequestURL().toString().replace("redirect", "")))
                .build();
    }
}
