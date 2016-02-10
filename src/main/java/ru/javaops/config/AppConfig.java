package ru.javaops.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.sql.SQLException;

/**
 * GKislin
 */
@Configuration
public class AppConfig {

    //    @Profile("dev")
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2WebServer() throws SQLException {
        return Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082");
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }

    /*
        @Autowire(required = true)
        public void configeJackson(Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder) {
            jackson2ObjectMapperBuilder.se
                    .setS(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
                    .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }

    @Bean
    @Primary
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder mapperBuilder) {
        return mapperBuilder.build()
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }
*/
    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder() {
            @Override
            public void configure(ObjectMapper objectMapper) {
                super.configure(objectMapper);
                objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
                        .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            }
        };
    }
}
