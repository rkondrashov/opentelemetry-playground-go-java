package otel.example.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseStatus(HttpStatus.NO_CONTENT)
public class ExampleController {

    @GetMapping("/")
    public void get() {
    }

}
