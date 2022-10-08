package team.uni.apollo.basic.gateway.service.config;

import org.slf4j.helpers.BasicMarkerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogConfig {
    @Bean
    public BasicMarkerFactory basicMarkerFactory() {
        return new BasicMarkerFactory();
    }
}
