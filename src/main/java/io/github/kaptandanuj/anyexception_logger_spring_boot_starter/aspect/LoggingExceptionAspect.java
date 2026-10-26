package io.github.kaptandanuj.anyexception_logger_spring_boot_starter.aspect;

import io.github.kaptandanuj.anyexception_logger_spring_boot_starter.annotation.ExceptionLog;
import io.github.kaptandanuj.anyexception_logger_spring_boot_starter.properties.ExceptionLoggerProperties;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
public class LoggingExceptionAspect {

    private static final Logger log = LoggerFactory.getLogger("DANUJ-EXCEPTION-LOGGER");
    private final ExceptionLoggerProperties properties;

    public LoggingExceptionAspect(ExceptionLoggerProperties properties) {
        this.properties = properties;
    }

    @AfterThrowing(
            pointcut = "@annotation(io.github.kaptandanuj.anyexception_logger_spring_boot_starter.annotation.ExceptionLog) || " +
                    "@within(io.github.kaptandanuj.anyexception_logger_spring_boot_starter.annotation.ExceptionLog)",
            throwing = "ex"
    )
    public void handleException(JoinPoint joinPoint, Throwable ex) {
        if (!properties.isEnabled()) {
            return;
        }


        ExceptionLog exceptionLog = extractAnnotation(joinPoint);

        String methodName = joinPoint.getSignature().toShortString();
        String tag = (exceptionLog != null && !exceptionLog.message().isEmpty())
                ? exceptionLog.message()
                : "UNHANDLED EXCEPTION";

        boolean shouldPrintStackTrace = (exceptionLog != null ? exceptionLog.printStackTrace() : true)
                && properties.isPrintStackTrace();

        boolean shouldLogArgs = (exceptionLog == null) || exceptionLog.pringArgs();

        StringBuilder banner = new StringBuilder();
        banner.append("\n==================== [ ").append(tag).append(" ] ====================")
                .append("\nMethod    : ").append(methodName)
                .append("\nException : ").append(ex.getClass().getName())
                .append("\nMessage   : ").append(ex.getMessage());

        if (shouldLogArgs) {
            banner.append("\nArguments : ").append(Arrays.toString(joinPoint.getArgs()));
        }

        banner.append("\n==========================================================================");


        System.err.println(banner.toString());

        if (exceptionLog != null && "WARN".equalsIgnoreCase(exceptionLog.level())) {
            if (shouldPrintStackTrace) {
                log.warn(banner.toString(), ex);
            } else {
                log.warn(banner.toString());
            }
        } else {
            if (shouldPrintStackTrace) {
                log.error(banner.toString(), ex);
            } else {
                log.error(banner.toString());
            }
        }
    }

    private ExceptionLog extractAnnotation(JoinPoint joinPoint) {
        if (joinPoint.getSignature() instanceof MethodSignature methodSignature) {
            Method method = methodSignature.getMethod();
            ExceptionLog methodAnnotation = method.getAnnotation(ExceptionLog.class);
            if (methodAnnotation != null) {
                return methodAnnotation;
            }
        }
        return joinPoint.getTarget().getClass().getAnnotation(ExceptionLog.class);
    }
}