package pers.kun.core.config;

import org.springframework.context.annotation.Bean;
import pers.kun.core.exception.RestAdviceHandler;
import pers.kun.core.util.SpringContextHolder;

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
