package ru.javaops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.javaops.config.AppConfig;
import ru.javaops.config.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
@EnableJpaRepositories("ru.javaops.repository")
public class JavaOPsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaOPsApplication.class, args);
    }
}
