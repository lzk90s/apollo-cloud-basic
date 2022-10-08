package team.uni.apollo.basic.core.config;

import org.springframework.context.annotation.Bean;
import team.uni.apollo.basic.core.exception.RestAdviceHandler;
import team.uni.apollo.basic.core.util.SpringContextHolder;

public class BeanConfiguration {
    @Bean
    public RestAdviceHandler restExceptionAdvice() {
        return new RestAdviceHandler();
    }
    
    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

}
