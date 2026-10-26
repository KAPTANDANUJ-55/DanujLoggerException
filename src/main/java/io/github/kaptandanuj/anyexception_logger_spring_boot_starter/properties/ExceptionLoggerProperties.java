package io.github.kaptandanuj.anyexception_logger_spring_boot_starter.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kaptandanuj.logger")

public class ExceptionLoggerProperties {

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isPrintStackTrace() {
        return printStackTrace;
    }

    public void setPrintStackTrace(boolean printStackTrace) {
        this.printStackTrace = printStackTrace;
    }

    private boolean enabled = true;

    private boolean printStackTrace = true;
}