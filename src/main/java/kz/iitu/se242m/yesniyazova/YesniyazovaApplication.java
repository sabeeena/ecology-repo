package kz.iitu.se242m.yesniyazova;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = "kz.iitu.se242m.yesniyazova")
public class YesniyazovaApplication {

    public static void main(String[] args) {
        SpringApplication.run(YesniyazovaApplication.class, args);
    }

}
