package kz.iitu.se242m.yesniyazova;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = "kz.iitu.se242m.yesniyazova")
@EnableJpaAuditing
public class YesniyazovaApplication {

    public static void main(String[] args) {
        SpringApplication.run(YesniyazovaApplication.class, args);
    }

}
