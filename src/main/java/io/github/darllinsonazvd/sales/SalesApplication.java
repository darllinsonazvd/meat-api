package io.github.darllinsonazvd.sales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SalesApplication {
    public static void main(String[] args) {
        SpringApplication.run(SalesApplication.class, args);
    }
}
