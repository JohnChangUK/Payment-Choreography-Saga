package payment.saga.payment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;

@Configuration
public class JdbcConfig {

    @Bean
    public Scheduler jdbcScheduler(@Value("${spring.datasource.maximum-pool-size}") int connectionPoolSize) {
        return Schedulers.fromExecutor(Executors.newFixedThreadPool(connectionPoolSize));
    }

}
