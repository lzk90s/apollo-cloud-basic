package team.uni.apollo.basic.feign.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team.uni.apollo.basic.feign.interceptor.FeignRequestInterceptor;

/**
 * @author : qihang.liu
 * @date 2022-02-28
 */
@Configuration
public class FeignConfiguration {
    @Bean
    public FeignErrorDecoder feignExceptionErrorDecoder() {
        return new FeignErrorDecoder();
    }

    @Bean
    public FeignRequestInterceptor feignRequestInterceptor() {
        return new FeignRequestInterceptor();
    }

    @Bean
    public IntegrationAspect integrationAspect() {
        return new IntegrationAspect();
    }
}
