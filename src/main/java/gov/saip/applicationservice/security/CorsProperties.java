package gov.saip.applicationservice.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@Configuration
@ConfigurationProperties(prefix = "cors")
@Data
public class CorsProperties {
  private String[] allowedOrigins = null;
}
