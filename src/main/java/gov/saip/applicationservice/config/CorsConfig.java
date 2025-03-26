package gov.saip.applicationservice.config;

import gov.saip.applicationservice.security.CorsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Autowired
    private CorsProperties cors;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").
                allowCredentials(true).
                allowedOrigins(cors.getAllowedOrigins()).
                allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS").
                allowedHeaders("*"). //"Origin, X-Requested-With, Content-Type, Accept, " + "X-CSRF-TOKEN"
                exposedHeaders("Access-Control-Allow-Credentials");//"Vary","Access-Control-Max-Age", "Access-Control-Allow-Credentials","Access-Control-Allow-Methods" "Access-Control-Allow-Headers"
    }

}