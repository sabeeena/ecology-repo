package kz.iitu.se242m.yesniyazova.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.*;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@Configuration
@PropertySource("classpath:application.properties")
@Lazy
@Scope("singleton")
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.datasource.driver-class-name}")
    private String dbDriverClass;

    private BasicDataSource dataSource;

    @PostConstruct
    public void init() {
        log.info("[DBConfig] DatabaseConfig has been constructed.");
        log.info("[DBConfig] Properties loaded from application.properties:");
        log.info("[DBConfig] DB URL: {}", dbUrl);
        log.info("[DBConfig] DB User: {}", dbUser != null ? dbUser : "Not provided");

        if (dbUrl == null || dbUser == null || dbPassword == null) {
            log.warn("[DBConfig] One or more DB properties are missing - check config.");
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void checkDatabaseConnection() {
        log.info("[DBConfig] Checking DB connectivity for: {}", dbUrl);
        if (dataSource == null) {
            log.warn("[DBConfig] DataSource has not been created yet.");
        } else {
            try (Connection conn = dataSource.getConnection()) {
                if (!conn.isValid(2)) {
                    log.warn("[DBConfig] Database connection is not valid!");
                } else {
                    log.info("[DBConfig] Database connection is valid.");
                }
            } catch (SQLException e) {
                log.error("[DBConfig] Failed to connect to database.", e);
            }
        }
    }

    @PreDestroy
    public void destroy() {
        log.info("[DBConfig] Database configuration shutting down...");
        try {
            if (dataSource != null) {
                dataSource.close();
                log.info("[DBConfig] DataSource closed successfully.");
            }
        } catch (Exception e) {
            log.error("[DBConfig] Failed to close DataSource.", e);
        }
    }

    @Bean(name = "customDataSource")
    public DataSource dataSource() {
        dataSource = new BasicDataSource();
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUser);
        dataSource.setPassword(dbPassword);
        dataSource.setDriverClassName(dbDriverClass);
        log.info("[DBConfig] Initialized DataSource.");
        return dataSource;
    }

    @Bean
    @Primary
    @DependsOn("customDataSource")
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
