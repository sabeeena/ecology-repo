package kz.iitu.se242m.yesniyazova.config;

import jakarta.transaction.UserTransaction;
import jakarta.transaction.TransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration
public class JtaConfig {

    @Bean
    UserTransaction userTx() {
        return com.arjuna.ats.jta.UserTransaction.userTransaction();
    }

    @Bean
    TransactionManager txManagerBean() {
        return com.arjuna.ats.jta.TransactionManager.transactionManager();
    }

    @Bean
    PlatformTransactionManager transactionManager(
            UserTransaction utx, TransactionManager tm) {
        return new JtaTransactionManager(utx, tm);
    }
}
