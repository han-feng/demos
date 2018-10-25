package org.demos.sec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class DemosSpringsecApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemosSpringsecApplication.class, args);
    }
}
