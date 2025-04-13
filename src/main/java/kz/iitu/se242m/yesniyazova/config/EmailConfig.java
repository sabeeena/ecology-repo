package kz.iitu.se242m.yesniyazova.config;

import kz.iitu.se242m.yesniyazova.notifications.email.SmtpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
@Profile("prod")
@PropertySource("classpath:application.properties")
public class EmailConfig {

    @Value("${email.host}")
    private String host;

    @Value("${email.user}")
    private String user;

    @Value("${email.pass}")
    private String pass;

    @Bean(initMethod = "connect", destroyMethod = "disconnect")
    @Lazy
    public SmtpClient smtpClient() {
        return new SmtpClient(host, user, pass);
    }
}
