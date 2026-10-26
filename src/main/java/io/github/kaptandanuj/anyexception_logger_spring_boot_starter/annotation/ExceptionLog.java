package io.github.kaptandanuj.anyexception_logger_spring_boot_starter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionLog {
  // Log Level : Warn Or Error Default Is Error
  String level() default "ERROR";
 // Custom Log Message That Will Be Seen In Header
  String message() default "";
// If UserWant TO PrintStackTree OR NOT  Default Is True
  boolean printStackTrace() default true;
// If You Want To Print Method Args At Console Or NOT
  boolean pringArgs() default true;


}
