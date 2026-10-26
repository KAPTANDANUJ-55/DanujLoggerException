package io.github.kaptandanuj.anyexception_logger_spring_boot_starter.config;


import io.github.kaptandanuj.anyexception_logger_spring_boot_starter.properties.ExceptionLoggerProperties;
import io.github.kaptandanuj.anyexception_logger_spring_boot_starter.aspect.LoggingExceptionAspect;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@AutoConfiguration
@EnableAspectJAutoProxy
@EnableConfigurationProperties(ExceptionLoggerProperties.class)
@ConditionalOnProperty(prefix = "kaptandanuj.logger", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ExceptionLoggerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public LoggingExceptionAspect exceptionLoggingAspect(ExceptionLoggerProperties properties) {
        return new LoggingExceptionAspect(properties);
    }
}